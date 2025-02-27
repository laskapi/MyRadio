package com.laskapi.myradio.data

import android.util.Log
import com.laskapi.myradio.TAG
import com.laskapi.myradio.room.Mirror
import java.net.InetAddress
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MirrorsProvider @Inject constructor() {
    private val HOST = "all.api.radio-browser.info"
    private var mirrors: ArrayList<Mirror>? = null

    fun getMirror(): Mirror? {
        if (mirrors == null) {
            mirrors = fetchMirrors().let { ArrayList(it) }
        }

        Log.d(TAG,mirrors.toString())
        var result:Mirror? = null
        result = mirrors?.random()
        mirrors?.remove(result)
        return result
    }


    private fun fetchMirrors(): List<Mirror> {
        return InetAddress.getAllByName(HOST).map {
            Mirror(address = "https://"+it.hostAddress)
        }
    }
}