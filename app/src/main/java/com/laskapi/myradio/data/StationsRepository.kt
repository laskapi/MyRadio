package com.laskapi.myradio.data

import android.util.Log
import com.laskapi.myradio.TAG
import com.laskapi.myradio.model.StationModel
import javax.inject.Inject
import dagger.Lazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface StationsDataSource {
    suspend fun fetchStations(filter: String): List<StationModel>
}

class StationsRepository @Inject constructor(
    private val stationsDataSource:
    Lazy<StationsDataSource>,
    private val favoritesDataSource: FavoritesDataSource
) {
    suspend fun getStations(filter: String = ""): List<StationModel> {
        val list = withContext(Dispatchers.IO) {
            stationsDataSource.get()
        }.fetchStations(filter)
        Log.d(TAG, list.toString())
        return list
    }

    suspend fun addToFavorites(station: StationModel):List<StationModel> {
        return withContext(Dispatchers.IO) { favoritesDataSource.addToFavorite(station) }
    }

    suspend fun removeFavorite(station: StationModel):List<StationModel> {
      return  withContext(Dispatchers.IO) { favoritesDataSource.removeFromFavorites(station) }
    }

    suspend fun getFavorites(): List<StationModel> {
        return withContext(Dispatchers.IO) {
            favoritesDataSource.getAllFavorites()
        }
    }


}