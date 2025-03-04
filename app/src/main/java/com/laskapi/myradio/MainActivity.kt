package com.laskapi.myradio

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.laskapi.myradio.data.MirrorsRepository
import com.laskapi.myradio.data.StationHeader
import com.laskapi.myradio.room.Database
import com.laskapi.myradio.ui.RootComposable
import com.laskapi.myradio.ui.SearchScreen
import com.laskapi.myradio.ui.theme.MyRadioTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

/*
    @Inject
    lateinit var mirrorsRepository: MirrorsRepository
    @Inject
    lateinit var database : Database
*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyRadioTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RootComposable()
                //  Greeting("Android", mirrorsRepository,database)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, mirrorsRepo: MirrorsRepository,database: Database,
             modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name, it is your mirror: $mirrorsRepo!",
        modifier = modifier
    )
    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }

    LaunchedEffect(Unit) {
        async(Dispatchers.IO+coroutineExceptionHandler) {
            if (BuildConfig.DEBUG) {
                database.clearAllTables();
            }
       //     Log.d(TAG, mirrorsRepo.getMirror())
             }.await()
    }
}



