package com.laskapi.myradio.viewmodel

import android.content.ComponentName
import android.content.Context
import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import com.laskapi.myradio.TAG
import com.laskapi.myradio.data.DataStoreRepository
import com.laskapi.myradio.data.StationsRepository
import com.laskapi.myradio.model.StationModel
import com.laskapi.myradio.player.PlaybackService
import com.laskapi.myradio.ui.root.BottomNavItem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min


private const val LAST_FAVORITE_INDEX = "last_favorite_index"


@HiltViewModel
class MViewModel @Inject constructor(
    // val retrofitStationsService: Lazy<StationsRetrofitService>
    // val mirrorsRepository: MirrorsRepository,
    val dataStoreRepository: DataStoreRepository,
    @ApplicationContext val ctx: Context,
    val stationsRepository: StationsRepository,

    ) : ViewModel() {


    val errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)

    private var itemToRemoveIndex: Int? = null
    private var launchingScreenProperty: BottomNavItem = BottomNavItem.Home
    private lateinit var playerController: MediaController
    private val controllerFuture: ListenableFuture<MediaController>
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(TAG, "VVVVVVVVVVVVVVVV+$throwable.printStackTrace()")
        throwable.printStackTrace()
    }

    private val _playerState = MutableStateFlow(PlayerState())
    val playerState = _playerState.asStateFlow()

    private val _controllerState = MutableStateFlow<MediaController?>(null)
    val controllerState = _controllerState.asStateFlow()

    private val _stations = MutableStateFlow<List<StationModel>>(emptyList())
    val stations: StateFlow<List<StationModel>> = _stations.asStateFlow()

    private val _favorites = MutableStateFlow<List<StationModel>>(emptyList())
    val favorites: StateFlow<List<StationModel>> = _favorites.asStateFlow()

    //  val playeController: MutableStateFlow<MediaController?> = MutableStateFlow(null)

    init {
        getStations()
        getFavorites()
        val sessionToken = SessionToken(
            ctx, ComponentName(
                ctx, PlaybackService::class.java
            )
        )

        //               playeController.value = controllerFuture.get()
        controllerFuture = MediaController.Builder(ctx, sessionToken).buildAsync()
        controllerFuture.addListener(
            {
                playerController = controllerFuture.get()
                _controllerState.value = playerController
                playerController.addListener(object : Player.Listener {
                    private var shouldResumePlaying: Boolean = false

                    override fun onIsPlayingChanged(playing: Boolean) {
                        _playerState.update { state ->
                            state.copy(isPlaying = playing)
                        }
                        super.onIsPlayingChanged(playing)
                    }

                    override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                        if (mediaMetadata.station != null) {
                            _playerState.update { state ->
                                state.copy(metadata = mediaMetadata)
                            }
                        }
                        super.onMediaMetadataChanged(mediaMetadata)
                    }

                    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {

                        if (shouldResumePlaying) {
                            play(true)
                            shouldResumePlaying = false
                            errorMessage.value = null
                        }
                        itemToRemoveIndex?.let { playerController.removeMediaItem(it) }
                        itemToRemoveIndex = null

                        val currentIndexInFavorites = favorites.value.indexOfFirst {
                            it.stationuuid
                                .equals(mediaItem?.mediaId)
                        }

                        viewModelScope.launch {
                            dataStoreRepository.putInt(
                                LAST_FAVORITE_INDEX, max(currentIndexInFavorites, 0)
                            )
                        }

           //             super.onMediaItemTransition(mediaItem, reason)
                    }

                    override fun onPlayerError(error: PlaybackException) {
                        shouldResumePlaying = true
                        errorMessage.value = error.message.toString()
                 //       super.onPlayerError(error)
                    }

                })


                viewModelScope.launch(Dispatchers.Default) {
                    stations.collect { list ->
                        if (launchingScreenProperty == BottomNavItem.Search) {
                            Log.d(TAG, "SEARCH")
                            setPlaylist(list)
                        }
                    }
                }
                viewModelScope.launch(Dispatchers.Default) {
                    favorites.collect { list ->
                        if (launchingScreenProperty == BottomNavItem.Home) {
                            Log.d(TAG, "FAVOR")
                            setPlaylist(list)
                        }
                    }
                }
                viewModelScope.launch {
                    dataStoreRepository.getInt(LAST_FAVORITE_INDEX).collect {
                        if (playerController.currentMediaItem == null) {
                            val startItemIndex = min(it ?: 0, favorites.value.size - 1)
                            playerController.setMediaItem(
                                buildMediaItem(
                                    favorites.value.get(
                                        startItemIndex
                                    )
                                )
                            )
                            setPlaylist(favorites.value)
                        }
                    }
                }

            }, MoreExecutors.directExecutor()
        )
    }


    fun selectStation(station: StationModel, launchingScreen: BottomNavItem) {
//        playeController.value?.setMediaItem(MediaItem.fromUri(streamUrl))
        launchingScreenProperty = launchingScreen
        playerController.setMediaItem(buildMediaItem(station))

        play(true)
        if (launchingScreen == BottomNavItem.Search) {
            setPlaylist(stations.value)
        } else {
            setPlaylist(favorites.value)
        }
    }

    private fun setPlaylist(stationsPlaylist: List<StationModel>) {

           viewModelScope.launch(Dispatchers.Main) {
                //   return@launch
                //     playerController.setMediaItem(buildMediaItem(favorites.value.get (lastFavoriteIndex.first())))


            val index = stationsPlaylist.indexOfFirst {
                it.stationuuid.equals(
                    playerController.currentMediaItem?.mediaId
                )
            }
            Log.d(
                TAG,
                "test:" + playerController.currentMediaItem.toString() + "::" + index + "::" + stationsPlaylist.size
            )

            playerController.moveMediaItem(playerController.currentMediaItemIndex, 0)
            if (playerController.mediaItemCount > 1) {
                playerController.removeMediaItems(1, playerController.mediaItemCount)
            }

            if (index == -1) {
                playerController.addMediaItems(stationsPlaylist.map { buildMediaItem(it) })
                itemToRemoveIndex = playerController.currentMediaItemIndex
            } else {
                playerController.addMediaItems(stationsPlaylist.filter {
                    !it.stationuuid.equals(
                        playerController
                            .currentMediaItem?.mediaId
                    )
                }.map
                { buildMediaItem(it) })
                playerController.moveMediaItem(0, index)
                itemToRemoveIndex = null
            }
        }
    }

    fun togglePlay() {
        play(!playerController.isPlaying)
    }

    private fun play(play: Boolean) {
        if (play) {
            playerController.play()
        } else {
            playerController.pause()
        }
    }

    fun playPrevious() = playerController.seekToPreviousMediaItem()
    fun playNext() = playerController.seekToNextMediaItem()

    fun addToFavorites(station: StationModel) =
        viewModelScope.launch { _favorites.value = stationsRepository.addToFavorites(station) }

    fun removeFromFavorites(station: StationModel) = viewModelScope.launch {
        _favorites.value = stationsRepository.removeFavorite(station)
    }

    private fun getFavorites() =
        viewModelScope.launch { _favorites.value = stationsRepository.getFavorites() }

    fun getStations(filter: String = "") {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _stations.value = stationsRepository.getStations(filter).toMutableStateList()
        }
    }

    private fun buildMediaItem(station: StationModel): MediaItem {
        return MediaItem.Builder().setUri(station.url).setMediaId(station.stationuuid)
            .setMediaMetadata(MediaMetadata.Builder().setStation(station.name).build()).build()
    }

    override fun onCleared() {
        MediaController.releaseFuture(controllerFuture)
        super.onCleared()
    }

}