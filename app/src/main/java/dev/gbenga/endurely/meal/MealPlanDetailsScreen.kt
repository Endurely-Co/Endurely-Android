package dev.gbenga.endurely.meal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gbenga.endurely.extensions.titleCase
import dev.gbenga.endurely.navigation.EndureNavigation
import dev.gbenga.endurely.ui.buttons.FitnessLoadingIndicator
import dev.gbenga.endurely.ui.buttons.GymScaffold
import dev.gbenga.endurely.ui.buttons.effect
import dev.gbenga.endurely.ui.theme.largePadding
import dev.gbenga.endurely.ui.theme.normalPadding
import org.koin.androidx.compose.koinViewModel

@Composable
fun MealPlanDetailsScreen(viewModel: MealPlanDetailsViewModel = koinViewModel(),
                          navigation: EndureNavigation,
                          planId: String) {

    val mealPlanDetailsUi by viewModel.mealPlanDetailsState.collectAsStateWithLifecycle()

    LaunchedEffect(planId) {
        viewModel.getMealPlanForUserById(planId)
    }

    GymScaffold(
        pageTitle = "Meal Plan Details",
        onBackRequest = {
            navigation.pop()
        },
        actions = {
            TextButton(onClick = {
                // delete meal plan
            }) {
                Text("Delete")
            }
        }
    ) {
        MealPlanDetailsContent(mealPlanDetailsUi, ) {

        }
    }
}


@Composable
fun MealPlanDetailsContent(mealDetailPlanUi: MealPlanDetailsState,
                        showMessage: (String) -> Unit){

    var mineralState by remember { mutableStateOf(false) }
    var microNutrientState by remember { mutableStateOf(false) }
    var vitaminsState by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        mineralState = true
    }

    LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        item {
            val isLoading = mealDetailPlanUi.details.effect(onError = {
                showMessage(it)
            }) { nutrients ->
                nutrients.let { detail ->

                    Column(modifier = Modifier.padding(largePadding)) {

                        Text(
                            detail.nutrients.item.titleCase(),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.fillMaxWidth().padding(bottom = normalPadding)
                        )

                        Card(modifier = Modifier.fillMaxWidth().padding(bottom = normalPadding)) {
                            Text(
                                detail.nutrients.otherNutrients,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.fillMaxWidth().padding(normalPadding)
                            )
                        }
                        AnimatedDropDown("Minerals", expand = mineralState,
                            onClick = {
                                mineralState = it
                            }, detail.nutrients.minerals
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
                            }, detail.nutrients.vitamins
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
                            }, detail.nutrients.macronutrients
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
            }


            Box (modifier = Modifier.fillMaxWidth()){
                AnimatedVisibility(isLoading, modifier = Modifier.align(Alignment.TopStart)) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
            }
        }


    }
}