package com.laskapi.myradio.data

import retrofit2.http.GET
import retrofit2.http.Query

interface StationsRetrofitService {

    @GET("/search")
    suspend fun getStationsByName(@Query("name") name: String): List<StationHeader>
}