package com.rohmat.tmdb_android.presentation.ui.screen.detail

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.rohmat.tmdb_android.util.Constants
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    movieId: Int,
    onBack: () -> Unit,
    viewModel: DetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showShareSheet by remember { mutableStateOf(false) }

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
                        IconButton(onClick = { showShareSheet = true }) {
                            Icon(Icons.Filled.Share, contentDescription = "Share")
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

                if (showShareSheet) {
                    val shareText = "share ${movie.title}"
                    ModalBottomSheet(
                        onDismissRequest = { showShareSheet = false },
                        sheetState = sheetState
                    ) {
                        Text(
                            text = "Share",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                        ListItem(
                            headlineContent = { Text("Share via other apps") },
                            leadingContent = {
                                Icon(Icons.Filled.Share, contentDescription = null)
                            },
                            modifier = Modifier.clickable {
                                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, shareText)
                                }
                                ContextCompat.startActivity(
                                    context,
                                    Intent.createChooser(sendIntent, "Share via"),
                                    null
                                )
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) showShareSheet = false
                                }
                            }
                        )
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
