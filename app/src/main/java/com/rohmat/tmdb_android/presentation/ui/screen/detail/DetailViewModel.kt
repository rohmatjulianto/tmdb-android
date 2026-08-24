package com.rohmat.tmdb_android.presentation.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rohmat.tmdb_android.domain.model.Movie
import com.rohmat.tmdb_android.domain.model.Review
import com.rohmat.tmdb_android.domain.usecase.GetMovieDetailUseCase
import com.rohmat.tmdb_android.domain.usecase.GetMovieReviewsUseCase
import com.rohmat.tmdb_android.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DetailUiState(
    val isLoading: Boolean = true,
    val movie: Movie? = null,
    val reviews: List<Review> = emptyList(),
    val errorMessage: String? = null
)

class DetailViewModel(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            getMovieDetailUseCase(movieId)
                .onSuccess { movie ->
                    _uiState.value = _uiState.value.copy(isLoading = false, movie = movie)
                }
                .onFailure { throwable ->
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = throwable.message)
                }

            getMovieReviewsUseCase(movieId)
                .onSuccess { reviews ->
                    _uiState.value = _uiState.value.copy(reviews = reviews)
                }
        }
    }

    fun onFavoriteClick() {
        val movie = _uiState.value.movie ?: return
        viewModelScope.launch {
            toggleFavoriteUseCase(movie)
            _uiState.value = _uiState.value.copy(movie = movie.copy(isFavorite = !movie.isFavorite))
        }
    }
}
