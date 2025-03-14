package com.laskapi.myradio.data

import com.laskapi.myradio.model.StationModel
import com.laskapi.myradio.room.FavoriteDao
import javax.inject.Inject

class FavoritesDataSource @Inject constructor(val favoriteDao: FavoriteDao) {

 fun addToFavorite(station: StationModel): List<StationModel> {
        favoriteDao.insertFavorite(station)
        return getAllFavorites()
    }

    fun removeFromFavorites(station: StationModel): List<StationModel> {
        favoriteDao.deleteFavorite(station)
        return getAllFavorites()

    }

    fun getAllFavorites(): List<StationModel> {
        return favoriteDao.getFavorites()
    }

}
