package com.laskapi.myradio.ui.favorites

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.laskapi.myradio.ui.root.BottomNavItem
import com.laskapi.myradio.viewmodel.MViewModel

@Composable
fun FavoritesScreen(viewModel: MViewModel) {
    val favoritesList by viewModel.favorites.collectAsStateWithLifecycle()

    val textFieldHeight=with(LocalDensity.current){
        MaterialTheme.typography.bodyLarge.lineHeight.toDp().times(3)
    }
    LazyVerticalGrid(columns = GridCells.Adaptive(128.dp)) {
        items(favoritesList) { station ->
            FavoriteStationItem(station, { viewModel.removeFromFavorites(station)},
                textFieldHeight,{viewModel.selectStation(station, BottomNavItem.Home)} )
        }
    }

}

