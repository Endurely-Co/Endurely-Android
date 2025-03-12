package dev.gbenga.endurely.routines

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gbenga.endurely.R
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.core.rememberDateTimeUtils
import dev.gbenga.endurely.navigation.EndureNavigation
import dev.gbenga.endurely.ui.EndurelyDatePicker
import dev.gbenga.endurely.ui.buttons.EndurelyTextField
import dev.gbenga.endurely.ui.buttons.FitnessLoadingIndicator
import dev.gbenga.endurely.ui.buttons.TextFieldButton
import dev.gbenga.endurely.ui.theme.largePadding
import dev.gbenga.endurely.ui.theme.xLargePadding
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRoutineScreen (
    navigation: EndureNavigation,
    isDarkTheme: Boolean,
    viewModel: EditRoutineViewModel = koinViewModel()){

    val editRoutineState by viewModel.editRoutineState.collectAsStateWithLifecycle()

    var routineNameValue by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    val dateFormater = rememberDateTimeUtils()
    var dateValue by remember { mutableStateOf("Date") }
    var showTimePicker by remember { mutableStateOf(false) }
    var timeValue by remember { mutableStateOf("Time") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var showMessage by remember { mutableStateOf("") }


    Log.d("addNewRoutine", "state: ${editRoutineState.selectedExercises}")

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
        viewModel.setDate(it)
    }, isDarkTheme = isDarkTheme)

    var showExerciseDialog by remember { mutableStateOf(false) }

    ExerciseSuggestions(show = showExerciseDialog,
        isDarkTheme = isDarkTheme, onDismissed = {
            showExerciseDialog = false
        }){ name, exercise ->
        viewModel.addExercise(name, exercise.duration, exercise.id)
    }


    RoutineTimePickerDialog(
        isDarkTheme =isDarkTheme,
        show = showTimePicker, onDismiss = {
            showTimePicker = false
        }) { hour, min ->
        timeValue = dateFormater.getTime(min, hour)
        viewModel.setTime(hour, min)
    }

    AddNewRoutineContent(addNewRoutineState = editRoutineState,
        onSubmitClick = {
          //  viewModel.submitRoutine()
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
                        viewModel.setRoutineName(it)
                        routineNameValue = it
                    }, label = R.string.routine_name
                )
            }
            item {

                ConstraintLayout(modifier = Modifier.fillMaxWidth(),) {
                    val (routineDate, routineTime, routineDateTimeTile) = createRefs()

                    Text("Routine date", style = MaterialTheme.typography.titleLarge, modifier = Modifier.constrainAs(routineDateTimeTile){
                        start.linkTo(parent.start)
                    })

                    TextFieldButton(onClick = {
                        showDatePicker = true
                    }, modifier = Modifier.constrainAs(routineDate) {
                        top.linkTo(routineDateTimeTile.bottom)
                        start.linkTo(parent.start)
                    }.fillMaxWidth(.4f),
                        title = dateValue)

                    TextFieldButton(onClick = {
                        showTimePicker = true
                    }, modifier = Modifier.constrainAs(routineTime) {
                        top.linkTo(routineDateTimeTile.bottom)
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
            editRoutineState.selectedExercises.map {
                item {

                    ExerciseSection(it.name,
                        isDarkTheme = isDarkTheme, onRemoveClick ={
                            viewModel.removeExercise(it.exercise.id)
                        }
                    )
                }
            }

        }

    }

    LaunchedEffect(editRoutineState.addedNewRoutine) {
        if (editRoutineState.addedNewRoutine is UiState.Success ){
            navigation.pop()
            // navigation.pop()
        }
    }

    when(val addedNewRoutineUi = editRoutineState.addedNewRoutine){
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