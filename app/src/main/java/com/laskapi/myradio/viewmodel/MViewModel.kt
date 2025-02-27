package com.laskapi.myradio.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laskapi.myradio.data.StationsRetrofitService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.Lazy
@HiltViewModel
class MViewModel @Inject constructor(
    val retrofitStationsService: Lazy<StationsRetrofitService>
   /* val mirrorsRepository: MirrorsRepository,
    val stationsRepository: StationsRepository*/
) :
    ViewModel() {


    fun test() {
        viewModelScope.launch(Dispatchers.IO) {

   //             retrofitStationsService.get()
        //                  Log.d(TAG, stationsRepository.fetchRecommendedStations().toString())
        }
    }
}