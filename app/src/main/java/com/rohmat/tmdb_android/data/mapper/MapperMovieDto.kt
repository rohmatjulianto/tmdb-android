package com.rohmat.tmdb_android.data.mapper

import com.rohmat.tmdb_android.data.local.database.entity.FavoriteMovieEntity
import com.rohmat.tmdb_android.data.remote.dto.MovieDto
import com.rohmat.tmdb_android.data.remote.dto.ReviewDto
import com.rohmat.tmdb_android.domain.model.Movie
import com.rohmat.tmdb_android.domain.model.Review
import com.rohmat.tmdb_android.util.Constants


fun MovieDto.toDomain(isFavorite: Boolean = false): Movie = Movie(
    id = id,
    title = title,
    overview = overview.orEmpty(),
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate.orEmpty(),
    voteAverage = voteAverage ?: 0.0,
    isFavorite = isFavorite
)


fun FavoriteMovieEntity.toDomain(): Movie = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    isFavorite = true
)

fun Movie.toEntity(): FavoriteMovieEntity = FavoriteMovieEntity(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage
)

fun ReviewDto.toDomain(): Review = Review(
    id = id,
    author = author,
    content = content,
    avatarUrl = authorDetails.avatarPath?.let { path ->
        if (path.startsWith("http")) path else Constants.IMAGE_BASE_URL + path
    }
)