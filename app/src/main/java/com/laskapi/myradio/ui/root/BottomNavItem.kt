package com.laskapi.myradio.ui.root

import android.content.Context
import com.laskapi.myradio.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
sealed class BottomNavItem(
    val route: String, val iconId: Int, val label: String
) {
    @Contextual
    @ApplicationContext lateinit var ctx: Context

    @Serializable
    object Search : BottomNavItem("search", R.drawable.search_24px, "Search")

    @Serializable
    object Home : BottomNavItem("home", R.drawable.star_24px, "Favorites")

    @Serializable
    object Alarm : BottomNavItem("alarm", R.drawable.alarm_24px,"Alarm")


    companion object {
        fun getItems(): List<BottomNavItem> {
            return listOf(Search, Home, Alarm)
        }
    }
}