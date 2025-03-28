package com.laskapi.myradio.ui.root

import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import com.laskapi.myradio.TAG
import com.laskapi.myradio.viewmodel.PlayerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow


private const val INFOTEXT_CHANGE_INTERVAL = 3000L

@Composable
fun PlayerBar(
    modifier: Modifier = Modifier, playerState: StateFlow<PlayerState>,
    togglePlay: () -> Unit, previous: () -> Unit, next: () -> Unit,
    playerController: MediaController
) {
    val state by playerState.collectAsStateWithLifecycle()
    var infotext by remember { mutableStateOf("") }
    val metaDataList = mutableListOf<String>()

    playerController.addListener(object : Player.Listener {
        override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
            metaDataList.clear()
            mediaMetadata.station?.also { metaDataList.add(it.toString()) }
            mediaMetadata.albumArtist?.also { metaDataList.add(it.toString()) }
            mediaMetadata.albumTitle?.also { metaDataList.add(it.toString()) }
            mediaMetadata.artist?.also { metaDataList.add(it.toString()) }
            mediaMetadata.displayTitle?.also { metaDataList.add(it.toString()) }
            mediaMetadata.title?.also { metaDataList.add(it.toString()) }
            mediaMetadata.subtitle?.also { metaDataList.add(it.toString()) }
            mediaMetadata.composer?.also { metaDataList.add(it.toString()) }
            mediaMetadata.conductor?.also { metaDataList.add(it.toString()) }
            mediaMetadata.description?.also { metaDataList.add(it.toString()) }
            mediaMetadata.genre?.also { metaDataList.add(it.toString()) }
            metaDataList.removeAll { it.isEmpty() }

        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            infotext=mediaItem?.mediaMetadata?.station.toString()

        }
    })


    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(8.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.primaryContainer),


        ) {
        Box(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 0.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center
        ) {

            LaunchedEffect(Unit) {
                var iter = 0
                while (true) {
                    //      Log.d(TAG, "inside2:: $metaDataList}")
                    if (metaDataList.isNotEmpty()) {
                        iter = iter % metaDataList.size
                        infotext = metaDataList.get(iter)
                        iter++
                    }
                    delay(INFOTEXT_CHANGE_INTERVAL)
                }
            }
              AnimatedContent(targetState = infotext)
            {
                  info->
            Text(
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                text = info,
                color = MaterialTheme.colorScheme.onPrimary
            )
            }


         //   state.metadata.also {

                /*     Text(modifier = Modifier.padding(8.dp),
                         textAlign = TextAlign.Center,
                         text = it.station.toString(),
                         color = MaterialTheme.colorScheme.onPrimary
                     )
                */     /*
                                        AsyncImage(
                                            model = it.artworkUri, contentDescription = "artwork",
                                            modifier = Modifier
                                                .clip(MaterialTheme.shapes.medium)
                                                .height(36.dp), contentScale = ContentScale.FillHeight
                                        )
                        */
          //  }

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = { previous() },
//                shape = MaterialTheme.shapes.medium,
//                contentPadding = PaddingValues(0.dp),
                //               modifier = Modifier.size(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )

            ) {
                Icon(
                    painter = painterResource(
                        androidx.media3.session.R.drawable.media3_icon_previous
                    ), contentDescription = stringResource(
                        androidx.media3.session.R.string.media3_controls_seek_to_previous_description
                    )
                )
            }

            Button(
                onClick = {
                    togglePlay()
                },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.padding(8.dp)/*.size(40.dp)*/,
                /*    border = BorderStroke(
                        width = 3.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    ),
               */      colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,

                    )
            ) {
                if (state.isPlaying) {
                    Icon(
                        painter = painterResource(
                            androidx.media3.session.R.drawable
                                .media3_icon_pause
                        ),
                        stringResource(androidx.media3.session.R.string.media3_controls_pause_description),
                        modifier = Modifier
                            .padding(8.dp)/*aspectRatio(1f).*/.size(48.dp)
                    )
                } else {
                    Icon(
                        painter = painterResource(
                            androidx.media3.session.R.drawable
                                .media3_icon_play
                        ),
                        stringResource(androidx.media3.session.R.string.media3_controls_pause_description),
                        modifier = Modifier
                            .padding(8.dp)/*aspectRatio(1f).*/.size(48.dp)
                    )
                }
            }

            Button(
                onClick = { next() },
                //shape = CircleShape,
                //     contentPadding = PaddingValues(0.dp),
                //     modifier = Modifier.size(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(
                    painter = painterResource(
                        androidx.media3.session.R.drawable.media3_icon_next
                    ), contentDescription = stringResource(
                        androidx.media3.session.R.string.media3_controls_seek_to_next_description
                    )
                )
            }

        }
    }
}