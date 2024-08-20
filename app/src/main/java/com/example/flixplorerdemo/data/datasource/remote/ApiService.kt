package com.example.flixplorerdemo.data.datasource.remote

import com.example.flixplorerdemo.data.model.PhotoDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("photos")
    suspend fun getPhotos(
        @Query("_page") page: Int,
        @Query("_limit") limit: Int = 5
    ): List<PhotoDTO>
}
