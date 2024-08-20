package com.example.flixplorerdemo.data.repository

import androidx.paging.PagingData
import com.example.flixplorerdemo.data.datasource.local.UserPreferences
import com.example.flixplorerdemo.data.datasource.remote.RemoteDataSource
import com.example.flixplorerdemo.data.model.PhotoDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FlixplorerRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val userPreferences: UserPreferences
) {
    fun readUseMaterial3(): Flow<Boolean> {
        return userPreferences.useMaterial3
    }

    fun readUseDarkMode(): Flow<String> {
        return userPreferences.useDarkMode
    }

    suspend fun updateUseMaterial3(useMaterial3: Boolean) {
        userPreferences.updateUseMaterial3(useMaterial3)
    }

    suspend fun updateUseDarkMode(useDarkMode: String) {
        userPreferences.updateUseDarkMode(useDarkMode)
    }

    fun getPopularMovies(): Flow<PagingData<PhotoDTO>> = remoteDataSource
        .getPopularMovies()
        .map {
            // it.map { movieDto ->
            //     movieDto.mapToUI()
            // }
            it
        }
}
