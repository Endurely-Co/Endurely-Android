package dev.gbenga.endurely.meal

import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.meal.data.GetMealPlan
import dev.gbenga.endurely.meal.data.MealPlanDetailsData

data class MealPlanDetailsState(
    val details: UiState<MealPlanDetailsData> = UiState.Idle())

