package com.rohmat.tmdb_android.presentation.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rohmat.tmdb_android.domain.model.Movie
import com.rohmat.tmdb_android.domain.usecase.GetNowPlayingMoviesUseCase
import com.rohmat.tmdb_android.domain.usecase.GetPopularMoviesUseCase
import com.rohmat.tmdb_android.domain.usecase.GetTopRatedMoviesUseCase
import kotlinx.coroutines.flow.Flow

class HomeViewModel(
    getPopularMoviesUseCase: GetPopularMoviesUseCase,
    getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) : ViewModel() {

    val popularMovies: Flow<PagingData<Movie>> =
        getPopularMoviesUseCase().cachedIn(viewModelScope)

    val topRatedMovies: Flow<PagingData<Movie>> =
        getTopRatedMoviesUseCase().cachedIn(viewModelScope)

    val nowPlayingMovies: Flow<PagingData<Movie>> =
        getNowPlayingMoviesUseCase().cachedIn(viewModelScope)
}
