import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.ui.PlayerView

@Composable
fun PlayerViewComposableIUnused(playerView: PlayerView) {
    // if (playerController != null) {
    AndroidView(
        factory = { context ->
            PlayerView(context).apply { /*player = playerController */}
        },
        update = { player ->
            player.player?.setMediaItem(MediaItem.fromUri("streamUrl"))
        }
    )
//    }
}