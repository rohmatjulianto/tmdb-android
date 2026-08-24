package com.rohmat.tmdb_android.domain.usecase

import com.rohmat.tmdb_android.domain.model.Review
import com.rohmat.tmdb_android.domain.repository.MovieRepository

class GetMovieReviewsUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(movieId: Int): Result<List<Review>> = repository.getMovieReviews(movieId)
}
