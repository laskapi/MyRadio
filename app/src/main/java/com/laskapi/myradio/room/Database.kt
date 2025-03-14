package com.laskapi.myradio.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.laskapi.myradio.model.MirrorModel
import com.laskapi.myradio.model.StationModel

@Database(entities = [MirrorModel::class, StationModel::class], version = 1)
abstract class Database: RoomDatabase() {

    abstract fun getMirrorDao():MirrorDao
    abstract fun getFavoriteDao():FavoriteDao
}