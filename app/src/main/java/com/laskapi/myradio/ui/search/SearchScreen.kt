package com.laskapi.myradio.ui.search


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.laskapi.myradio.ui.theme.MyRadioTheme
import com.laskapi.myradio.viewmodel.MViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel:MViewModel
  /*  stations: StateFlow<List<StationHeader>>, getStations: (String) -> Unit, selectStation:
        (String) -> Unit, updateFavorites:(StationHeader,Boolean)->Unit*/) {

    val stationsList by viewModel.stations.collectAsStateWithLifecycle()

    var text by rememberSaveable { mutableStateOf("") }

    Column {
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .fillMaxWidth(),
        ) {

            val localFocusManager=LocalFocusManager.current

            TextField(
                singleLine = true,
                value = text,
                onValueChange = {
                    text = it
                    viewModel.getStations(text)
                },
                shape =MaterialTheme.shapes.medium,// RoundedCornerShape(15.dp),

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
                    .padding(8.dp), /*keyboardActions = KeyboardActions(onAny = {
                        localFocusManager.clearFocus()
                    viewModel.getStations(text)
                }
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search,)
*/
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme
                        .colorScheme.surfaceContainer
                ),
        ) {
            items(stationsList) { station ->
                SearchStationItem(
                    station,viewModel.isFavorite(station), {viewModel.selectStation(it)}, {viewModel
                        .addToFavorites(station)},{viewModel.removeFromFavorites(station)}

                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyRadioTheme {
        SearchScreen(viewModel = viewModel()/*
            MutableStateFlow<List<StationHeader>>(emptyList()).asStateFlow(), {}, {},
            updateFavorites = TODO(),*/
        )
    }
}