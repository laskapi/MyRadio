package com.laskapi.myradio.data

import com.laskapi.myradio.model.StationModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class StationsRetrofitDataSource @Inject constructor(
    private val stationsService: StationsRetrofitService,
    private val ioDispatcher: CoroutineDispatcher
) : StationsDataSource {
    override suspend fun fetchStations(filter: String): List<StationModel> =
        withContext(ioDispatcher) {

            if (filter.isEmpty()) {
                val country="PL"/*"Poland"*/ //Locale.current.platformLocale.country//displayCountry
                stationsService.getRecommendedStations(country)
            } else {
                stationsService.getStationsByFilter(filter)
            }
        }
}
