package com.laskapi.myradio.ui.root

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
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
import androidx.media3.common.Player
import com.laskapi.myradio.player.PlayerController
import kotlinx.coroutines.delay


private const val INFOTEXT_CHANGE_INTERVAL = 3000L

@Composable
fun PlayerBar(
    playerController: PlayerController,
    modifier: Modifier = Modifier,

    ) {
    //   val metadataList by playerController.metadataList.collectAsStateWithLifecycle()
    //val isPlaying by playerController.isPlaying.collectAsStateWithLifecycle()
    val state by playerController.state.collectAsStateWithLifecycle()

    var infotext by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(8.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .background(colorScheme.primaryContainer),


        ) {
        Box(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 0.dp)
                .fillMaxWidth()
                .background(colorScheme.primary), contentAlignment = Alignment.Center
        ) {

            LaunchedEffect(state.metadataList) {
                var iter = 0
                while (true) {
                    //      Log.d(TAG, "inside2:: $metaDataList}")
                    if (state.metadataList.isNotEmpty()) {
                        iter = iter % state.metadataList.size
                        infotext = state.metadataList.get(iter)
                        iter++
                    }
                    delay(INFOTEXT_CHANGE_INTERVAL)
                }
            }
            AnimatedContent(targetState = infotext) { info ->
                Text(
                    minLines = 2, maxLines = 2,
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    text = info,
                    color = colorScheme.onPrimary
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = { playerController.playPrevious() },
//                shape = MaterialTheme.shapes.medium,
//                contentPadding = PaddingValues(0.dp),
                //               modifier = Modifier.size(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.primaryContainer,
                    contentColor = colorScheme.onPrimary
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
                    playerController.togglePlay()
                },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.padding(8.dp)/*.size(40.dp)*/,/*    border = BorderStroke(
                        width = 3.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    ),
               */
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.primary,
                    contentColor = colorScheme.onPrimary,

                    )
            ) {
                if (state.isPlaying) {
                    Icon(
                        painter = painterResource(
                            androidx.media3.session.R.drawable.media3_icon_pause
                        ),
                        stringResource(androidx.media3.session.R.string.media3_controls_pause_description),
                        modifier = Modifier
                            .padding(8.dp)/*.aspectRatio(1f)*/.size(48.dp)
                    )
                } else {
                    Box() {
                        Icon(
                            painter = painterResource(
                                androidx.media3.session.R.drawable.media3_icon_play
                            ),
                            stringResource(androidx.media3.session.R.string.media3_controls_pause_description),
                            modifier = Modifier
                                .padding(8.dp)/*.aspectRatio(1f)*/.size(48.dp)
                        )
                        if (state.state == Player.STATE_BUFFERING) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(64.dp),
                                color = colorScheme.onPrimary,
                                trackColor = colorScheme.surfaceVariant,
                            )
                        }
                    }
                }
            }

            Button(
                onClick = { playerController.playNext() },
                //shape = CircleShape,
                //     contentPadding = PaddingValues(0.dp),
                //     modifier = Modifier.size(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.primaryContainer,
                    contentColor = colorScheme.onPrimary
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