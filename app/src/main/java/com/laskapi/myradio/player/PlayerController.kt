package com.laskapi.myradio.player

import android.content.ComponentName
import android.content.Context
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import com.laskapi.myradio.TAG
import com.laskapi.myradio.model.StationModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayerController @Inject constructor(
    ctx: Context,
    setErrorMessage: (msq: String?) -> Unit,
    private val playlist: Flow<List<StationModel>>
) {
    private val sessionToken = SessionToken(
        ctx, ComponentName(
            ctx, PlaybackService::class.java
        )
    )
    private val controllerFuture = MediaController.Builder(ctx, sessionToken).buildAsync()

    private lateinit var mediaController: MediaController
    private var itemToRemoveIndex: Int? = null
    private val scope = CoroutineScope(Dispatchers.Main)


    private val _state = MutableStateFlow(PlayerState())
    val state = _state.asStateFlow()

    private val _currMediaItem = MutableStateFlow(MediaItem.EMPTY)
    val currMediaItem = _currMediaItem.asStateFlow()
    /*

        private val _isPlaying=MutableStateFlow(false)
        val isPlaying=_isPlaying.asStateFlow()

        private val _metadataList = MutableStateFlow<List<String>>(emptyList())
        val metadataList=_metadataList.asStateFlow()

    */

    private var lastFavoriteId: String = ""

    init {

        controllerFuture.addListener(
            {
                mediaController = controllerFuture.get()

                scope.launch {
                    playlist.collect { list ->
                        if (mediaController.currentMediaItem == null && list.isNotEmpty()) {
                            list.find { it.stationuuid == lastFavoriteId }?.let {
                                mediaController.setMediaItem(
                                    buildMediaItem(
                                        it
                                    )
                                )
                            }
                        }
                        setPlaylist(list)

                    }
                }

                mediaController.addListener(
                    object : Player.Listener {
                        private var shouldResumePlaying: Boolean = false

                        override fun onIsPlayingChanged(playing: Boolean) {
                            //_isPlaying.value=playing
                            _state.update { currState ->
                                currState.copy(isPlaying = playing)
                            }
                        }

                        override fun onPlaybackStateChanged(playbackState: Int) {
                            Log.d(TAG, "PLAYBACK STATE = $playbackState")
                            _state.update { currState->currState.copy(state=playbackState) }
                            super.onPlaybackStateChanged(playbackState)
                        }

                        override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                            val tempList = mutableListOf<String>()
                            mediaMetadata.station?.also { tempList.add(it.toString()) }
                            mediaMetadata.albumArtist?.also { tempList.add(it.toString()) }
                            mediaMetadata.albumTitle?.also { tempList.add(it.toString()) }
                            mediaMetadata.artist?.also { tempList.add(it.toString()) }
                            mediaMetadata.displayTitle?.also { tempList.add(it.toString()) }
                            mediaMetadata.title?.also { tempList.add(it.toString()) }
                            mediaMetadata.subtitle?.also { tempList.add(it.toString()) }
                            mediaMetadata.composer?.also { tempList.add(it.toString()) }
                            mediaMetadata.conductor?.also { tempList.add(it.toString()) }
                            mediaMetadata.description?.also { tempList.add(it.toString()) }
                            mediaMetadata.genre?.also { tempList.add(it.toString()) }
                            tempList.removeAll { it.isEmpty() }
                            Log.d(TAG, "METADATA CHANGED+$tempList")

                            if (tempList.isNotEmpty()) {
                                _state.update { currState -> currState.copy(metadataList = tempList) }
                                //     _metadataList.value=list}
                            }
                        }

                        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {

                            if (shouldResumePlaying) {
                                mediaController.play()
                                shouldResumePlaying = false
                                setErrorMessage(null)
                            }
                            itemToRemoveIndex?.let { mediaController.removeMediaItem(it) }
                            itemToRemoveIndex = null
                            mediaItem?.let {
                                _currMediaItem.value = it
                            }
                        }


                        override fun onPlayerError(error: PlaybackException) {
                            shouldResumePlaying = true
                            setErrorMessage(error.message.toString())
                        }

                    })
            }, MoreExecutors.directExecutor()
        )
    }


    private fun setPlaylist(stationsPlaylist: List<StationModel>) {

        val index = stationsPlaylist.indexOfFirst {
            it.stationuuid == mediaController.currentMediaItem?.mediaId
        }
        Log.d(
            TAG,
            "PLAYLIST STATE:" + mediaController.currentMediaItem.toString() + "::" + index + "::" +
                    stationsPlaylist.size
        )

        mediaController.moveMediaItem(mediaController.currentMediaItemIndex, 0)
        if (mediaController.mediaItemCount > 1) {
            mediaController.removeMediaItems(1, mediaController.mediaItemCount)
        }

        if (index == -1) {
            mediaController.addMediaItems(stationsPlaylist.map { buildMediaItem(it) })
            itemToRemoveIndex = mediaController.currentMediaItemIndex
        } else {
            mediaController.addMediaItems(stationsPlaylist.filter {
                it.stationuuid != mediaController.currentMediaItem?.mediaId
            }.map { buildMediaItem(it) })
            mediaController.moveMediaItem(0, index)
            itemToRemoveIndex = null
        }
    }

    private fun buildMediaItem(station: StationModel): MediaItem {
        return MediaItem.Builder().setUri(station.url).setMediaId(station.stationuuid)
            .setMediaMetadata(MediaMetadata.Builder().setStation(station.name).build())
            .build()
    }


    fun playPrevious() = mediaController.seekToPreviousMediaItem()
    fun playNext() = mediaController.seekToNextMediaItem()

    fun togglePlay() {
        play(!mediaController.isPlaying)
    }

    fun play(play: Boolean) {
        if (play) {
            mediaController.play()
        } else {
            mediaController.pause()
        }
    }


    fun clear() {
        MediaController.releaseFuture(controllerFuture)
    }

    fun setLastFavoriteId(id: String) {
        lastFavoriteId = id

    }

    fun setCurrentStation(station: StationModel) {
        mediaController.setMediaItem(buildMediaItem(station))
    }

}