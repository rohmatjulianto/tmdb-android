package com.rohmat.tmdb_android.di

import com.rohmat.tmdb_android.data.local.database.dao.FavoriteMovieDao
import com.rohmat.tmdb_android.data.remote.api.MovieApiServices
import com.rohmat.tmdb_android.data.repository.MovieRepositoryImpl
import com.rohmat.tmdb_android.domain.repository.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<MovieRepository> {
        MovieRepositoryImpl(api = get<MovieApiServices>(), dao = get<FavoriteMovieDao>())
    }
}