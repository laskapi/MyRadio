package com.laskapi.myradio.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Ignore

@Entity(tableName = "favorites")
data class StationModel(

    @PrimaryKey
    val stationuuid: String,
    val name: String,
    val favicon: String,
    val url: String,

    )