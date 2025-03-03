package com.laskapi.myradio.ui

import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent


import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.laskapi.myradio.TAG
import com.laskapi.myradio.data.StationHeader
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenWithSearchBar(stations: StateFlow<List<StationHeader>>, getStations:
    (String)
->
Unit) {
    val stationsList by stations.collectAsStateWithLifecycle()

    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .onKeyEvent {
                    if (
                        it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                        getStations(text)
                        Log.d(TAG, "Typing enter")
                        return@onKeyEvent true
                    }
                    return@onKeyEvent false
                }
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchBarDefaults.InputField(
                    query = text,
                    onQueryChange = {
                        //      getStations(it)
                        text = it
                    },
                    onSearch = { expanded = false },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text("search for radio") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) },

                    )
            },
            expanded = expanded,
            onExpandedChange = { expanded = false/*it*/ }
        ) {}

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(stationsList) { item ->
                StationItem(item)
            }

        }


    }

}

