package com.example.flixplorerdemo.ui.component

import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.example.flixplorerdemo.R
import com.example.flixplorerdemo.data.model.PhotoDTO
import com.example.flixplorerdemo.util.Constants

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MediaRow(
    title: String,
    list: LazyPagingItems<PhotoDTO>,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    listItemModifier: Modifier = Modifier,
    onItemClicked: (PhotoDTO) -> Unit,
) {
    Column {
        // if (list.itemCount > 0) {
        //     Text(
        //         text = title,
        //         style = MaterialTheme.typography.headlineSmall,
        //         modifier = Modifier.padding(dimensionResource(id = R.dimen.normal_padding_half))
        //     )
        // }

        LazyRow(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            state = rememberLazyListState()
        ) {
            Log.d("MediaRow", "MediaRow, list.itemCount: ${list.itemCount}")
            items(list.itemCount, key = { index -> list[index]?.id ?: index }) { index ->
                Log.d("MediaRow", "MediaRow, index: $index")
                list[index]?.let {
                    Log.d("MediaRow", "MediaRow, dto: $it")
                    MediaCard(
                        it,
                        animatedVisibilityScope,
                        listItemModifier,
                        onItemClicked
                    )
                }
            }
            if (list.loadState.append == LoadState.Loading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MediaCard(
    homeMediaUI: PhotoDTO,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    onItemClicked: (PhotoDTO) -> Unit,
) {
    ElevatedCard(
        onClick = { onItemClicked(homeMediaUI) },
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.normal_padding_half))
            .width(dimensionResource(id = R.dimen.home_grid_card_width))
            .height(dimensionResource(id = R.dimen.home_grid_card_height))
            .background(Color(0x30FFFFFF))
            .sharedElement(
                state = rememberSharedContentState(key = "poster-${homeMediaUI.id}"),
                animatedVisibilityScope = animatedVisibilityScope,
                boundsTransform = { _, _ ->
                    tween(durationMillis = Constants.ANIM_TIME_SHORT)
                }
            )
    ) {
        Box {
            AsyncImage(
                model = homeMediaUI.url,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.ic_load_placeholder),
                error = painterResource(id = R.drawable.ic_load_error),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            ElevatedCard(
                onClick = { onItemClicked(homeMediaUI) },
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 8.dp, vertical = 0.dp) // 设置外边距
                    .clip(RoundedCornerShape(8.dp)) // 设置圆角
                    .background(Color(0xFF000000))
            ) {
                Box(
                    modifier = Modifier
                        // .align(Alignment.Center)
                        // .padding(horizontal = 8.dp) // 设置外边距
                        .clip(RoundedCornerShape(8.dp)) // 设置圆角
                        .background(Color(0xFF000000))
                ) {
                    Text(
                        text = homeMediaUI.title,
                        modifier = Modifier
                            .padding(horizontal = 4.dp) // 设置水平内边距
                            .fillMaxWidth() // 填满宽度
                            .align(Alignment.Center),
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                        // maxLines = 2
                    )
                }
            }
        }
    }
}
