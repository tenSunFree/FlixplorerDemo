package com.example.flixplorerdemo.ui.screens.movie

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.flixplorerdemo.R
import com.example.flixplorerdemo.data.common.RemoteSourceException
import com.example.flixplorerdemo.data.model.PhotoDTO
import com.example.flixplorerdemo.ui.component.ErrorView
import com.example.flixplorerdemo.ui.component.LoadingView
import com.example.flixplorerdemo.ui.component.MediaRow
import com.example.flixplorerdemo.util.getError
import com.example.flixplorerdemo.util.hasItems
import com.example.flixplorerdemo.util.isAnyError
import com.example.flixplorerdemo.util.isAnyRefreshing

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MoviesScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    viewModel: MoviesViewModel = hiltViewModel(),
    onItemClick: (PhotoDTO) -> Unit
) {
    Log.d("MainScreenNavGraph", "SharedTransitionScope.MoviesScreen")
    val movieState by viewModel.moviesState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    MoviesScreenContent(
        movieState,
        scrollState,
        animatedVisibilityScope,
        modifier,
        onItemClick
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun SharedTransitionScope.MoviesScreenContent(
    movieState: MovieScreenState,
    scrollState: ScrollState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    onItemClick: (PhotoDTO) -> Unit
) {
    Log.d("MainScreenNavGraph", "SharedTransitionScope.MoviesScreenContent")
    val popularMoviesLazyItems = movieState.popular.collectAsLazyPagingItems()


    val movieItems = listOf(
        popularMoviesLazyItems
    )
    val isScreenLoading by derivedStateOf {
        movieItems.isAnyRefreshing()
    }
    val hasItems by derivedStateOf {
        movieItems.hasItems()
    }

    val isLoadingError by derivedStateOf {
        movieItems.isAnyError()
    }

    Log.d(
        "MoviesScreen",
        "MoviesScreen, hasItems: $hasItems, isScreenLoading: $isScreenLoading, isLoadingError: ${isLoadingError.first}"
    )
    when {
        hasItems -> {
            Box(modifier = modifier.fillMaxSize()) {
                // 背景图片
                Image(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {

                    Spacer(modifier = Modifier.weight(25f))

                    // MediaCarousel(
                    //     list = upcomingMoviesLazyItems,
                    //     carouselLabel = stringResource(R.string.upcoming_movies),
                    //     animatedVisibilityScope = animatedVisibilityScope,
                    //     onItemClicked = { onItemClick(it) }
                    // )
                    // MediaRow(
                    //     title = stringResource(R.string.popular),
                    //     list = popularMoviesLazyItems,
                    //     animatedVisibilityScope = animatedVisibilityScope,
                    //     onItemClicked = { onItemClick(it) }
                    // )
                    MediaRow(
                        title = stringResource(R.string.top_rated),
                        list = popularMoviesLazyItems,
                        animatedVisibilityScope = animatedVisibilityScope,
                        onItemClicked = { onItemClick(it) }
                    )

                    Spacer(modifier = Modifier.weight(8f))
                }
            }
        }

        isScreenLoading -> {
            LoadingView(modifier = Modifier.fillMaxSize())
        }

        // Todo: Possible UI rewrite to handle each API error in their own UI container
        isLoadingError.first -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val loadStateError = isLoadingError.second!!
                val error =
                    (loadStateError.error as RemoteSourceException).getError(LocalContext.current)
                ErrorView(
                    errorText = error,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
