package com.laskapi.myradio.data

import android.net.DnsResolver
import android.util.Log
import com.laskapi.myradio.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetAddress
import java.util.concurrent.Executor
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class MirrorsRemoteDataSource @Inject constructor()/*:Executor*/ {
    private val HOST = "all.api.radio-browser.info"

    fun fetchMirrors() : Array<out InetAddress>? {
        return InetAddress.getAllByName(HOST)
    }

    /*   suspend*//* fun fetchMirrors()*//*: Array<out InetAddress>?*//* {

        //    return withContext(Dispatchers.IO) {
        // async(Dispatchers.IO) {

     //   suspendCoroutine {
            DnsResolver.getInstance()
                .query(null,
                    HOST,
                    DnsResolver.FLAG_NO_RETRY,
   this,//                 this@MirrorsRemoteDataSource,
                    null,
                    object : DnsResolver.Callback<Collection<InetAddress>> {
                        override fun onAnswer(answer: Collection<InetAddress>, rcode: Int) {
                            Log.d(TAG, answer.toString())
                        }
                         //   return answer

                            //it.resume(answer.toTypedArray())

                        override fun onError(error: DnsResolver.DnsException) {}//=
                          //  it.resumeWithException(IOException(error))
                    })
 //       }

    }

override fun execute(command: Runnable?) {
    CoroutineScope(Dispatchers.IO).launch {
        command?.run()
    }
}*/

}
