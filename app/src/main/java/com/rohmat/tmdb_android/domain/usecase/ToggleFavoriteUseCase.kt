package com.rohmat.tmdb_android.domain.usecase

import com.rohmat.tmdb_android.domain.model.Movie
import com.rohmat.tmdb_android.domain.repository.MovieRepository

class ToggleFavoriteUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(movie: Movie) = repository.toggleFavorite(movie)
}
