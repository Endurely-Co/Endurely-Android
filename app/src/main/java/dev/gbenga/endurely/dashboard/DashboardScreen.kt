package dev.gbenga.endurely.dashboard

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gbenga.endurely.R
import dev.gbenga.endurely.core.Tokens
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.main.EndurelyBottomBar
import dev.gbenga.endurely.navigation.EndureNavigation
import dev.gbenga.endurely.routines.RoutinesScreen
import dev.gbenga.endurely.ui.theme.Orange
import dev.gbenga.endurely.ui.theme.Purple
import dev.gbenga.endurely.ui.theme.largePadding
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(nav: EndureNavigation, viewModel: DashboardViewModel = koinViewModel()) {
    val dashboardUi by viewModel.dashboardUi.collectAsStateWithLifecycle()
    val signOut by viewModel.signOut.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(signOut) {
        when(signOut){
            is UiState.Success ->{
                nav.gotoLogin(onTop = true)
            }
            is UiState.Failure ->{
                Toast.makeText(context, (signOut as UiState.Failure<String>).message,
                    Toast.LENGTH_SHORT).show()
            }
            else -> {/*Nothing*/}
        }
    }
    DashboardScreenContent(dashboardUi, signOutRequest={
        nav.gotoLogin(onTop = true)
    }, onItemClick ={ routineId, title ->
        nav.gotoRoutineDetails(routineId, title)
    }){
        nav.gotoWelcome()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreenContent(dashboardUiState: DashboardUiState,
                           signOutRequest: () -> Unit,
                           onItemClick: (String, String) -> Unit,
                           onInValidUser: () -> Unit,){
    val pagerState = rememberPagerState(pageCount = {3})
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
        SnackbarHost(hostState = snackbarHostState){ data ->
            // custom snackbar with the custom colors
            Snackbar(
                contentColor = Color.White,
                containerColor = Color(Purple),
                actionColor = Color.Red,
                //contentColor = ...,
                snackbarData = data
            )
        }
                       },
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
                Box (modifier = Modifier.fillMaxHeight()){
                    Text(
                        dashboardUiState.pageTitles[pagerState.currentPage],
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(horizontal = largePadding)
                            .align(Alignment.BottomStart),
                    )
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
        val coroutineScope = rememberCoroutineScope()
       val viewModel: SettingsViewModel = koinViewModel()

       HorizontalPager(pagerState, modifier = Modifier.padding(it),
           userScrollEnabled = false) { page ->
           when(page){
               0 -> DashboardScreenList(dashboardUiState, onInValidUser)
               1 -> RoutinesScreen(onItemClick = onItemClick){
                   coroutineScope.launch {
                       snackbarHostState.showSnackbar(it)
                   }
               }
               2 -> SettingsScreen(viewModel){
                   coroutineScope.launch {
                       val action =snackbarHostState.showSnackbar("Are sure you want to sign out?", actionLabel = "Sign Out",
                           duration = SnackbarDuration.Short)
                       if (action == SnackbarResult.ActionPerformed){
                           viewModel.signOut()
                           signOutRequest()
                       }
                   }
               }
           }
       }
    }
}
