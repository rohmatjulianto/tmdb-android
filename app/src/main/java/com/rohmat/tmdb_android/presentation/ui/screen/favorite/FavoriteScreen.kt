package com.rohmat.tmdb_android.presentation.ui.screen.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rohmat.tmdb_android.util.Constants
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    onMovieClick: (Int) -> Unit,
    onBack: () -> Unit,
    viewModel: FavoriteViewModel = koinViewModel()
) {
    val favorites by viewModel.favorites.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorite Movies") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (favorites.isEmpty()) {
            Text(
                text = "No favorite movies yet.",
                modifier = Modifier.padding(padding).padding(16.dp)
            )
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(favorites.size, key = { index -> favorites[index].id }) { index ->
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { onMovieClick(favorites[index].id) }
                    ) {
                        AsyncImage(
                            model = favorites[index].posterPath?.let { Constants.IMAGE_BASE_URL + it },
                            contentDescription = favorites[index].title,
                            modifier = Modifier.padding(end = 12.dp)
                        )
                        Column {
                            Text(text = favorites[index].title)
                            Text(text = favorites[index].releaseDate)
                            Text(text = favorites[index].overview, maxLines = 3)
                        }
                    }
                }
            }
        }
    }
}
