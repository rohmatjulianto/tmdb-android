package com.rohmat.tmdb_android.di


import com.rohmat.tmdb_android.domain.usecase.GetFavoriteMoviesUseCase
import com.rohmat.tmdb_android.domain.usecase.GetMovieDetailUseCase
import com.rohmat.tmdb_android.domain.usecase.GetMovieReviewsUseCase
import com.rohmat.tmdb_android.domain.usecase.GetNowPlayingMoviesUseCase
import com.rohmat.tmdb_android.domain.usecase.GetPopularMoviesUseCase
import com.rohmat.tmdb_android.domain.usecase.GetTopRatedMoviesUseCase
import com.rohmat.tmdb_android.domain.usecase.IsFavoriteUseCase
import com.rohmat.tmdb_android.domain.usecase.ToggleFavoriteUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetPopularMoviesUseCase(get()) }
    factory { GetTopRatedMoviesUseCase(get()) }
    factory { GetNowPlayingMoviesUseCase(get()) }
    factory { GetMovieDetailUseCase(get()) }
    factory { GetMovieReviewsUseCase(get()) }
    factory { ToggleFavoriteUseCase(get()) }
    factory { IsFavoriteUseCase(get()) }
    factory { GetFavoriteMoviesUseCase(get()) }
}
