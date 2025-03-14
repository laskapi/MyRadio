package com.laskapi.myradio.data

import android.util.Log
import com.laskapi.myradio.TAG
import com.laskapi.myradio.model.MirrorModel
import java.net.InetAddress
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MirrorsProvider @Inject constructor() {
    private val HOST = "all.api.radio-browser.info"
    private var mirrors: ArrayList<MirrorModel>? = null

    fun getMirror(): MirrorModel? {
   //     return MirrorModel(address = "https://nl1.api.radio-browser.info")

        if (mirrors == null) {
            mirrors = fetchMirrors().let { ArrayList(it) }
        }

        Log.d(TAG, "Here is MirrorsProvider - I got these mirrors: "+mirrors.toString())
        var result: MirrorModel? = null
        result = mirrors?.random()
        mirrors?.remove(result)
        return /*Mirror(address = "https://nl1.api.radio-browser.info")*/result
    }


    private fun fetchMirrors(): List<MirrorModel> {
        return InetAddress.getAllByName(HOST).map {
            MirrorModel(address = "https://" + it.canonicalHostName)
        }
    }
}