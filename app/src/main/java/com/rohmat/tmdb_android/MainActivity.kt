package com.rohmat.tmdb_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rohmat.tmdb_android.presentation.navigation.Screen
import com.rohmat.tmdb_android.presentation.ui.screen.detail.DetailScreen
import com.rohmat.tmdb_android.presentation.ui.screen.favorite.FavoriteScreen
import com.rohmat.tmdb_android.presentation.ui.screen.home.HomeScreen
import com.rohmat.tmdb_android.presentation.ui.theme.TmdbandroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            TmdbandroidTheme {
                MovieAppNavHost()
            }
        }
    }

}

@Composable
fun MovieAppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Detail.createRoute(movieId))
                },
                onFavoriteIconClick = {
                    navController.navigate(Screen.Favorite.route)
                }
            )
        }
        composable(Screen.Favorite.route) {
            FavoriteScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Detail.createRoute(movieId))
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Detail.route) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull() ?: -1
            DetailScreen(
                movieId = movieId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
