package com.rohmat.tmdb_android.domain.usecase

import com.rohmat.tmdb_android.domain.model.Movie
import com.rohmat.tmdb_android.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteMoviesUseCase(private val repository: MovieRepository) {
    operator fun invoke(): Flow<List<Movie>> = repository.getFavoriteMovies()
}
