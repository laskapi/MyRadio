package com.laskapi.myradio.player

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.session.MediaSessionManager
import android.media.session.PlaybackState
import android.os.Bundle
import android.provider.MediaStore.Images.Media
import androidx.annotation.OptIn
import androidx.compose.material.icons.Icons
import androidx.core.os.bundleOf
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.CommandButton
import androidx.media3.session.DefaultMediaNotificationProvider
import androidx.media3.session.MediaConstants
import androidx.media3.session.MediaController
import androidx.media3.session.MediaNotification
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSession.ConnectionResult
import androidx.media3.session.MediaSession.ConnectionResult.AcceptedResultBuilder
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.ListenableFuture
import com.laskapi.myradio.R
import com.laskapi.myradio.TAG

class PlaybackService : MediaSessionService() {
    private var mediaSession: MediaSession? = null

    /*  private val COMMAND_NEXT = SessionCommand("ACTION_NEXT", Bundle.EMPTY)
      private val COMMAND_PREVIOUS = SessionCommand("ACTION_PREVIOUS", Bundle.EMPTY)
  */



    private val listener=
        MediaSessionManager.OnActiveSessionsChangedListener { list: MutableList<android.media
        .session.MediaController>? ->
            list?.forEach { mediaController ->
                Log.d(TAG,"BBBBBBBBBBBBBBBB")
                /*if (mediaController.playbackState?.state == PlaybackState.STATE_PLAYING ||
                    mediaController.playbackState?.state == PlaybackState.STATE_BUFFERING) {*/
                if (!mediaController.sessionToken.equals(mediaSession!!.token))
                    mediaController.transportControls.stop()
            }
        }


    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()
        val audioAttributes= AudioAttributes
            .Builder().setUsage(C.USAGE_MEDIA)
            .setContentType(C.AUDIO_CONTENT_TYPE_UNKNOWN)
            .setAllowedCapturePolicy(C.ALLOW_CAPTURE_BY_ALL)
            .build()

        val player = ExoPlayer.Builder(this).setName(getString(R.string.app_name))
            .setAudioAttributes(audioAttributes,true)
            .build()
        player.repeatMode = Player.REPEAT_MODE_ALL
//val am= getSystemService(Context.AUDIO_SERVICE) as AudioManager
  //      am.re

                //    msm.getActiveSessions(null).forEach{it.transportControls.stop()}

        /*      val previousButton = CommandButton.Builder().setDisplayName(
                  getString(
                      R.string.previousButton
                  )
              ).setIconResId(android.R.drawable.ic_media_previous).setSessionCommand(COMMAND_PREVIOUS).build()

              val nextButton = CommandButton.Builder().setDisplayName(getString(R.string.nextButton))
                  .setIconResId(android.R.drawable.ic_media_next).setSessionCommand(COMMAND_NEXT).build()
      */

        mediaSession = MediaSession.Builder(this, player)
            //          .setCustomLayout(ImmutableList.of(previousButton,nextButton)).setCallback
            //          (MyCallback())
            .build()

        //}

    }

    /*private inner class MyCallback : MediaSession.Callback {
        @OptIn(UnstableApi::class)
        override fun onConnect(
            session: MediaSession, controller: MediaSession.ControllerInfo
        ): ConnectionResult {
            return AcceptedResultBuilder(session).setAvailablePlayerCommands(
                ConnectionResult.DEFAULT_PLAYER_COMMANDS.buildUpon()
                     .add(Player.COMMAND_SEEK_TO_NEXT_MEDIA_ITEM)
                    .build()
            ).setAvailableSessionCommands(
                ConnectionResult.DEFAULT_SESSION_COMMANDS.buildUpon()
                     .add(COMMAND_PREVIOUS)
                    .add(COMMAND_NEXT)
                    .build()
            ).build()
        }

        override fun onCustomCommand(
            session: MediaSession,
            controller: MediaSession.ControllerInfo,
            customCommand: SessionCommand,
            args: Bundle
        ): ListenableFuture<SessionResult> {
                when(customCommand){
                    COMMAND_NEXT-> session.player.seekToNextMediaItem()
                    COMMAND_PREVIOUS-> session.player.seekToPreviousMediaItem()
                }
            return super.onCustomCommand(session, controller, customCommand, args)
        }
    }
*/
    override fun onGetSession(
        controllerInfo: MediaSession.ControllerInfo
    ): MediaSession? = mediaSession

    // Remember to release the player and media session in onDestroy
    override fun onDestroy() {
        mediaSession?.run {

            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        mediaSession?.run { { player.stop() } }
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
        //pauseAllPlayersAndStopSelf()
    }


}