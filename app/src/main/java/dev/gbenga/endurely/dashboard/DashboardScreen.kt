package dev.gbenga.endurely.dashboard

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gbenga.endurely.R
import dev.gbenga.endurely.main.EndurelyBottomBar
import dev.gbenga.endurely.navigation.EndureNavigation
import dev.gbenga.endurely.ui.theme.Orange
import dev.gbenga.endurely.ui.theme.Purple
import dev.gbenga.endurely.ui.theme.largePadding
import dev.gbenga.endurely.ui.theme.normalPadding
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(nav: EndureNavigation, viewModel: DashboardViewModel = koinViewModel()) {
    val dashboardUi by viewModel.dashboardUi.collectAsStateWithLifecycle()
    DashboardScreenContent(dashboardUi,){
        nav.gotoWelcome()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreenContent(dashboardUiState: DashboardUiState, onInValidUser: () -> Unit,){
    val pagerState = rememberPagerState(pageCount = {3})
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            EndurelyBottomBar(onItemClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(it)
                }
            })
        },
        topBar = {
            TopAppBar(title = {

            }, navigationIcon = {
                var expanded by remember { mutableStateOf(false) }
                MinimalDropdownMenu(expanded){
                    expanded = it
                }
                IconButton(modifier = Modifier.padding(horizontal = largePadding), onClick = {
                    expanded = true
                }) {
                    Icon(painter = painterResource(R.drawable.four_squares_ic),
                        "", modifier = Modifier.size(30.dp))
                }
            }, actions = {
                TextButton(onClick = {},modifier = Modifier
                    .drawBehind {
                        drawCircle(color = Color(Purple), radius = 60f)
                    }
                    .size(80.dp)
                    .padding(horizontal = largePadding) ) {
                    Text("T", textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.White), )
                }
            }, modifier = Modifier.height(80.dp))
        }
    ) {
        Log.d("dashboardUiState", "${dashboardUiState.fullName}")
       HorizontalPager(pagerState, modifier = Modifier.padding(it),
           userScrollEnabled = false) { page ->
           when(page){
               0 -> DashboardScreenList(dashboardUiState, onInValidUser)
               1 -> RoutineScreen()
               2 -> SettingsScreen()
           }
       }
    }
}
