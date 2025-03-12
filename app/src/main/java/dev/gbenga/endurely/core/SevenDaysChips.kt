package dev.gbenga.endurely.core

import android.util.Log
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayoutScope
import dev.gbenga.endurely.routines.DayOfWeek
import dev.gbenga.endurely.ui.buttons.AppChip
import dev.gbenga.endurely.ui.theme.largePadding
import kotlinx.coroutines.launch

@Composable
fun SevenDaysChips(modifier: Modifier =Modifier, lazyList: LazyListState,
                   days: List<DayOfWeek>,
                   onSelectDay: (String) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    var selectedIndex by remember { mutableStateOf(-1) }
    LaunchedEffect(selectedIndex) {
        if (selectedIndex < 0)return@LaunchedEffect
        Log.d("selectedIndex", "in: $selectedIndex")
        coroutineScope.launch {
            val itemInfo = lazyList.layoutInfo.visibleItemsInfo.firstOrNull { it.index == selectedIndex }
            if (itemInfo != null) {
                val center = lazyList.layoutInfo.viewportEndOffset / 2
                val childCenter = itemInfo.offset + itemInfo.size / 2
                lazyList.animateScrollBy((childCenter - center).toFloat())
            } else {
                lazyList.animateScrollToItem(selectedIndex)
            }
        }

    }

    LazyRow(state = lazyList, modifier = modifier.padding(largePadding)) {

        days.mapIndexed { index, day ->
            item {
                if (selectedIndex != day.selectedIndex){
                    selectedIndex = day.selectedIndex
                }
                AppChip(
                    modifier = Modifier.animateItem(),
                    selected = day.selected,
                    enabled = day.enabled,
                    title = day.name
                ) {
                    onSelectDay(day.name)

                }
            }
        }
    }
}