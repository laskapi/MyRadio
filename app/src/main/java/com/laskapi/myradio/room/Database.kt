package com.laskapi.myradio.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Mirror::class], version = 1)
abstract class Database: RoomDatabase() {

    abstract fun getMirrorDao():MirrorDao
}