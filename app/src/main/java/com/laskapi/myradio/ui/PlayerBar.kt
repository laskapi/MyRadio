package com.laskapi.myradio.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.laskapi.myradio.TAG
import kotlinx.coroutines.flow.StateFlow


@Composable
fun PlayerBar(
    modifier: Modifier = Modifier, isPlaying: StateFlow<Boolean>, togglePlay: () -> Unit
) {
    val isPlay by isPlaying.collectAsStateWithLifecycle()

    Row(
        modifier = modifier
            .fillMaxWidth()
    //        .height(IntrinsicSize.Min)
            .padding(8.dp)
          //  .wrapContentHeight(Alignment.Bottom)
           .clip(shape = MaterialTheme.shapes.medium/*RoundedCornerShape(15.dp)*/)
            .background(MaterialTheme.colorScheme.primaryContainer)
        ,horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically
    ) {

        FloatingActionButton(onClick = {
            togglePlay()
        },
            modifier=Modifier.padding(8.dp),
            contentColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.onPrimary) {
            if (isPlay) {

                Icon(
                    painter = painterResource(androidx.media3.session.R.drawable.media3_icon_stop),
                    "Play",
                    modifier = Modifier
                        .padding(8.dp)
                         /*aspectRatio(1f).*/.size(48.dp)
                )
            } else {
                Icon(
                    painter = painterResource(androidx.media3.session.R.drawable.media3_icon_play),
                    "Stop",
                    modifier = Modifier
                        .padding(8.dp)

                        /*aspectRatio(1f).*/.size(48.dp)
                )
            }
        }

    }
}