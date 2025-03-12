package dev.gbenga.endurely.meal

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomAppBarState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gbenga.endurely.R
import dev.gbenga.endurely.core.SevenDaysChips
import dev.gbenga.endurely.core.rememberDateTimeUtils
import dev.gbenga.endurely.extensions.titleCase
import dev.gbenga.endurely.navigation.EndureNavigation
import dev.gbenga.endurely.ui.EndurelyDatePicker
import dev.gbenga.endurely.ui.buttons.EndureButton
import dev.gbenga.endurely.ui.buttons.EndureOutlinedButton
import dev.gbenga.endurely.ui.buttons.EndurelyBottomSheet
import dev.gbenga.endurely.ui.buttons.EndurelyTextField
import dev.gbenga.endurely.ui.buttons.FitnessLoadingIndicator
import dev.gbenga.endurely.ui.buttons.GymScaffold
import dev.gbenga.endurely.ui.buttons.effect
import dev.gbenga.endurely.ui.theme.btnNormal
import dev.gbenga.endurely.ui.theme.floatingPadding
import dev.gbenga.endurely.ui.theme.largePadding
import dev.gbenga.endurely.ui.theme.normalPadding
import dev.gbenga.endurely.ui.theme.normalRadius
import dev.gbenga.endurely.ui.theme.smallPadding
import dev.gbenga.endurely.ui.theme.xLargePadding
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealPlanScreen(navController: EndureNavigation,
                   isDarkMode: Boolean,
                   viewModel: MealPlanViewModel = koinViewModel()) {

    val mealPlanUi by viewModel.mealPlanUi.collectAsStateWithLifecycle()

    val daysState = rememberLazyListState()

    val snackbarHostState = remember { SnackbarHostState() }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val addNewMealPlanSheet = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    var dateValue by remember { mutableStateOf("Enter date") }

    var showBottomSheet by remember { mutableStateOf(false) }
    var mealValue by remember { mutableStateOf("") }
    val dateFormater = rememberDateTimeUtils()
    var message by remember { mutableStateOf("") }

    LaunchedEffect(message) {
        if (message.isNotBlank()){
            snackbarHostState.showSnackbar(message)
        }
    }

    LaunchedEffect(showBottomSheet) {
        Log.d("showBottomSheet", "showBottomSheet -> $showBottomSheet")
    }

    GymScaffold(
        isDarkMode = isDarkMode,
        pageTitle = "Meal Plan",
        snackbarHostState = snackbarHostState,
        onBackRequest = {
            navController.pop()
        }, //.padding(bottom = floatingPadding)
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {
                coroutineScope.launch {
                    showBottomSheet = true
                }
            }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_fastfood_24),
                    contentDescription = "Add Plan",
                    modifier = Modifier.padding(end = normalPadding)
                )
                Text("New Plan")
            }
        }

    ) {
        NewMealBottomSheet(showBottomSheet, addNewMealPlanSheet,
            mealValue,
            onMealChanged = {
                mealValue = it.split(" ").let { meals ->
                    if (meals.size > 1) {
                        meals[0]
                    } else {
                        it
                    }
                }
            }, onDismissRequest = {
                showBottomSheet = false
            }){


            val plannedMealLoading = mealPlanUi.plannedMeal.effect(onError = {
                //snackbarHostState.showSnackbar(it)
                message = it
            }, onOther = {
                message =""
            }) {
                message = it.data.message
                showBottomSheet = false
                viewModel.clearState()
            }

            FitnessLoadingIndicator(show = plannedMealLoading)

            MealNutrientContent(mealPlanUi, expandSheet = {
                coroutineScope.launch {
                    addNewMealPlanSheet.expand()
                }
            }, onNutrientRequest = {
                if (it is ButtonAction.Continue){
                    viewModel.requestFoodNutrients(mealValue)
                }else{
                    viewModel.planMeal(dateFormater.getDateMillis())
                }
            }, isDarkTheme =isDarkMode,
                dateValue =dateValue,
                onDateChanged = { dateStr, millis ->
                    dateValue = dateFormater.getDate(millis)
            } ) {
                coroutineScope.launch {

                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    //snackbarHostState.showSnackbar(it)
                }
            }
        }

        MealPlanContent(mealPlanUi, daysState, showMessage = {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(it)
            }
        }, changeDayRequest = {
            viewModel.selectDay(it)
        }, onClickItem = {
            navController.gotoMealPlanDetails(it)
        })

    }

}

