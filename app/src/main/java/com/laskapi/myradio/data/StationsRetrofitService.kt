package com.laskapi.myradio.data

import retrofit2.http.GET
import retrofit2.http.Query

interface StationsRetrofitService {

    @GET("/json/stations/search?order=clickcount&limit=30")
    suspend fun getStationsByFilter(@Query("name") filter: String): List<StationHeader>

    @GET("/json/stations/search?order=clickcount&limit=30")
    suspend fun getRecommendedStations(@Query("country") displayCountry: String):
            List<StationHeader>
}