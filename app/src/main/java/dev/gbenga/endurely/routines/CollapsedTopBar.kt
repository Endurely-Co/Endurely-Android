package dev.gbenga.endurely.routines

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import dev.gbenga.endurely.R

val COLLAPSED_TOP_BAR_HEIGHT = 60.dp
val EXPANDED_TOP_BAR_HEIGHT = 200.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollapsedTopBar(
    modifier: Modifier = Modifier,
    isCollapsed: Boolean
) {
    val color = if (isCollapsed) MaterialTheme.colorScheme.background else Color.Transparent
    Box(
        modifier = modifier
            .background(color)
            .fillMaxWidth()
            .height(COLLAPSED_TOP_BAR_HEIGHT)
            .padding(16.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        AnimatedVisibility(visible = isCollapsed) {
            Text(text = "Library", style = MaterialTheme.typography.headlineSmall)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxLibrary(onBackRequest: () -> Unit, content: @Composable () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("Routine Goals")
            }, navigationIcon = {
                BackNavigationButton(onBackRequest)
            })
        }
    ) {
        val listState = rememberLazyListState()

        val overlapHeightPx = with(LocalDensity.current) {
            EXPANDED_TOP_BAR_HEIGHT.toPx() - COLLAPSED_TOP_BAR_HEIGHT.toPx()
        }
        val isCollapsed: Boolean by remember {
            derivedStateOf {
                val isFirstItemHidden =
                    listState.firstVisibleItemScrollOffset > overlapHeightPx
                isFirstItemHidden || listState.firstVisibleItemIndex > 0
            }
        }
        Box(modifier = Modifier.padding(it)) {
            content()
        }
    }
}


@Composable
fun BackNavigationButton(onBackRequest: (() -> Unit)? = null){
    IconButton(onClick = {
        onBackRequest?.invoke()
    }) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null
        )
    }
}
