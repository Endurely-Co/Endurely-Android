package dev.gbenga.endurely.routines

import android.util.Log
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gbenga.endurely.core.SevenDaysChips
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.navigation.EndureNavigation
import dev.gbenga.endurely.ui.buttons.AppChip
import dev.gbenga.endurely.ui.buttons.FitnessLoadingIndicator
import dev.gbenga.endurely.ui.theme.largePadding
import dev.gbenga.endurely.ui.theme.xXLargePadding
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun RoutinesScreen(
    viewModel: RoutinesViewModel = koinViewModel(),
    onItemClick: (String, String) -> Unit,
    showMessage: (String) -> Unit,) {

    val routineUi by viewModel.routinesUi.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getRoutinesByUserId()
    }

    RoutinesContent(showMessage = showMessage, onRefresh ={
        viewModel.getRoutinesByUserId()
    }, routineUi = routineUi, onSelectDay = {
        viewModel.selectDay(it)
    }, onItemClick =onItemClick){

    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoutinesContent(routineUi: RoutineUiState,
                    showMessage: (String) -> Unit,
                    onSelectDay: (String) -> Unit,
                    onRefresh: () -> Unit,  onItemClick: (String, String) -> Unit,
                    onResetUiState: () -> Unit){
    var errMessage by remember { mutableStateOf("") }
    val pullToRefreshState = rememberPullRefreshState(refreshing = false, onRefresh = {
        onRefresh()
    })


    LaunchedEffect(errMessage) {
        if (errMessage.isNotBlank()){
            showMessage(errMessage)
        }
    }

    ConstraintLayout(modifier = Modifier.pullRefresh(pullToRefreshState).fillMaxSize()) {
        val (routineBlock, noRoutineBlock, loadingBlock, days) = createRefs()
        val coroutineScope = rememberCoroutineScope()
        val daysState = rememberLazyListState()



        SevenDaysChips(modifier =Modifier.constrainAs(days){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, lazyList = daysState, days= routineUi.days,
            onSelectDay=onSelectDay)


        when(val routineState = routineUi.routines){
            is UiState.Success ->{
                routineState.data.let { routines ->
                    if (routines.isEmpty()){
                        Text("No available routine",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.constrainAs(noRoutineBlock){
                                top.linkTo(days.bottom,)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            })
                    }else{
                        LazyColumn(

                            modifier = Modifier.constrainAs(routineBlock){
                            top.linkTo(days.bottom, xXLargePadding)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }.fillMaxSize().padding(vertical = largePadding),) {
                            items(routines.size, key = {
                                it
                            }){ index ->
                                RoutineUiItem(modifier = Modifier.animateItem(), routines[index], index,
                                    routineUi.isDarkMode ?: isSystemInDarkTheme(),
                                    onItemClick = {

                                        onItemClick(routines[index].routineId,
                                            routines[index].routineName)
                                    })

                            }
                        }
                    }


                }

            }
            is UiState.Failure ->{
                errMessage = routineState.message
                onResetUiState()
            }
            is UiState.Loading ->{
                FitnessLoadingIndicator(show = true,
                    modifier = Modifier.constrainAs(loadingBlock){
                    top.linkTo(days.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
            }
            else ->{
                errMessage = ""
            }
        }
    }
}