package com.rohmat.tmdb_android.presentation.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.rohmat.tmdb_android.domain.model.Movie
import com.rohmat.tmdb_android.presentation.components.MoviePosterItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMovieClick: (Int) -> Unit,
    onFavoriteIconClick: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val popular = viewModel.popularMovies.collectAsLazyPagingItems()
    val topRated = viewModel.topRatedMovies.collectAsLazyPagingItems()
    val nowPlaying = viewModel.nowPlayingMovies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("MOVIE") },
                actions = {
                    IconButton(onClick = onFavoriteIconClick) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Favorite Movies")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(vertical = 8.dp)
        ) {
            MovieSection(title = "Popular Movie") {
                MovieRow(popular, onMovieClick, posterWidth = 180.dp)
            }
            MovieSection(title = "Top Rated") {
                MovieRow(topRated, onMovieClick)
            }
            MovieSection(title = "Now Playing") {
                MovieRow(nowPlaying, onMovieClick)
            }
        }
    }
}

@Composable
private fun MovieSection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = title, modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
        content()
    }
}

@Composable
private fun MovieRow(
    items: LazyPagingItems<Movie>,
    onMovieClick: (Int) -> Unit,
    posterWidth: Dp = 120.dp
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(items.itemCount, key = items.itemKey { it.id }) { index ->
            val movie = items[index]
            if (movie != null) {
                MoviePosterItem(
                    movie = movie,
                    modifier = Modifier.clickable { onMovieClick(movie.id) },
                    posterWidth = posterWidth
                )
            }
        }
    }
}
