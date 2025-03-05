package dev.gbenga.endurely.routines

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.ui.buttons.FitnessLoadingIndicator
import dev.gbenga.endurely.ui.theme.largePadding
import org.koin.androidx.compose.koinViewModel

@Composable
fun RoutinesScreen(
    viewModel: RoutinesViewModel = koinViewModel(),
    onItemClick: (String, String) -> Unit,
    showMessage: (String) -> Unit,) {

    val routineUi by viewModel.routinesUi.collectAsStateWithLifecycle()

    RoutinesContent(showMessage = showMessage, onRefresh ={
        viewModel.getRoutinesByUserId()
    }, routineUi = routineUi, onItemClick =onItemClick){
        viewModel.clearState()
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoutinesContent(routineUi: RoutineUiState,
                    showMessage: (String) -> Unit,
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
        val (routineBlock, noRoutineBlock, loadingBlock, titleBlock) = createRefs()
//        Text(Tokens.gymRoutine, style = MaterialTheme.typography.headlineMedium)
        when(val routineState = routineUi.routines){
            is UiState.Success ->{
                routineState.data.let { routines ->
                    if (routines.isEmpty()){
                        Text("No available routine",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.constrainAs(noRoutineBlock){
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            })
                    }else{


                        val rou = rememberCoroutineScope()

                        LazyColumn(

                            modifier = Modifier.constrainAs(routineBlock){
                            top.linkTo(parent.top)
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
                                        onItemClick(routines[index].routineId, routines[index].routineName)
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
                    top.linkTo(parent.top)
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