package com.laskapi.myradio.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laskapi.myradio.TAG
import com.laskapi.myradio.data.DataStoreRepository
import com.laskapi.myradio.data.StationsRepository
import com.laskapi.myradio.model.StationModel


import com.laskapi.myradio.player.PlayerController
import com.laskapi.myradio.ui.root.BottomNavItem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val LAST_FAVORITE_INDEX = "last_favorite_index"

@HiltViewModel
class MViewModel @Inject constructor(
    // val retrofitStationsService: Lazy<StationsRetrofitService>
    // val mirrorsRepository: MirrorsRepository,
    private val dataStoreRepository: DataStoreRepository,
    @ApplicationContext val ctx: Context,
    private val stationsRepository: StationsRepository,

    ) : ViewModel() {


    val errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    private var launchingScreenProperty: BottomNavItem = BottomNavItem.Home

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(TAG, "VVVVVVVVVVVVVVVV+$throwable.printStackTrace()")
        throwable.printStackTrace()
    }

    //  private val _playerState = MutableStateFlow(PlayerState())
    //  val playerState = _playerState.asStateFlow()

    private val _playerController = MutableStateFlow<PlayerController?>(null)
    val playerController = _playerController.asStateFlow()

    private val _stations = MutableStateFlow<List<StationModel>>(emptyList())
    val stations: StateFlow<List<StationModel>> = _stations.asStateFlow()

    private val _favorites = MutableStateFlow<List<StationModel>>(emptyList())
    val favorites: StateFlow<List<StationModel>> = _favorites.asStateFlow()

    private val playlist = MutableStateFlow<List<StationModel>>(emptyList())

    init {

            viewModelScope.launch {

            launch {
                dataStoreRepository.getString(LAST_FAVORITE_INDEX).collect {
                    it?.let {
                        _playerController.value?.setLastFavoriteId(it)
                    }
                    Log.d(TAG, "LOADING $it")
                }
            }

            _playerController.value =
                PlayerController(ctx,  { errorMessage.value = it }, playlist)

            getStations()
            getFavorites()

            launch(Dispatchers.IO) {

                        playerController.value?.currMediaItem?.collect {
                            Log.d(TAG,"CONTROLLER1" + it.mediaId)
                            saveCurrItemId(it.mediaId)}

            }

            launch(Dispatchers.Default) {
                stations.collect { list ->
                    if (launchingScreenProperty == BottomNavItem.Search) {
                        Log.d(TAG, "SEARCH")
                        playlist.value = list
                    }
                }
            }
            launch(Dispatchers.Default) {
                favorites.collect { list ->
                    if (launchingScreenProperty == BottomNavItem.Home) {
                        Log.d(TAG, "FAVOR")
                        playlist.value = list
                    }
                }
            }
        }
    }


    fun selectStation(station: StationModel, launchingScreen: BottomNavItem) {
        launchingScreenProperty = launchingScreen
        _playerController.value?.let {
            it.setCurrentStation(station)
            it.play(true)
        }
        if (launchingScreen == BottomNavItem.Search) {
            playlist.value = stations.value
        } else {
            playlist.value = favorites.value
        }
    }


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

    override fun onCleared() {
        playerController.value?.clear()
        super.onCleared()
    }

    private fun saveCurrItemId(itemId:String) {
        Log.d(TAG,"SAVE_LAST_ITEM")
        if (favorites.value.indexOfFirst { it.stationuuid == itemId } != -1) {
            viewModelScope.launch {
                dataStoreRepository.putString(
                    LAST_FAVORITE_INDEX, itemId
                )
            }
        }
    }

}