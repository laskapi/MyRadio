package com.laskapi.myradio.ui.search


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.laskapi.myradio.ui.root.BottomNavItem
import com.laskapi.myradio.viewmodel.MViewModel
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(viewModel: MViewModel) {

    val stationsList by viewModel.stations.collectAsStateWithLifecycle()
    val favoritesList by viewModel.favorites.collectAsStateWithLifecycle()
    var searchText by rememberSaveable { mutableStateOf("") }
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column {
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .fillMaxWidth(),
        ) {

            //    val localFocusManager=LocalFocusManager.current

            TextField(
                singleLine = true,
                value = searchText,
                onValueChange = {
                    coroutineScope.launch {
                        searchText = it
                        lazyListState.scrollToItem(0)
                        viewModel.getStations(searchText)

                    }
                },
                shape = MaterialTheme.shapes.medium,// RoundedCornerShape(15.dp),

                placeholder = { Text("type to search") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                colors = OutlinedTextFieldDefaults.colors().copy(
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = MaterialTheme.colorScheme.background,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(8.dp),
                /*keyboardActions = KeyboardActions(onAny = {
                                       localFocusManager.clearFocus()
                                   viewModel.getStations(text)
                               }
                               ),
                               keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search,)
               */
            )
        }
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme
                        .colorScheme.surfaceContainer
                ),
        ) {
            items(stationsList) { station ->
                SearchStationItem(
                    station, favoritesList.contains(station),
                    {
                        viewModel
                            .selectStation(station, BottomNavItem.Search)
                    },
                    {
                        viewModel
                            .addToFavorites(station)
                    }, { viewModel.removeFromFavorites(station) }

                )
            }
        }
    }
}
