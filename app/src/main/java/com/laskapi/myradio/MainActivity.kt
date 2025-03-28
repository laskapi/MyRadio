package com.laskapi.myradio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.laskapi.myradio.ui.root.RootComposable
import com.laskapi.myradio.ui.theme.MyRadioTheme
import dagger.hilt.android.AndroidEntryPoint

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
/*

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
            Log.d(TAG, mirrorsRepo.getMirror())
             }.await()
    }
}



*/
