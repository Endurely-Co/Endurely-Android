package dev.gbenga.endurely.routines

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gbenga.endurely.R
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.navigation.EndureNavigation
import dev.gbenga.endurely.routines.data.UserExercise
import dev.gbenga.endurely.routines.data.duration
import dev.gbenga.endurely.ui.buttons.FitnessLoadingIndicator
import dev.gbenga.endurely.ui.theme.Maroon
import dev.gbenga.endurely.ui.theme.Purple
import dev.gbenga.endurely.ui.theme.largePadding
import dev.gbenga.endurely.ui.theme.normalPadding
import dev.gbenga.endurely.ui.theme.normalRadius
import dev.gbenga.endurely.ui.theme.smallPadding
import dev.gbenga.endurely.ui.theme.xLargePadding
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RoutineDetailScreen(navigation: EndureNavigation,
                        title: String,
                        routineId: String,
                        viewModel: RoutineDetailViewModel = koinViewModel()){
    val routineDetailUi by viewModel.routineDetail.collectAsStateWithLifecycle()
//    viewModel.savedStateHandle.set()
    val listState = rememberLazyListState()
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getRoutineDetails(routineId)
    }

    BoxLibrary(onBackRequest = {
        navigation.pop()
    }){

        LazyColumn (modifier = Modifier,
            state = listState) {
            item {
                ExpandedTopBar(title)
            }
            item {
                var prevCur by remember { mutableStateOf(0) }
                when(val uiState = routineDetailUi.userExercises){
                    is UiState.Success ->{
                        isLoading = false
                       Column(modifier = Modifier.padding(normalPadding)) {
                           uiState.data.mapIndexed {i, it -> AnimatedVisibility(prevCur != i, enter = fadeIn()) {
                               RoutineDetailItem(it)
                           }
                               prevCur = i
                           }
                       }
                    }
                    is UiState.Failure ->{
                        isLoading = false
                        // show error
                    }
                    is UiState.Loading ->{
                        isLoading = true
                    }
                    else ->{
                        isLoading = false
                        // do nothing
                    }
                }
            }
        }


    }

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        FitnessLoadingIndicator(modifier = Modifier, show = isLoading)
    }
}

@Composable
fun RoutineDetailItem(userExercise: UserExercise){
    Card(modifier = Modifier.clickable {

    }.fillMaxWidth().height(120.dp).padding(normalPadding),
        elevation = CardDefaults.cardElevation(3.dp)) {
        ConstraintLayout(modifier = Modifier
            .padding(normalPadding).fillMaxWidth().height(120.dp)) {
            val (title, time, completed, statusIc) = createRefs()
            Image(painter = painterResource(R.drawable.routine_ic),
                contentDescription = null,
                modifier = Modifier.constrainAs(statusIc){
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                    .clip(RoundedCornerShape(normalRadius)).background(Color(0xffFFCC80))
                    .width(100.dp).height(100.dp))
            Text(userExercise.exercise.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.constrainAs(title){
                    top.linkTo(parent.top)
                    start.linkTo(statusIc.end, normalPadding)
                })
            Text(userExercise.duration.duration(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.constrainAs(time){
                    top.linkTo(title.bottom)
                    start.linkTo(statusIc.end, normalPadding)
                })

            Text(userExercise.status(), style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.constrainAs(completed){
                    top.linkTo(time.bottom)
                    start.linkTo(statusIc.end, normalPadding)
                    bottom.linkTo(parent.bottom)
                })
        }
    }
}

@Composable
private fun ExpandedTopBar(title: String) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .fillMaxHeight(.2f),
        contentAlignment = Alignment.BottomStart
    ) {
        Image(
            modifier = Modifier.fillMaxWidth().height(250.dp),
            painter = painterResource(R.drawable.fitness_girl),
            contentDescription = null,
            contentScale = ContentScale.Fit,
        )
        Text(
            modifier = Modifier.padding(16.dp),
            text = title,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
}