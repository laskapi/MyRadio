package com.laskapi.myradio.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class StationModel(
 /*   @PrimaryKey(autoGenerate = true)
    val Id:Int=0,
*/
 @PrimaryKey
 val stationuuid: String,
 val name: String,
    val favicon: String,
    val url: String
)