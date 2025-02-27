package com.laskapi.myradio.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.laskapi.myradio.room.Mirror

@Dao
interface MirrorDao {

    @Query("SELECT * FROM mirrors")
    fun getAll():List<Mirror>

    @Insert
    fun insertAll(mirrors: List<Mirror>)

    @Query("DELETE FROM mirrors")
    fun deleteAll()


}