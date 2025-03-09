package dev.gbenga.endurely.routines

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gbenga.endurely.R
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.core.rememberDateTimeUtils
import dev.gbenga.endurely.navigation.EndureNavigation
import dev.gbenga.endurely.ui.EndurelyDatePicker
import dev.gbenga.endurely.ui.EndurelyAlertDialog
import dev.gbenga.endurely.ui.buttons.EndureOutlinedButton
import dev.gbenga.endurely.ui.buttons.EndurelyTextField
import dev.gbenga.endurely.ui.buttons.FitnessLoadingIndicator
import dev.gbenga.endurely.ui.buttons.GymScaffold
import dev.gbenga.endurely.ui.buttons.TextFieldButton
import dev.gbenga.endurely.ui.theme.appColor
import dev.gbenga.endurely.ui.theme.largePadding
import dev.gbenga.endurely.ui.theme.smallRadius
import dev.gbenga.endurely.ui.theme.xLargePadding
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewRoutineScreen(navigation: EndureNavigation, isDarkTheme: Boolean,
                        addNewRoutineViewModel: AddNewRoutineViewModel = koinViewModel()) {

    val addNewRoutineState by addNewRoutineViewModel.addRoutineUi.collectAsStateWithLifecycle()

    var routineNameValue by remember { mutableStateOf("")}
    var showDatePicker by remember { mutableStateOf(false) }
    val dateFormater = rememberDateTimeUtils()
    var dateValue by remember { mutableStateOf("Date") }
    var showTimePicker by remember { mutableStateOf(false) }
    var timeValue by remember { mutableStateOf("Time") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var showMessage by remember { mutableStateOf("") }


    Log.d("addNewRoutine", "state: ${addNewRoutineState.selectedExercises}")

    LaunchedEffect(showMessage) {
        if (showMessage.isNotBlank()){
            coroutineScope.launch {
                snackbarHostState.showSnackbar(showMessage)
            }
        }

    }

    EndurelyDatePicker(showDatePicker, onDismissRequest = {
        showDatePicker = false
    }, onSelectDate = {
        dateValue = dateFormater.getDate(it)
        //  dateValue = it.toString()
        addNewRoutineViewModel.setDate(it)
    }, isDarkTheme = isDarkTheme)

    var showExerciseDialog by remember { mutableStateOf(false) }

    ExerciseSuggestions(show = showExerciseDialog,
        isDarkTheme = isDarkTheme, onDismissed = {
            showExerciseDialog = false
        }){ name, exercise ->
        addNewRoutineViewModel.addExercise(name, exercise.duration, exercise.id)
    }


    RoutineTimePickerDialog(
        isDarkTheme =isDarkTheme,
        show = showTimePicker, onDismiss = {
            showTimePicker = false
        }) { hour, min ->
        timeValue = dateFormater.getTime(min, hour)
        addNewRoutineViewModel.setTime(hour, min)
    }

    AddNewRoutineContent(addNewRoutineState = addNewRoutineState,
        onSubmitClick = {
            addNewRoutineViewModel.submitRoutine()
        },
        snackbarHostState = snackbarHostState,
        onBackRequest={
            navigation.pop()
    }) {


        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(largePadding),
            verticalArrangement = Arrangement.spacedBy(
                largePadding
            )
        ) {
            item {
                EndurelyTextField(
                    value = routineNameValue,
                    onValueChanged = {
                        addNewRoutineViewModel.setRoutineName(it)
                        routineNameValue = it
                    }, label = R.string.routine_name
                )
            }
            item {

                ConstraintLayout(modifier = Modifier.fillMaxWidth(),) {
                    val (routineDate, routineTime) = createRefs()

                    TextFieldButton(onClick = {
                        showDatePicker = true
                    }, modifier = Modifier.constrainAs(routineDate) {
                        start.linkTo(parent.start)
                    }.fillMaxWidth(.4f),
                        title = dateValue)


                    TextFieldButton(onClick = {
                        showTimePicker = true
                    }, modifier = Modifier.constrainAs(routineTime) {
                        end.linkTo(parent.end)
                    }.fillMaxWidth(.4f), title = timeValue)
                }


                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = xLargePadding),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Selected Exercises", style = MaterialTheme.typography.titleMedium)
                    TextButton(
                        onClick = {
                            showExerciseDialog = true
                        }
                    ) {
                        Row {
                            Icon(Icons.Default.Add, contentDescription = "Add new routine")
                            Text(
                                stringResource(R.string.btn_add), style = MaterialTheme.typography
                                    .titleMedium.copy(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.W900
                                    )
                            )
                        }
                    }
                }
            }
            addNewRoutineState.selectedExercises.map {
                item {

                    ExerciseSection(it.name,
                        isDarkTheme = isDarkTheme, onRemoveClick ={
                            addNewRoutineViewModel.removeExercise(it.exercise.id)
                        }
                    )
                }
            }

        }

    }

    LaunchedEffect(addNewRoutineState.addedNewRoutine) {
        if (addNewRoutineState.addedNewRoutine is UiState.Success ){
            navigation.pop()
           // navigation.pop()
        }
    }

    when(val addedNewRoutineUi = addNewRoutineState.addedNewRoutine){
        is UiState.Failure ->{
            showMessage = addedNewRoutineUi.message
        }
        is UiState.Loading ->{
            showMessage =""
            FitnessLoadingIndicator()
        }
        else ->{
            showMessage =""
        }
    }
}

@Composable
fun AddNewRoutineContent(addNewRoutineState: AddNewRoutineState,
                         snackbarHostState: SnackbarHostState,
                         onSubmitClick: () -> Unit,
                         onBackRequest: () -> Unit,
                         content: @Composable () -> Unit,
){

    GymScaffold(
        snackbarHostState = snackbarHostState,
        onBackRequest =onBackRequest,
        pageTitle = "New Routine",
        actions = {
            EndureOutlinedButton("Save",
                enabled = addNewRoutineState.enableSubmit,
                onClick = onSubmitClick)
        }


    ) {


        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (mainContent) = createRefs()

            Box(modifier = Modifier.constrainAs(mainContent){
                top.linkTo(parent.top)
            }) {
                content()
            }

        }
    }
}

@Composable
fun  LazyItemScope.ExerciseSection(title: String, isDarkTheme: Boolean, onRemoveClick: () -> Unit){
    Row(modifier = Modifier
        .animateItem()
        .fillMaxWidth()
        .clip(RoundedCornerShape(smallRadius))
        .background(appColor(isDarkTheme).defaultCard), horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically) {
        Text(title, modifier = Modifier
            .fillMaxWidth(.9f)
            .padding(largePadding),
            style = MaterialTheme.typography.titleMedium)
        IconButton(onClick = onRemoveClick) {
            Icon(Icons.Default.Clear, contentDescription = "remove", tint = Color.Red)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineTimePickerDialog(show: Boolean,
                            isDarkTheme : Boolean, onDismiss: () -> Unit,  onConfirm: (Int, Int) -> Unit){
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime[Calendar.HOUR_OF_DAY],
        initialMinute = currentTime[Calendar.MINUTE],
        is24Hour = false,
    )
    EndurelyAlertDialog(
        show =show,
        isDarkTheme =isDarkTheme,
        onDismiss = { onDismiss()},
        onConfirm = {
            onConfirm(timePickerState.hour, timePickerState.minute)
            onDismiss()
        }
    ) {
        TimePicker(
            state = timePickerState,

        )
    }
}