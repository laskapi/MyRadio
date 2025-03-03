package com.laskapi.myradio.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.laskapi.myradio.data.StationHeader


@Composable
fun StationItem(it: StationHeader) {
    Row(modifier=Modifier.clickable{/*TODO play */}
        .fillMaxWidth()
        .padding(8.dp)) {

        AsyncImage(
            model = it.favicon,
            contentDescription = it.favicon,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(40.dp)

            )
        Spacer(modifier = Modifier.width((8.dp)))

        Text(
            text = it.name,
            style=MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(
                start = 8.dp,
                top = 4.dp,
                end = 8.dp,
                bottom = 4.dp
            )
        )


    }
}