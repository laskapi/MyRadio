package com.laskapi.myradio.ui.favorites

import androidx.compose.foundation.BasicTooltipState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.laskapi.myradio.R
import com.laskapi.myradio.model.StationModel

@Composable
fun FavoriteStationItem(
    stationModel: StationModel, removeFromFavorites: () -> Unit,
    myComputedTExtFieldHeight: Dp,
    selectStation: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable { selectStation(stationModel.url) },
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .aspectRatio(1f)
                    .clip(shape = MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.primary), contentAlignment = Alignment
                    .Center
            )
            {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth(), model = stationModel.favicon,
                    contentDescription = stationModel.name,
                    contentScale = ContentScale.FillWidth
                )
                FilledIconButton(
                    onClick = { removeFromFavorites() },
                    colors = IconButtonDefaults.outlinedIconButtonColors(
                        contentColor =
                        MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.7f)
                    ),
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close, contentDescription = stringResource(
                            R.string.remove
                        )
                    )
                }
            }

            Row(
                modifier = Modifier.height(myComputedTExtFieldHeight),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    maxLines = 3,
                    text = stationModel.name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                    //       color=MaterialTheme.colorScheme.surface
                )

            }
        }
    }
}