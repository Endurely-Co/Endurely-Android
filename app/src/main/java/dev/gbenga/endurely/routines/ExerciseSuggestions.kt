package dev.gbenga.endurely.routines

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gbenga.endurely.R
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.routines.data.NewExercise
import dev.gbenga.endurely.ui.EndurelyAlertDialog
import dev.gbenga.endurely.ui.buttons.EndureOutlinedButton
import dev.gbenga.endurely.ui.buttons.EndurelyTextField
import dev.gbenga.endurely.ui.buttons.NoLabelTextField
import dev.gbenga.endurely.ui.theme.appColor
import dev.gbenga.endurely.ui.theme.btnNormal
import dev.gbenga.endurely.ui.theme.largePadding
import dev.gbenga.endurely.ui.theme.normalPadding
import dev.gbenga.endurely.ui.theme.normalRadius
import dev.gbenga.endurely.ui.theme.normalThickness
import dev.gbenga.endurely.ui.theme.thinThickness
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseSuggestions(
    viewModel: ExerciseSuggestionsViewModel = koinViewModel(),
    show: Boolean, isDarkTheme: Boolean,
    onDismissed: () -> Unit,
    onSelectedExercise: (String, NewExercise) -> Unit){

    if (show){
        val textField = FocusRequester()
        val searchedExercise by viewModel.searchExercises.collectAsStateWithLifecycle()
       // var searchedText by remember { mutableStateOf("") }

        var searchedText by remember { mutableStateOf(TextFieldValue(text = "")) }
        var duration by rememberSaveable { mutableStateOf("") }
        var selectedExercise by rememberSaveable { mutableStateOf(Pair( "", 0L)) }

        val context = LocalContext.current

        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
        )

        ModalBottomSheet(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(appColor(isDarkTheme).defaultCard),
            sheetState = sheetState,
            onDismissRequest = {
                onDismissed()
                duration = ""
                searchedText = searchedText.copy(text = "")
                viewModel.closeSuggestionPanel()
                selectedExercise = selectedExercise.copy("", 0)
            }
        ) {
            ConstraintLayout(modifier = Modifier
                .fillMaxSize()
                .padding(normalPadding)) {
                val (searchField, suggestions, timeDate, title, dateTimeTitle) = createRefs()

                Row(    modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Add Exercise",
                        style = MaterialTheme.typography.bodyLarge,

                        ) //"Save"
                    EndureOutlinedButton (onClick = {
                        if (listOf(selectedExercise.first, duration).all { d -> d.isNotBlank() }
                            && selectedExercise.second != 0L){

                            onSelectedExercise(selectedExercise.first,
                                NewExercise(id =selectedExercise.second,
                                    duration = duration))
                            onDismissed()
                        }
                    }, text = "Save")
                }


                TextField(value = searchedText,
                    singleLine = true,
                    placeholder = { Text("Search for exercise") },
                    shape = RoundedCornerShape(normalRadius),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700),
                    visualTransformation =  VisualTransformation.None,
                    colors  = TextFieldDefaults.colors().copy(focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent),
                    onValueChange = { value ->
                        searchedText = value
                        viewModel.search(value.text)
                    }, modifier = Modifier
                        .constrainAs(searchField) {
                            top.linkTo(title.bottom, largePadding)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .fillMaxWidth()
                        .focusRequester(textField))

                Text("", modifier = Modifier.constrainAs(dateTimeTitle){
                    top.linkTo(searchField.bottom, largePadding)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
                ExerciseDuration(modifier = Modifier
                    .constrainAs(timeDate) {
                        top.linkTo(dateTimeTitle.bottom, normalPadding)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth(), onTimeChanged = {
                    duration = it
                })


                when(val searchUi = searchedExercise.searchUi){
                    is UiState.Success ->{
                        Box( modifier = Modifier
                            .constrainAs(suggestions) {
                                top.linkTo(searchField.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .wrapContentHeight()
                            .padding(normalPadding)) {
                            LazyColumn(horizontalAlignment = Alignment.Start,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(normalRadius))
                                    .background(
                                        appColor(isDarkTheme).defaultCard
                                    )) {
                                items(searchUi.data.size){
                                    TextButton(onClick = {
                                        searchUi.data[it].let {
                                            searchedText = searchedText.copy(text = it.name,
                                                selection = TextRange(it.name.length)
                                            )
                                            selectedExercise = Pair(it.name, it.id)
                                           viewModel.closeSuggestionPanel()
                                        }
                                    },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(btnNormal)
                                            .align(Alignment.CenterStart)
                                    ) {
                                        Text(searchUi.data[it].name,
                                            style = MaterialTheme.typography.bodyMedium
                                                .copy(fontWeight = FontWeight.W900))
                                    }
                                    Divider(thickness = thinThickness)
                                }
                            }
                        }
                    }
                    is UiState.Failure ->{
                        Toast.makeText(context, searchUi.message, Toast.LENGTH_SHORT).show()
                    }

                    //is UiState.Loading ->{}
                    else -> {
                        // Nothing
                    }
                }

            }
        }

    }

}

@Composable
fun ExerciseDuration(modifier: Modifier =Modifier, onTimeChanged: (String) -> Unit){

    var minsValue by remember { mutableStateOf("") }
    var hrsValue by remember { mutableStateOf("") }

    Row(modifier =modifier, horizontalArrangement = Arrangement.spacedBy(largePadding)) {
        EndurelyTextField(minsValue,
            label = R.string.lb_minutes,
            modifier = Modifier
                .fillMaxWidth(.3f),
            keyboardType = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)) {
            if (it.isBlank()){
                minsValue = ""
                return@EndurelyTextField
            }
            if(it.isDigitsOnly() && it.length < 3 && it.toInt() <24) {
                minsValue = it
                onTimeChanged("$minsValue:$hrsValue")
            }
        }
        EndurelyTextField(hrsValue,
            label = R.string.lb_hours,
            modifier = Modifier
                .fillMaxWidth(.35f),
            keyboardType = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)) {
            if (it.isBlank()){
                hrsValue = ""
                return@EndurelyTextField
            }
            if(it.isDigitsOnly() && it.length < 3 && it.toInt() <= 60){
                hrsValue = it
                onTimeChanged("$minsValue:$hrsValue")
            }
        }
    }
}

