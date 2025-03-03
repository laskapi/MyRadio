package com.laskapi.myradio.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
sealed class BottomNavItem(val route: String, val icon: @Contextual ImageVector, val label:
String) {
    @Serializable
    object Search : BottomNavItem("search", Icons.Default.Search, "Search")
    @Serializable
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    @Serializable
    object Alarm : BottomNavItem("alarm", Icons.Default.Person, "Alarm")

    companion object{
          fun getItems():List<BottomNavItem>{
         return listOf(Search,Home,Alarm)
     }}
}