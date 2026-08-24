package com.rohmat.tmdb_android.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rohmat.tmdb_android.data.local.database.dao.FavoriteMovieDao
import com.rohmat.tmdb_android.data.local.database.entity.FavoriteMovieEntity

@Database(
    entities = [FavoriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}