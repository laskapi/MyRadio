package com.laskapi.myradio.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.laskapi.myradio.viewmodel.MViewModel

import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RootComposable(viewModel: MViewModel = viewModel()) {
    val navController = rememberNavController()

    /*
        val selectedNavigationIndex = rememberSaveable {
            mutableIntStateOf(0)
        }
    */


    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            NavigationBar {
                BottomNavItem.getItems().forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = false,// selectedNavigationIndex.intValue == index,
                        onClick = {
                            //         selectedNavigationIndex.intValue = index
                            navController.navigate(
                                route = item
                            )
                        },
                        icon = {
                            Icon(imageVector = item.icon, contentDescription = item.label)
                        },

                        label = {
                            Text(
                                item.label,
                                /*                   color = if (index == selectedNavigationIndex.intValue)
                                                       Color.Black
                                                   else Color.Gray
                                */
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.surface,
                            indicatorColor = MaterialTheme.colorScheme.primary
                        )
                    )

                }
            }
        }

    ) { it ->
        it.calculateTopPadding()
        NavHost(navController = navController, startDestination = BottomNavItem.Home) {
            composable<BottomNavItem.Home> { HomeScreen() }
            composable<BottomNavItem.Search> {
                SearchScreen(viewModel.stations) {
                    viewModel
                        .getStations(it)
                }
            }
            composable<BottomNavItem.Alarm> { AlarmScreen() }
        }
    }

}

/*
@Composable
fun RootComposable(viewModel: MViewModel = viewModel()) {

    val stations by viewModel.stations.collectAsStateWithLifecycle()
    Text("I am")
    Column {
        for (s in stations) {
            Text(s.name)
        }


    }

}
*/

