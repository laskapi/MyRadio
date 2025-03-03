package com.laskapi.myradio.data

import android.util.Log
import com.laskapi.myradio.TAG
import javax.inject.Inject
import dagger.Lazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface StationsDataSource {
    suspend fun fetchStations(filter:String): List<StationHeader>
}

class StationsRepository @Inject constructor(
    private val dataSource:
    Lazy<StationsDataSource>
) {
    suspend fun getStations(filter:String=""): List<StationHeader> {
        val list = withContext(Dispatchers.IO) {
            dataSource.get()
        }.fetchStations(filter)
        Log.d(TAG,list.toString())
        return list
    }
}