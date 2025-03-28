package com.laskapi.myradio.player

import android.content.ComponentName
import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import com.laskapi.myradio.viewmodel.LAST_FAVORITE_INDEX
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max

class PlayerController @Inject constructor(@ApplicationContext ctx: Context) {

    private val sessionToken = SessionToken(
        ctx, ComponentName(
            ctx, PlaybackService::class.java
        )
    )
    private val controllerFuture = MediaController.Builder(ctx, sessionToken).buildAsync()
    val playerController: MediaController = controllerFuture.get()
    val errorMessage: MutableStateFlow<String?> = MutableStateFlow("")

    init {
        controllerFuture.addListener(
            {
                playerController.addListener(object : Player.Listener {
                    private var shouldResumePlaying: Boolean = false

                    override fun onIsPlayingChanged(playing: Boolean) {
                        _playerState.update { state ->
                            state.copy(isPlaying = playing)
                        }
                        super.onIsPlayingChanged(playing)
                    }

                    override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                        if (mediaMetadata.station != null) {
                            _playerState.update { state ->
                                state.copy(metadata = mediaMetadata)
                            }
                        }
                        super.onMediaMetadataChanged(mediaMetadata)
                    }

                    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {

                        if (shouldResumePlaying) {
                            playerController.play()
                            shouldResumePlaying = false
                            errorMessage.value = null
                        }
                        itemToRemoveIndex?.let { playerController.removeMediaItem(it) }
                        itemToRemoveIndex = null

                        val currentIndexInFavorites = favorites.value.indexOfFirst {
                            it.stationuuid
                                .equals(mediaItem?.mediaId)
                        }

                        viewModelScope.launch {
                            dataStoreRepository.putInt(
                                LAST_FAVORITE_INDEX, max(currentIndexInFavorites, 0)
                            )
                        }

                    }

                    override fun onPlayerError(error: PlaybackException) {
                        shouldResumePlaying = true
                        errorMessage.value = error.message.toString()
                    }

                })
            }, MoreExecutors.directExecutor()
        )
    }
}