package com.rohmat.tmdb_android.domain.repository

import androidx.paging.PagingData
import com.rohmat.tmdb_android.data.remote.dto.MovieDto
import com.rohmat.tmdb_android.data.remote.dto.ReviewDto
import com.rohmat.tmdb_android.domain.model.Movie
import com.rohmat.tmdb_android.domain.model.Review
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<PagingData<Movie>>

    fun getTopRatedMovies(): Flow<PagingData<Movie>>

    fun getNowPlayingMovies(): Flow<PagingData<Movie>>

    suspend fun getMovieDetail(movieId: Int): Result<Movie>

    suspend fun getMovieReviews(movieId: Int): Result<List<Review>>

    suspend fun toggleFavorite(movie: Movie)

    fun isFavorite(movieId: Int): Flow<Boolean>

    fun getFavoriteMovies(): Flow<List<Movie>>
}