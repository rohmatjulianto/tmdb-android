package com.rohmat.tmdb_android.presentation.ui.screen.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rohmat.tmdb_android.util.Constants
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    movieId: Int,
    onBack: () -> Unit,
    viewModel: DetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.loadMovie(movieId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.movie?.title.orEmpty()) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    uiState.movie?.let { movie ->
                        IconButton(onClick = { viewModel.onFavoriteClick() }) {
                            Icon(
                                imageVector = if (movie.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = "Toggle Favorite"
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        when {
            uiState.isLoading -> CircularProgressIndicator(modifier = Modifier.padding(padding).padding(16.dp))
            uiState.movie != null -> {
                val movie = uiState.movie!!
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    AsyncImage(
                        model = movie.posterPath?.let { Constants.IMAGE_BASE_URL + it },
                        contentDescription = movie.title,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(text = "Title", modifier = Modifier.padding(top = 12.dp))
                    Text(text = movie.title)

                    Text(text = "Release date", modifier = Modifier.padding(top = 8.dp))
                    Text(text = movie.releaseDate)

                    Text(text = "Description", modifier = Modifier.padding(top = 12.dp))
                    Text(text = movie.overview)

                    Text(text = "Review", modifier = Modifier.padding(top = 12.dp))
                    if (uiState.reviews.isEmpty()) {
                        Text(text = "No reviews yet.")
                    } else {
                        uiState.reviews.forEach { review ->
                            Column(modifier = Modifier.padding(top = 8.dp)) {
                                Text(text = review.author)
                                Text(text = review.content)
                            }
                        }
                    }
                }
            }
            uiState.errorMessage != null -> {
                Text(
                    text = uiState.errorMessage.orEmpty(),
                    modifier = Modifier.padding(padding).padding(16.dp)
                )
            }
        }
    }
}
