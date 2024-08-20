package com.example.flixplorerdemo.ui.screens.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flixplorerdemo.data.repository.FlixplorerRepository
import com.example.flixplorerdemo.ui.screens.movie.MovieScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val flixRepository: FlixplorerRepository) :
    ViewModel() {

    val moviesState: StateFlow<MovieScreenState> = flow {
        emit(
            MovieScreenState(
                popular = flixRepository
                    .getPopularMovies(),
                    // .cachedIn(viewModelScope),
            )
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        MovieScreenState.default
    )
}
