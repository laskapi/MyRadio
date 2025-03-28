package com.laskapi.myradio.viewmodel

import androidx.media3.common.MediaMetadata
import androidx.media3.common.Metadata

data class PlayerState(
    var isPlaying: Boolean =false,
    var metadata: MediaMetadata =MediaMetadata.Builder().setStation("").build()

)