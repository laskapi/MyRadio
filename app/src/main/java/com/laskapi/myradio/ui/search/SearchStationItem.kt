package com.laskapi.myradio.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.laskapi.myradio.R
import com.laskapi.myradio.model.StationModel
import kotlinx.coroutines.Job


@Composable
fun SearchStationItem(
    stationModel: StationModel,
    isFavorite: Boolean,
    selectStation: (String) -> Unit,
    addToFavorites:
        () -> Unit,
    removeFromFavorites: () -> Unit
) {
    Card(
        modifier = Modifier
            //    .height(IntrinsicSize.Min)
            .padding(6.dp)
            .clickable { selectStation(stationModel.url) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),

        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(80.dp, 40.dp), contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = stationModel.favicon,
                    contentDescription = stationModel.favicon,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .height(40.dp)
                    //                    .size(100.dp,40.dp)
                    //.weight(0.5f)
                    , contentScale = ContentScale.FillHeight//, clipToBounds = true
                )
            }
            Spacer(modifier = Modifier.width((8.dp)))

            Text(
                text = stationModel.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.weight(0.1f))

            var favorite by remember { mutableStateOf(isFavorite) }
            Button(
                onClick = {
                    favorite = !favorite
                    if (favorite) {
                        addToFavorites()
                    } else {
                        removeFromFavorites()
                    }
                },
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.primary
                )

            ) {
                if (favorite) {
                    Icon(painterResource(R.drawable.star_24px_filled), "Favorite")
                } else {
                    Icon(painterResource(R.drawable.star_24px), "Favorite")

                }

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun StationItemPreview() {
    // StationItem(StationModel(0,"fewf", "fewf", "fefw", "dffsd"), {},{ })
}