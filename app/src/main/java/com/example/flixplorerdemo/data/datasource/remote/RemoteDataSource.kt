package com.example.flixplorerdemo.data.datasource.remote

import androidx.paging.PagingData
import com.example.flixplorerdemo.data.model.PhotoDTO
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getPopularMovies(): Flow<PagingData<PhotoDTO>>
}
