package com.laskapi.myradio.viewmodel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laskapi.myradio.data.StationHeader
import com.laskapi.myradio.data.StationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MViewModel @Inject constructor(
    // val retrofitStationsService: Lazy<StationsRetrofitService>
    // val mirrorsRepository: MirrorsRepository,
    val stationsRepository: StationsRepository
) :
    ViewModel() {

    private val _stations = MutableStateFlow<List<StationHeader>>(emptyList())
    val stations: StateFlow<List<StationHeader>> = _stations.asStateFlow()

    init {
        getStations()
    }


    fun getStations(filter:String="") {
        viewModelScope.launch(Dispatchers.IO) {
            _stations.value = stationsRepository.getStations(filter).toMutableStateList()
        }
    }

}