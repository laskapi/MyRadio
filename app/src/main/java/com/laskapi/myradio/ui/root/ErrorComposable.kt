package com.laskapi.myradio.ui.root

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.laskapi.myradio.R

@Composable
fun ErrorComposable(text:String,onClick:()->Unit) {
    Surface(
        onClick = {onClick()},
        shadowElevation = 16.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .zIndex(1f)
            .fillMaxWidth(0.8f)
            .fillMaxHeight(0.3f)
            .wrapContentHeight()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                textAlign = TextAlign
                    .Center,
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 5,
                softWrap = true,
            )
            Icon(
                modifier = Modifier
                    .padding(0.dp, 16.dp, 16.dp, 16.dp)
                    .size(48.dp),
                imageVector = Icons.Default.Warning, contentDescription =
                stringResource(R.string.warning),
                tint = Color.Red
            )

        }
    }
}