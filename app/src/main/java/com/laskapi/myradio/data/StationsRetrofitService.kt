package com.laskapi.myradio.data

import com.laskapi.myradio.model.StationModel
import retrofit2.http.GET
import retrofit2.http.Query

interface StationsRetrofitService {

    @GET("/json/stations/search?order=votes&limit=30")
    suspend fun getStationsByFilter(@Query("name") filter: String): List<StationModel>

    @GET("/json/stations/search?order=votes&limit=30")
    suspend fun getRecommendedStations(@Query("countrycode" +
            "") displayCountry: String):
            List<StationModel>
}