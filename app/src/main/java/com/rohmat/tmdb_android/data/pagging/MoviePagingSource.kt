package com.rohmat.tmdb_android.data.pagging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rohmat.tmdb_android.data.local.database.dao.FavoriteMovieDao
import com.rohmat.tmdb_android.data.mapper.toDomain
import com.rohmat.tmdb_android.data.remote.dto.MovieResponseDto
import com.rohmat.tmdb_android.domain.model.Movie
import java.io.IOException
import retrofit2.HttpException

class MoviePagingSource(
    private val favoriteDao: FavoriteMovieDao,
    private val fetch: suspend (page: Int) -> MovieResponseDto
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val response = fetch(page)
            val movies = response.results.map { dto ->
                val isFav = favoriteDao.getById(dto.id) != null
                dto.toDomain(isFavorite = isFav)
            }
            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= response.totalPages) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
