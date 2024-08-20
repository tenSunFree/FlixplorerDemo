package com.thesomeshkumar.flixplorer.ui.navigation

import android.util.Log
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flixplorerdemo.ui.navigation.BottomBarScreen
import com.example.flixplorerdemo.ui.navigation.NavGraph
import com.example.flixplorerdemo.ui.screens.movie.MoviesScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainScreenNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            Log.d("MainScreenNavGraph", "MainScreenNavGraph, Navigated to ${destination.route}")
        }
    }

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            route = NavGraph.BOTTOM_BAR_GRAPH,
            startDestination = BottomBarScreen.Movies.route,
        ) {
            Log.d("MainScreenNavGraph", "MainScreenNavGraph, NavHost, route: ${navController.currentDestination?.route}")
            composable(route = BottomBarScreen.Movies.route) {
                Log.d("MainScreenNavGraph", "MainScreenNavGraph, Movies")
                MoviesScreen(
                    animatedVisibilityScope = this,
                    modifier = modifier
                ) {
                    Log.d("MainScreenNavGraph", "MainScreenNavGraph, Movies2")
                    // navController.navigate(
                    //     FlixDetails(
                    //         type = Constants.MEDIA_TYPE_MOVIE,
                    //         id = it.id.toString(),
                    //         name = it.title,
                    //         backdrop = it.url.removePrefix("/"),
                    //         poster = it.url.removePrefix("/")
                    //     )
                    // )
                }
            }

            // composable(route = BottomBarScreen.TvShows.route) {
            //     Log.d("MainScreenNavGraph", "MainScreenNavGraph, TvShows")
            //     TvShowScreen(
            //         animatedVisibilityScope = this,
            //         modifier = modifier
            //     ) {
            //         Log.d("MainScreenNavGraph", "MainScreenNavGraph, TvShows2")
            //         navController.navigate(
            //             FlixDetails(
            //                 type = Constants.MEDIA_TYPE_TV_SHOW,
            //                 id = it.id.toString(),
            //                 name = it.name,
            //                 backdrop = it.backdropPath.removePrefix("/"),
            //                 poster = it.posterPath.removePrefix("/")
            //             )
            //         )
            //     }
            // }
//
            // composable<FlixDetails> { arguments: NavBackStackEntry ->
            //     Log.d("MainScreenNavGraph", "MainScreenNavGraph, FlixDetails")
            //     val args = arguments.toRoute<FlixDetails>()
            //     val name = args.name
            //     val backdrop = args.backdrop
            //     val poster = args.poster
            //     Log.d("MainScreenNavGraph", "MainScreenNavGraph, FlixDetails, name: $name, backdrop: $backdrop, poster: $poster")
//
            //     DetailsScreen(
            //         name = name,
            //         backdrop = backdrop,
            //         poster = poster,
            //         animatedVisibilityScope = this,
            //         onNavigationUp = {
            //             Log.d("MainScreenNavGraph", "MainScreenNavGraph, FlixDetails2")
            //             navController.popBackStack()
            //         }
            //     )
            // }
//
            // composable<SettingsScreen> {
            //     Log.d("MainScreenNavGraph", "MainScreenNavGraph, SettingsScreen")
            //     SettingsScreen(onNavigationUp = {
            //         Log.d("MainScreenNavGraph", "MainScreenNavGraph, SettingsScreen2")
            //         navController.popBackStack()
            //     })
            // }
        }
    }
}
