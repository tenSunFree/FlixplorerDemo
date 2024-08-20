package com.example.flixplorerdemo.ui.screens.home

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.flixplorerdemo.ui.navigation.BottomBarScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.thesomeshkumar.flixplorer.ui.navigation.MainScreenNavGraph

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    val screens = listOf(
        BottomBarScreen.Movies,
        BottomBarScreen.TvShows
    )
    Log.d("HomeScreen", "HomeScreen, screens: $screens")

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = true

    // 设置状态栏颜色为白色，文字颜色为黑色
    systemUiController.setStatusBarColor(
        color = Color.White,
        darkIcons = useDarkIcons
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    Log.d("HomeScreen", "HomeScreen, navBackStackEntry: $navBackStackEntry")
    val currentDestination = navBackStackEntry?.destination
    Log.d("HomeScreen", "HomeScreen, currentDestination: $currentDestination")
    val showNavigationState = rememberSaveable { (mutableStateOf(false)) }
    Log.d("HomeScreen", "HomeScreen, showNavigationState: $showNavigationState")
    showNavigationState.value = screens.any {
        Log.d("HomeScreen", "HomeScreen, it.route: ${it.route}")
        Log.d("HomeScreen", "HomeScreen, currentDestination?.route: ${currentDestination?.route}")
        it.route == currentDestination?.route
    }
    Log.d("HomeScreen", "HomeScreen, showNavigationState: ${showNavigationState.value}")

    Scaffold(
        // topBar = {
        //     AnimatedVisibility(
        //         visible = showNavigationState.value,
        //         enter = slideInVertically(
        //             animationSpec = tween(Constants.ANIM_TIME_SHORT)
        //         ) {
        //             -it
        //         },
        //         exit = slideOutVertically(
        //             animationSpec = tween(Constants.ANIM_TIME_SHORT)
        //         ) {
        //             -it
        //         }
        //     ) {
        //         FlixTopAppBar(stringResource(id = R.string.app_name), onSettingsClick = {
        //             navController.navigate(SettingsScreen)
        //         })
        //     }
        // },
        // bottomBar = {
        // AnimatedVisibility(
        //     visible = showNavigationState.value,
        //     enter = slideInVertically(
        //         animationSpec = tween(Constants.ANIM_TIME_SHORT)
        //     ) {
        //         it
        //     },
        //     exit = slideOutVertically(
        //         animationSpec = tween(Constants.ANIM_TIME_SHORT)
        //     ) {
        //         it
        //     }
        // ) {
        //     FlixplorerBottomBar(navController = navController)
        // }
        // }
    ) { padding: PaddingValues ->
        MainScreenNavGraph(
            navController = navController,
            modifier = Modifier.padding(padding)
        )
    }
}