@Composable
fun MealPlanContent(mealPlanUi: MealPlanUiState, daysState: LazyListState,
                    showMessage: (String) -> Unit,
                    onClickItem: (String) -> Unit,
                    changeDayRequest: (String) -> Unit) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (topSevenDays, mealPlans, emptyContent) = createRefs()

        SevenDaysChips(lazyList = daysState, days
        = mealPlanUi.days, modifier = Modifier
            .constrainAs(topSevenDays) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .wrapContentHeight()) {
            changeDayRequest(it)
        }


        val loading = mealPlanUi.mealPlan
            .effect(modifier = Modifier
                .constrainAs(mealPlans) {
                    top.linkTo(topSevenDays.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxSize(), onError = {

                showMessage(it)
                //snackbarHostState.showSnackbar(it)
            }) {
                if (it.isEmpty()) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .constrainAs(emptyContent) {
                            top.linkTo(topSevenDays.bottom)
                            bottom.linkTo(parent.bottom)
                        }
                        .padding(normalPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        Text(
                            stringResource(R.string.no_meal_available),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(largePadding)
                    ) {
                        it.mapIndexed { index, plan ->
                            item {
                                MealPlanItem(
                                    plan.foodName, plan.otherNutrients,
                                    Color(
                                        mealPlanUi.colors[if (
                                            index >= mealPlanUi.colors.size) {
                                            index % 2
                                        } else index]
                                    )
                                ) {
                                    onClickItem(plan.mealPlanId)
                                }
                            }
                        }
                    }
                }

            }

        FitnessLoadingIndicator(show = loading,
            modifier = Modifier.constrainAs(mealPlans){
                bottom.linkTo(parent.bottom)
                top.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealNutrientContent(mealPlanUi: MealPlanUiState,
                        dateValue: String,
                        isDarkTheme: Boolean,
                        expandSheet: () -> Unit,
                        onNutrientRequest: (ButtonAction) -> Unit,
                        onDateChanged: (String, Long) -> Unit,
                        showMessage: (String) -> Unit){

    var mineralState by remember { mutableStateOf(false) }
    var microNutrientState by remember { mutableStateOf(false) }
    var vitaminsState by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    EndurelyDatePicker(showDatePicker, onDismissRequest = {
        showDatePicker = false
    }, onSelectDate = {

        onDateChanged(dateValue, it)
        //  dateValue = it.toString()
    }, isDarkTheme = isDarkTheme)

    LazyColumn(modifier = Modifier) {
        item {
            EndureOutlinedButton(
                text =  dateValue,
                modifier = Modifier
                    .padding(vertical = largePadding)
                    .height(btnNormal)
                    .fillMaxWidth()) {
                showDatePicker = true
            }

        }
        item {
            val isLoading = mealPlanUi.mealNutrients.effect(onError = {
                showMessage(it)
            }) { nutrients ->
                nutrients[0].let { nutrient ->

                    Column {
                        Text(
                            nutrient.item.titleCase(),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.fillMaxWidth().padding(bottom = normalPadding)
                        )

                        Card(modifier = Modifier.fillMaxWidth().padding(bottom = normalPadding)) {
                            Text(
                                nutrient.otherNutrients,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.fillMaxWidth().padding(normalPadding)
                            )
                        }
                        AnimatedDropDown("Minerals", expand = mineralState,
                            onClick = {
                                mineralState = it
                            }, nutrient.minerals
                        ) { mineral ->
                            Text(
                                mineral.name.titleCase(), style = MaterialTheme.typography
                                    .titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(mineral.summary, style = MaterialTheme.typography.bodyMedium)
                        }

                        AnimatedDropDown("Micronutrients", expand = microNutrientState,
                            onClick = {
                                microNutrientState = it
                            }, nutrient.vitamins
                        ) { mineral ->
                            Text(
                                mineral.name.titleCase(), style = MaterialTheme.typography
                                    .titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(mineral.summary, style = MaterialTheme.typography.bodyMedium)
                        }

                        AnimatedDropDown("Vitamins", expand = vitaminsState,
                            onClick = {
                                vitaminsState = it
                            }, nutrient.macronutrients
                        ) { mineral ->
                            Text(
                                mineral.name.titleCase(), style = MaterialTheme.typography
                                    .titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(mineral.summary, style = MaterialTheme.typography.bodyMedium)
                        }
                    }

                    //  }
                }
                expandSheet()
            }


            Box (modifier = Modifier.fillMaxWidth()){
                AnimatedVisibility(isLoading, modifier = Modifier.align(Alignment.TopStart)) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())}
            }
        }
        item{
            EndureButton(
                mealPlanUi.button.title,
                modifier = Modifier
                    .padding(vertical = xLargePadding)
                    .fillMaxWidth()) {
                onNutrientRequest(mealPlanUi.button)
            }
        }

    }
}


@Composable
fun <E> AnimatedDropDown(title: String,
                         expand: Boolean,
                         onClick: (Boolean) -> Unit,
                         items: List<E>,
                         subContent: @Composable (E) -> Unit){

    Card(modifier = Modifier.clickable { onClick(!expand) }
        .fillMaxWidth().padding(bottom = normalPadding)) {
        Column(modifier = Modifier.padding(normalPadding)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text(title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier)
                Icon(if(expand) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = title, Modifier.size(50.dp))
            }
            AnimatedVisibility(expand) {
                Column {
                    Divider(modifier = Modifier.fillMaxWidth())
                    items.mapIndexed { index, item ->
                        Column(modifier = Modifier.padding(smallPadding)){
                            subContent(item)
                        }
                        Divider(modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewMealBottomSheet(showBottomSheet: Boolean,
                       addNewMealPlan: SheetState,
                       mealValue: String,
                       onMealChanged: (String) -> Unit,
                       onDismissRequest: () -> Unit,
content: @Composable () -> Unit){

    EndurelyBottomSheet(
        showBottomSheet,
        sheetState = addNewMealPlan,
        onDismissRequest = onDismissRequest ){
        Text(
            stringResource(R.string.new_meal_plan),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = largePadding))
        EndurelyTextField(value = mealValue, label = R.string.lb_meal) {
            onMealChanged(it)
        }

        content()

    }

}

@Composable
fun MealPlanItem(title: String, subtitle: String, color: Color,
                 onClick: () -> Unit){
    Box(modifier = Modifier
        .padding(bottom = largePadding)
        .clip(RoundedCornerShape(normalRadius))
        .clickable { onClick() }
        .height(200.dp)
        .fillMaxWidth()){
        Image(painter = painterResource(R.drawable.breakfast),
            contentDescription = "",
            modifier = Modifier
                .background(color.copy(alpha = .8f))
                .height(200.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop)
        Column(
            modifier = Modifier
                .background(Color.Black.copy(alpha = .4f))
                .padding(normalPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(title.titleCase(), style = MaterialTheme.typography.headlineSmall
                .copy(fontWeight = FontWeight.Bold, color = Color.White))
            Text(subtitle, style = MaterialTheme.typography
                .bodyMedium.copy(fontWeight = FontWeight.W900, color = Color.White), maxLines = 2)
        }
    }
}

