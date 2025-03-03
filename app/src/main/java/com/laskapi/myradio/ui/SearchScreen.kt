package com.laskapi.myradio.ui

import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.laskapi.myradio.TAG
import com.laskapi.myradio.data.StationHeader
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    stations: StateFlow<List<StationHeader>>, getStations: (String) -> Unit
) {
    val stationsList by stations.collectAsStateWithLifecycle()

    var text by rememberSaveable { mutableStateOf("") }

    Column {
        Surface(//modifier=Modifier.padding(8.dp),
//    shadowElevation = 5.dp,
            // shape = RoundedCornerShape(25.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 3.dp
        ) {

            OutlinedTextField(
                singleLine = true,
                value = text,
                onValueChange = {
                    text = it
                },
                shape = RoundedCornerShape(25.dp),

                placeholder = { Text("type to search") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },

                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(8.dp)
                    .onKeyEvent {
                        if (
                            it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                            getStations(text)
                            Log.d(TAG, "Typing enter")
                            return@onKeyEvent true
                        }
                        return@onKeyEvent false
                    }


            )
}
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(stationsList) { item ->
                    StationItem(item)
                }
            }
        }
    }

