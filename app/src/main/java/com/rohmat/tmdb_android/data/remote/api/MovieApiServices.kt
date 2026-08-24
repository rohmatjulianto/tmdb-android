package com.rohmat.tmdb_android.data.remote.api

import com.rohmat.tmdb_android.data.remote.dto.MovieDto
import com.rohmat.tmdb_android.data.remote.dto.MovieResponseDto
import com.rohmat.tmdb_android.data.remote.dto.ReviewResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiServices {

    @GET("movie/popular")
    suspend fun getMoviePopular(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): MovieResponseDto

    @GET("movie/top_rated")
    suspend fun getMovieTopRated(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ):MovieResponseDto

    @GET("movie/now_playing")
    suspend fun getMovieNowPlaying(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): MovieResponseDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int
    ): MovieDto
    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReview(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): ReviewResponse
}