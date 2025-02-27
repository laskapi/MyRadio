package com.laskapi.myradio.data

import android.util.Log
import com.laskapi.myradio.TAG
import com.laskapi.myradio.room.Mirror
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.withContext
import java.net.InetAddress
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MirrorsRepository @Inject constructor(
 //   private val mirrorsLocalDataSource: MirrorsLocalDataSource,
    private val mirrorsRemoteDataSource: MirrorsRemoteDataSource,
 //   private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun fetchMirrorUrl(): Array<out InetAddress>? =  mirrorsRemoteDataSource.fetchMirrors()


    /*suspend fun getMirror(): String {
        return withContext(ioDispatcher) {
            var mirrors = mirrorsLocalDataSource.getMirrors()

            if (mirrors.isEmpty()) {
                Log.d("TAG", "mirrors are empty")
                try {
                    mirrorsRemoteDataSource.fetchMirrors()?.also {
                        mirrorsLocalDataSource.save(it)
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

                mirrors = mirrorsLocalDataSource.getMirrors()
            }
                mirrors.random().address

        }
    }*/
}
