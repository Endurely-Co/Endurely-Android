package dev.gbenga.endurely.meal

import androidx.compose.ui.graphics.Color
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.meal.data.GetMealPlan
import dev.gbenga.endurely.meal.data.GetMealPlanResponse
import dev.gbenga.endurely.meal.data.MealPlanResponse
import dev.gbenga.endurely.meal.data.NutrientItem
import dev.gbenga.endurely.meal.data.NutrientResponse
import dev.gbenga.endurely.routines.DayOfWeek

data class MealPlanUiState(
    val plannedMeal: UiState<MealPlanResponse> = UiState.Idle(),
    val mealNutrients: UiState<List<NutrientItem>,> = UiState.Idle(),
    val mealPlan : UiState<List<GetMealPlan>> = UiState.Idle(),
    val days: List<DayOfWeek> = emptyList(),
    val colors: List<Long> = emptyList(),
    val button: ButtonAction = ButtonAction.Continue(),
    val readOnlyTf: Boolean = false,
    )


data class MessageData(val feedbackMsg: String? = null, val reload: Boolean=false)

sealed interface ButtonAction{
    val title: String
    data class Continue(
        override val title: String="Continue",
        val onAction: () -> Unit ={}): ButtonAction
    data class AddMeal(override val title: String="Add Meal Plan"): ButtonAction
}