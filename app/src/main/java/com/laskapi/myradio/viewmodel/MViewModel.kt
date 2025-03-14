package com.laskapi.myradio.viewmodel

import android.content.ComponentName
import android.content.Context
import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import com.laskapi.myradio.TAG
import com.laskapi.myradio.model.StationModel
import com.laskapi.myradio.data.StationsRepository
import com.laskapi.myradio.player.PlaybackService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

const val CONNECTION_TIMEOUT = 2000L


@HiltViewModel
class MViewModel @Inject constructor(
    // val retrofitStationsService: Lazy<StationsRetrofitService>
    // val mirrorsRepository: MirrorsRepository,
    @ApplicationContext val ctx: Context,
    val stationsRepository: StationsRepository,

    ) : ViewModel() {


    private lateinit var playerController: MediaController

    private val _stations = MutableStateFlow<List<StationModel>>(emptyList())
    val stations: StateFlow<List<StationModel>> = _stations.asStateFlow()

    private val _favorites = MutableStateFlow<List<StationModel>>(emptyList())
    val favorites: StateFlow<List<StationModel>> = _favorites.asStateFlow()

    private val _isPlaying: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    var controllerFuture:ListenableFuture<MediaController>
    init {
        getStations()
        getFavorites()

        val sessionToken = SessionToken(
            ctx, ComponentName(
                ctx, PlaybackService::class.java
            )
        )
        controllerFuture = MediaController.Builder(ctx, sessionToken).buildAsync()
        controllerFuture.addListener(
            {
                playerController = (controllerFuture.get())
               // playerController.addListener()
            }, MoreExecutors.directExecutor()
        )
    }


    fun getStations(filter: String = "") {
        val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
            throwable.printStackTrace()
        }

/*
        fun getFromApi() {
            viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
                retrofitService.getStuffFromInternet()
            }
        }
*/
        viewModelScope.launch(Dispatchers.IO+coroutineExceptionHandler) {
            _stations.value = stationsRepository.getStations(filter).toMutableStateList()
        }
    }

    fun selectStation(streamUrl: String) {

        playerController.setMediaItem(MediaItem.fromUri(streamUrl))
        if (!playerController.isPlaying) {
            play(true)
        }
    }

    fun togglePlay() {
        play(!playerController.isPlaying)
    }

    private fun play(play: Boolean) {
        if (play) {
            playerController.addMediaItems(favorites.value.map { MediaItem.fromUri(it.url) }
                .filter { it!=playerController.currentMediaItem })


            playerController.play()
            _isPlaying.value = true
            viewModelScope.launch {
                delay(CONNECTION_TIMEOUT)
                if (!playerController.isPlaying) {
                    playerController.stop()
                    _isPlaying.value = false
                }
            }

        } else {

            playerController.stop()
            playerController.clearMediaItems()
            _isPlaying.value = false
        }
    }

    fun addToFavorites(station: StationModel) =
        viewModelScope.launch { _favorites.value = stationsRepository.addToFavorites(station) }

    fun removeFromFavorites(station: StationModel) =
        viewModelScope.launch {
            _favorites.value = stationsRepository.removeFavorite(station)
            }


    private fun getFavorites() =
        viewModelScope.launch { _favorites.value = stationsRepository.getFavorites() }

    fun isFavorite(station: StationModel): Boolean =
        favorites.value.contains(station)


    override fun onCleared() {
        MediaController.releaseFuture(controllerFuture)
        super.onCleared()
    }

}