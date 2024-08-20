package com.example.flixplorerdemo.ui.screens.movie

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.example.flixplorerdemo.data.model.PhotoDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class MovieScreenState(
    val popular: Flow<PagingData<PhotoDTO>>,
) {
    companion object {
        val default: MovieScreenState =
            MovieScreenState(
                popular = emptyFlow(),
            )
    }
}
