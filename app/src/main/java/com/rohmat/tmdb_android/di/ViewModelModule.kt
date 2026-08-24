package com.rohmat.tmdb_android.di

import com.rohmat.tmdb_android.presentation.ui.screen.detail.DetailViewModel
import com.rohmat.tmdb_android.presentation.ui.screen.favorite.FavoriteViewModel
import com.rohmat.tmdb_android.presentation.ui.screen.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { DetailViewModel(get(), get(), get()) }
    viewModel { FavoriteViewModel(get()) }
}
