package com.rohmat.tmdb_android.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rohmat.tmdb_android.data.local.database.entity.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: FavoriteMovieEntity)

    @Delete
    suspend fun delete(movie: FavoriteMovieEntity)

    @Query("SELECT * FROM table_movie_favorite WHERE id = :movieId")
    suspend fun getById(movieId: Int): FavoriteMovieEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM table_movie_favorite WHERE id = :movieId)")
    fun isFavorite(movieId: Int): Flow<Boolean>

    @Query("SELECT * FROM table_movie_favorite ORDER BY title ASC")
    fun getAllFavorites(): Flow<List<FavoriteMovieEntity>>
}