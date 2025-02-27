package com.laskapi.myradio.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class StationsRetrofitDataSource @Inject constructor(
    private val stationsService: StationsRetrofitService,
    private val ioDispatcher: CoroutineDispatcher
) : StationsDataSource {
    override suspend fun fetchRecommendedStations(): List<StationHeader> =
        withContext(ioDispatcher) {
            stationsService.getStationsByName("rmf")
        }
}
