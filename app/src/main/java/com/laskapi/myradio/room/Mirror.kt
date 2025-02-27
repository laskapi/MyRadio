package com.laskapi.myradio.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mirrors")
data class Mirror(
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val address:String?
)
