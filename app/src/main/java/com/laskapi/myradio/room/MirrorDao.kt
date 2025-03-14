package com.laskapi.myradio.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.laskapi.myradio.model.MirrorModel

@Dao
interface MirrorDao {

    @Query("SELECT * FROM mirrors")
    fun getAll():List<MirrorModel>

    @Insert
    fun insertAll(mirrors: List<MirrorModel>)

    @Query("DELETE FROM mirrors")
    fun deleteAll()


}