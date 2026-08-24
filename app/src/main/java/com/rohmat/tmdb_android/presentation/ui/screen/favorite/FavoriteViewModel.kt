package com.rohmat.tmdb_android.presentation.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rohmat.tmdb_android.domain.model.Movie
import com.rohmat.tmdb_android.domain.usecase.GetFavoriteMoviesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FavoriteViewModel(
    getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
) : ViewModel() {

    val favorites: StateFlow<List<Movie>> = getFavoriteMoviesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
