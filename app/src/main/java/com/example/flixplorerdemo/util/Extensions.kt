package com.example.flixplorerdemo.util

import android.content.Context
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.flixplorerdemo.R
import com.example.flixplorerdemo.data.common.RemoteSourceException
import okhttp3.ResponseBody

fun RemoteSourceException.getError(context: Context): String {
    return when (messageResource) {
        is Int -> context.getString(messageResource)
        is ResponseBody? -> messageResource!!.string()
        is String -> messageResource
        else -> context.getString(R.string.error_unexpected_message)
    }
}

fun Iterable<LazyPagingItems<*>>.isAnyRefreshing(): Boolean =
    any { it.loadState.refresh is LoadState.Loading }

fun Iterable<LazyPagingItems<*>>.hasItems(): Boolean = any { it.itemCount > 0 }

fun Iterable<LazyPagingItems<*>>.isAnyError(): Pair<Boolean, LoadState.Error?> {
    return if (any { it.loadState.refresh is LoadState.Error }) {
        val errorList: LazyPagingItems<*>? = this.find { it.loadState.refresh is LoadState.Error }
        true to errorList?.loadState?.refresh as LoadState.Error
    } else {
        false to null
    }
}