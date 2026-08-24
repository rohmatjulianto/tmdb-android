package com.rohmat.tmdb_android.presentation.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Favorite : Screen("favorite")
    data object Detail : Screen("detail/{movieId}") {
        fun createRoute(movieId: Int) = "detail/$movieId"
    }
}
