import android.view.LayoutInflater
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.session.MediaController
import androidx.media3.ui.PlayerView
import com.laskapi.myradio.R


@Composable
fun PlayerViewComposable(mediaController:MediaController?) {
     if (mediaController != null) {
    AndroidView(
        modifier = Modifier.fillMaxHeight(0.2f),//height(128.dp),

        factory = { context ->
/*
            val parser: XmlPullParser = resources.getXml(myResource)
            val attributes = Xml.asAttributeSet(parser)
            val attr=Attri
*/
            val playerView = LayoutInflater.from(context).inflate(R.layout.player,null,false)as PlayerView


            //  view.setShowNextButton(true)
           // view.setShowPreviousButton(true)
            playerView.apply{player=mediaController}
           /* PlayerView(context).apply { player = mediaController
            }*/
        },
      /*  update = { player ->
            player.player?.setMediaItem(MediaItem.fromUri("streamUrl"))
        }*/
    )
    }
}