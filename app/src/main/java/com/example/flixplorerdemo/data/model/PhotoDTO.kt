package com.example.flixplorerdemo.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PhotoDTO(
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)
