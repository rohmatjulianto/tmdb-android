package com.rohmat.tmdb_android.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.rohmat.tmdb_android.data.local.database.AppDatabase
import com.rohmat.tmdb_android.data.local.database.dao.FavoriteMovieDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private const val DB_NAME = "tmdb-android-db"
val databaseModule = module {
    factory<AppDatabase> {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    factory<FavoriteMovieDao> { get<AppDatabase>().favoriteMovieDao() }
}