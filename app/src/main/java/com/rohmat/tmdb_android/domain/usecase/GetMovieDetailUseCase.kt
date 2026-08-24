package com.rohmat.tmdb_android.domain.usecase


import com.rohmat.tmdb_android.domain.model.Movie
import com.rohmat.tmdb_android.domain.repository.MovieRepository

class GetMovieDetailUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(movieId: Int): Result<Movie> = repository.getMovieDetail(movieId)
}
