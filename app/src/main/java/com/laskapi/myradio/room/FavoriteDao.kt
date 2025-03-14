package com.laskapi.myradio.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.laskapi.myradio.model.StationModel

@Dao
interface FavoriteDao {

    @Insert
    fun insertFavorite(station:StationModel)

    @Query("SELECT * FROM favorites")
    fun getFavorites():List<StationModel>

    @Delete
    fun deleteFavorite(station:StationModel)
}
