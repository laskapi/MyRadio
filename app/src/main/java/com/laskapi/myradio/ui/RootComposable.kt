package com.laskapi.myradio.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.laskapi.myradio.viewmodel.MViewModel

import androidx.lifecycle.viewmodel.compose.viewModel
import com.laskapi.myradio.ui.favorites.FavoritesScreen
import com.laskapi.myradio.ui.search.SearchScreen

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
        contentWindowInsets = WindowInsets.systemBars,
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
                            Icon(painter = painterResource(item.iconId), contentDescription = item
                                .label)
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
                       /* colors = NavigationBarItemDefaults.colors(
                            selectedTextColor = MaterialTheme.colorScheme.surface,
                            selectedIconColor = MaterialTheme.colorScheme.surface,
                            indicatorColor = MaterialTheme.colorScheme.primary
                        )*/
                    )

                }
            }
        }

    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth()
                .background(
                    color = MaterialTheme
                        .colorScheme.surfaceContainer
                ),
            horizontalAlignment = Alignment
                .CenterHorizontally
        ) {
            NavHost(
                modifier = Modifier.weight(1f),
                navController = navController, startDestination = BottomNavItem.Home
            ) {
                composable<BottomNavItem.Search> {
                    SearchScreen(
                    viewModel
                    /*viewModel.stations,
                        {
                            viewModel.getStations(it)
                        }, {
                            viewModel.selectStation(it)
                        }*/
                    )
                }
                composable<BottomNavItem.Home> { FavoritesScreen(viewModel) }
                composable<BottomNavItem.Alarm> { AlarmScreen() }
            }

            PlayerBar(
                modifier = Modifier.fillMaxWidth(0.98f),viewModel.isPlaying
            ) {
                viewModel.togglePlay()
            }/*viewModel
            .player,*/
            /*     viewModel.streamUrl*/
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

