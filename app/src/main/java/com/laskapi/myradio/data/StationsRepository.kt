package com.laskapi.myradio.data

import javax.inject.Inject


data class StationHeader(
    val name:String,
    val stationuuid:String
)

interface StationsDataSource {
    suspend fun fetchRecommendedStations():List<StationHeader>
}

class StationsRepository @Inject constructor (private val dataSource:
        StationsDataSource){
    suspend fun fetchRecommendedStations():List<StationHeader> =
        dataSource.fetchRecommendedStations()
}