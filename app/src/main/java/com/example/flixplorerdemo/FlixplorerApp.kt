package com.example.flixplorerdemo

import android.app.Application
import android.util.Log
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.VideoFrameDecoder
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FlixplorerApp : Application(), ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader {
        Log.d("FlixplorerApp", "FlixplorerApp, newImageLoader")
        return ImageLoader
            .Builder(this)
            .components {
                add(VideoFrameDecoder.Factory())
            }
            .logger(DebugLogger())
            .build()
    }
}
