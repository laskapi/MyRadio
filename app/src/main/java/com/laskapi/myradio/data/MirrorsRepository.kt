package com.laskapi.myradio.data

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
