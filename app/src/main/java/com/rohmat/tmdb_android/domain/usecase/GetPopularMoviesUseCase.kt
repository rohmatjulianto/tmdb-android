package com.rohmat.tmdb_android.domain.usecase

import androidx.paging.PagingData
import com.rohmat.tmdb_android.domain.model.Movie
import com.rohmat.tmdb_android.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase(private val repository: MovieRepository) {
    operator fun invoke(): Flow<PagingData<Movie>> = repository.getPopularMovies()
}
