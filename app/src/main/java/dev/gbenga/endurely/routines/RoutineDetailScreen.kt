package dev.gbenga.endurely.routines

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import dev.gbenga.endurely.ui.buttons.EndureButton
import dev.gbenga.endurely.ui.buttons.FitnessLoadingIndicator
import dev.gbenga.endurely.ui.theme.largePadding
import dev.gbenga.endurely.ui.theme.normalPadding
import dev.gbenga.endurely.ui.theme.normalRadius
import dev.gbenga.endurely.ui.theme.smallPadding
import org.koin.androidx.compose.koinViewModel


@Composable
fun RoutineDetailScreen(navigation: EndureNavigation,
                        title: String,
                        routineId: String,
                        viewModel: RoutineDetailViewModel = koinViewModel()){
    val routineDetailUi by viewModel.routineDetail.collectAsStateWithLifecycle()

    Log.d("routineDetailUi", routineId)
    val listState = rememberLazyListState()
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(routineId) {
        viewModel.getRoutineDetails(routineId)
    }

    BoxLibrary(onBackRequest = {
        navigation.pop()
    }, onRemoveRequest = {

    }){


        val exerciseDetailsUi by viewModel.exerciseDetailsUi.collectAsStateWithLifecycle()

        RoutineBottomSheet(title = exerciseDetailsUi.title,
            content = exerciseDetailsUi.description,
            duration = exerciseDetailsUi.duration,
            showBottomSheet = exerciseDetailsUi.show){
            viewModel.hideDetails()
        }

        var selected by remember { mutableStateOf(false) }
        LazyColumn (modifier = Modifier,
            state = listState) {
            item {
                ExpandedTopBar(title)
            }
            item {
                LazyRow(modifier = Modifier
                    .padding(horizontal = normalPadding)
                    .padding(top = normalPadding).fillMaxWidth(), ) {
                    items(2){
                        AppChip(selected= selected){
                            selected = !selected
                        }
                    }
                }
                var prevIndex by rememberSaveable  { mutableStateOf(-1) }
                when(val uiState = routineDetailUi.userExercises){
                    is UiState.Success ->{
                        isLoading = false
                       Column(modifier = Modifier.padding(normalPadding)) {
                           uiState.data.mapIndexed {i, it ->
                               RoutineDetailItem(it){ exercise ->
                                   viewModel.showDetails(exercise)
                               }
                               prevIndex = i
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
fun RoutineDetailItem(userExercise: UserExercise, onClickExercise: (UserExercise) -> Unit){
    Card(modifier = Modifier.fillMaxWidth().height(120.dp).padding(normalPadding)
        .clickable {
            onClickExercise(userExercise)
    }, shape = RoundedCornerShape(normalRadius),
        elevation = CardDefaults.cardElevation(3.dp)) {
        ConstraintLayout(modifier = Modifier
            .padding(normalPadding).fillMaxWidth().height(120.dp)) {
            val (title, time, completed, statusIc) = createRefs()
            Image(painter = painterResource(R.drawable.routine_clipart),
                contentDescription = null,
                modifier = Modifier.constrainAs(statusIc){
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                    .clip(RoundedCornerShape(normalRadius))
                    .background(Color(0xffFFFDE7))
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
            .background(Color(0xffB3E5FC))
            .fillMaxWidth()
            .fillMaxHeight(.2f),
        contentAlignment = Alignment.BottomStart
    ) {
        Image(
            modifier = Modifier.fillMaxWidth().height(250.dp),
            painter = painterResource(R.drawable.routine_ic),
            contentDescription = null,
            contentScale = ContentScale.Fit,
        )
        Text(
            modifier = Modifier.padding(16.dp),
            text = title,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineMedium
                .copy(fontWeight = FontWeight.Bold)
        )
    }
}

// var selected by remember { mutableStateOf(false) }
@Composable
fun AppChip(modifier: Modifier =Modifier, title: String="New chip",selected: Boolean, enabled: Boolean = true, onSelect: (Boolean) -> Unit) {

    FilterChip(
        modifier = modifier.height(40.dp).padding(horizontal = normalPadding),
        selected = selected,
        onClick = { onSelect(selected)},
        enabled = enabled,
        label = { Text(title) },
        colors = FilterChipDefaults.filterChipColors(selectedLabelColor = MaterialTheme.colorScheme.primary),
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineBottomSheet(showBottomSheet: Boolean, title: String,
                       content: String, duration: String,
                       onDismissRequest: (Boolean) -> Unit) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    val scrollState = rememberScrollableState(consumeScrollDelta = {0f})

    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(.5f),
            sheetState = sheetState,
            onDismissRequest = { onDismissRequest(false) }
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = largePadding)
                    .padding(bottom = largePadding)
                    .scrollable(scrollState, orientation = Orientation.Vertical)

                    .fillMaxHeight(),
            ) {

                val (button, text, closeBtn) = createRefs()

                IconButton(modifier = Modifier.constrainAs(closeBtn){
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }.clip(CircleShape).padding(smallPadding).wrapContentSize(),
                    colors = IconButtonDefaults
                        .iconButtonColors(containerColor = MaterialTheme
                            .colorScheme.primary.copy(alpha = .4f),
                            contentColor = Color.White),
                    onClick = {
                    onDismissRequest(false)
                }) {
                    Icon(Icons.Default.Clear, contentDescription = "close routine details")
                }

                Column(modifier = Modifier.constrainAs(text){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.wrapContentHeight().fillMaxWidth()) {
                    Text(
                        title,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = smallPadding)
                    )
                    Row(modifier = Modifier.wrapContentWidth().padding(bottom = normalPadding)) {
                        Icon(painter = painterResource(R.drawable.baseline_timer_24),
                            contentDescription = "Exercise duration")
                        Text(
                            "Duration: ${duration.duration()}",
                            style = MaterialTheme.typography.bodySmall
                                .copy(fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.typography.bodySmall.color.copy(alpha = .5f)),
                            modifier = Modifier.padding(vertical = smallPadding, horizontal = smallPadding)
                        )
                    }

                    Text(
                        content,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding()
                    )
                }

                EndureButton("Mark as Complete",
                    modifier = Modifier.fillMaxWidth().constrainAs(button){
                        //top.linkTo(text.bottom, margin = xXLargePadding)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }) { }
            }


        }
    }


}
