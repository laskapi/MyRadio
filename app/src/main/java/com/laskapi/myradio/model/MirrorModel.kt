package com.laskapi.myradio.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mirrors")
data class MirrorModel(
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val address:String?
)
