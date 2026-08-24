package com.rohmat.tmdb_android.domain.usecase

import com.rohmat.tmdb_android.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class IsFavoriteUseCase(private val repository: MovieRepository) {
    operator fun invoke(movieId: Int): Flow<Boolean> = repository.isFavorite(movieId)
}
