package dev.gbenga.endurely.routines

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gbenga.endurely.R
import dev.gbenga.endurely.navigation.EndureNavigation
import dev.gbenga.endurely.routines.data.UserExercise
import dev.gbenga.endurely.routines.data.duration
import dev.gbenga.endurely.ui.theme.normalPadding
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RoutineDetailScreen(navigation: EndureNavigation, viewModel: RoutineDetailViewModel = koinViewModel()){
    val routineDetailUi by viewModel.routineDetail.collectAsStateWithLifecycle()
//    viewModel.savedStateHandle.set()
    val listState = rememberLazyListState()

    BoxLibrary{
        LazyColumn(
            modifier = Modifier.padding(),
            state = listState
        ) {
            item {
                ExpandedTopBar()
            }
            items(routineDetailUi.userExercises.size){
                RoutineDetailItem(routineDetailUi.userExercises[it])
            }
        }
    }
}

@Composable
fun RoutineDetailItem(userExercise: UserExercise){
    Card(modifier = Modifier.fillMaxWidth().height(120.dp)) {
        ConstraintLayout(modifier = Modifier
            .padding(normalPadding).fillMaxWidth().height(120.dp)) {
            val (title, time, completed, statusIc) = createRefs()
            Image(painter = painterResource(R.drawable.meal_plan_ic),
                contentDescription = null,
                modifier = Modifier.constrainAs(statusIc){
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }.size(100.dp))
            Text(userExercise.exercise.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.constrainAs(title){
                    top.linkTo(parent.top)
                    start.linkTo(statusIc.end)
                })
            Text(userExercise.duration.duration(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.constrainAs(time){
                    top.linkTo(title.bottom)
                })

            Text(userExercise.status, style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.constrainAs(completed){
                    top.linkTo(time.bottom)
                })
        }
    }
}

@Composable
private fun ExpandedTopBar() {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .height(EXPANDED_TOP_BAR_HEIGHT - COLLAPSED_TOP_BAR_HEIGHT),
        contentAlignment = Alignment.BottomStart
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.fitness_girl),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Library",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}