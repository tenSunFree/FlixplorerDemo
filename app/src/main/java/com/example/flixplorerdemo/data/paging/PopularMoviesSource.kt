package com.example.flixplorerdemo.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.flixplorerdemo.data.common.RequestErrorHandler
import com.example.flixplorerdemo.data.datasource.remote.ApiService
import com.example.flixplorerdemo.data.model.PhotoDTO
import javax.inject.Inject

class PopularMoviesSource @Inject constructor(
    private val api: ApiService
) : PagingSource<Int, PhotoDTO>() {

    override fun getRefreshKey(state: PagingState<Int, PhotoDTO>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoDTO> {
        return try {
            val nextPage = params.key ?: 1
            val popularMovies = api.getPhotos(nextPage)
            LoadResult.Page(
                data = popularMovies,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (popularMovies.isEmpty()) null else nextPage + 1
            )
        } catch (e: java.lang.Exception) {
            LoadResult.Error(throwable = RequestErrorHandler.getRequestError(e))
        }
    }
}
