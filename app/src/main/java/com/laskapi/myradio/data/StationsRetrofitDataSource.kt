package com.laskapi.myradio.data

import android.util.Log
import androidx.compose.ui.text.intl.Locale
import com.laskapi.myradio.TAG
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class StationsRetrofitDataSource @Inject constructor(
    private val stationsService: StationsRetrofitService,
    private val ioDispatcher: CoroutineDispatcher
) : StationsDataSource {
    override suspend fun fetchStations(filter: String): List<StationHeader> =
        withContext(ioDispatcher) {

            if (filter.isEmpty()) {
                val country=/*"Poland"*/Locale.current.platformLocale.displayCountry
                stationsService.getRecommendedStations(country)
            } else {
                stationsService.getStationsByFilter(filter)
            }
        }
}
