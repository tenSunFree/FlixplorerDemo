package com.example.flixplorerdemo.data.datasource.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.flixplorerdemo.data.model.PhotoDTO
import com.example.flixplorerdemo.data.paging.PopularMoviesSource
import com.example.flixplorerdemo.util.Constants
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl(private val apis: ApiService) : RemoteDataSource {

    override fun getPopularMovies(): Flow<PagingData<PhotoDTO>> =
        Pager(
            config = PagingConfig(pageSize = Constants.ITEM_LOAD_PER_PAGE),
            pagingSourceFactory = {
                PopularMoviesSource(api = apis)
            }
        ).flow
}
