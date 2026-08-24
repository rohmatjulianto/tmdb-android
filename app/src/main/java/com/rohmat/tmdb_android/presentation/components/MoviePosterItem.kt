package com.rohmat.tmdb_android.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rohmat.tmdb_android.domain.model.Movie
import com.rohmat.tmdb_android.util.Constants

@Composable
fun MoviePosterItem(
    movie: Movie,
    modifier: Modifier = Modifier,
    posterWidth: Dp = 120.dp
) {
    val posterHeight = posterWidth * 3 / 2
    Column(modifier = modifier.width(posterWidth)) {
        AsyncImage(
            model = movie.posterPath?.let { Constants.IMAGE_BASE_URL + it },
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(posterWidth)
                .height(posterHeight)
                .clip(RoundedCornerShape(8.dp))
        )
        Text(text = movie.title, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = movie.releaseDate, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}
