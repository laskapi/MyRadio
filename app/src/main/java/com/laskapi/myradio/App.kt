package com.laskapi.myradio

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App:Application() {
   }

val Any.TAG: String
    get() {
        return if (!javaClass.isAnonymousClass) {
            val name = MY_TAG+javaClass.simpleName
            if (name.length <= 23) name else name.substring(0, 23)// first 23 chars
        } else {
            val name = MY_TAG+javaClass.name
            if (name.length <= 23) name else name.substring(name.length - 23, name.length)// last 23 chars
        }
    }


  const val MY_TAG="MY_TAG"


