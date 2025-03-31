package com.laskapi.myradio.player

import androidx.media3.common.Player

data class PlayerState(
    var isPlaying: Boolean = false, var state: Int = Player.STATE_READY,
    var metadataList: MutableList<String> = mutableListOf(),

)


