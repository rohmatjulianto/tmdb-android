package com.rohmat.tmdb_android.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rohmat.tmdb_android.data.local.database.dao.FavoriteMovieDao
import com.rohmat.tmdb_android.data.mapper.toDomain
import com.rohmat.tmdb_android.data.mapper.toEntity
import com.rohmat.tmdb_android.data.pagging.MoviePagingSource
import com.rohmat.tmdb_android.data.remote.api.MovieApiServices
import com.rohmat.tmdb_android.data.remote.dto.MovieResponseDto
import com.rohmat.tmdb_android.domain.model.Movie
import com.rohmat.tmdb_android.domain.model.Review
import com.rohmat.tmdb_android.domain.repository.MovieRepository
import com.rohmat.tmdb_android.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    val api: MovieApiServices,
    val dao: FavoriteMovieDao
) : MovieRepository {
    override fun getPopularMovies(): Flow<PagingData<Movie>> = buildPager { page ->
        api.getMoviePopular(page = page)
    }

    override fun getTopRatedMovies(): Flow<PagingData<Movie>> = buildPager { page ->
        api.getMovieTopRated(page = page)
    }

    override fun getNowPlayingMovies(): Flow<PagingData<Movie>> = buildPager { page ->
        api.getMovieNowPlaying(page = page)
    }

    private fun buildPager(
        fetch: suspend (page: Int) -> MovieResponseDto
    ): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = Constants.PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { MoviePagingSource(dao, fetch) }
    ).flow

    override suspend fun getMovieDetail(movieId: Int): Result<Movie> = runCatching {
        val dto = api.getMovieDetail(movieId)
        val isFav = dao.getById(movieId) != null
        dto.toDomain(isFavorite = isFav)
    }

    override suspend fun getMovieReviews(movieId: Int): Result<List<Review>> = runCatching {
        api.getMovieReview(movieId = movieId).results.map { it.toDomain() }
    }


    override suspend fun toggleFavorite(movie: Movie) {
        val existing = dao.getById(movie.id)
        if (existing != null) {
            dao.delete(existing)
        } else {
            dao.insert(movie.toEntity())
        }
    }

    override fun isFavorite(movieId: Int): Flow<Boolean> = dao.isFavorite(movieId)

    override fun getFavoriteMovies(): Flow<List<Movie>> =
        dao.getAllFavorites().map { list -> list.map { it.toDomain() } }
}