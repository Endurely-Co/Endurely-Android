package dev.gbenga.endurely.dashboard

import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.meal.data.GetMealPlan
import dev.gbenga.endurely.meal.data.MealPlan

data class DashboardUiState (
    val dashboardMenus : List<DashboardMenu> = listOf(),
    val homeScreenMeal: HomeScreenMeal = HomeScreenMeal(),
    val planMeals : List<GetMealPlan> = listOf(),
    val greeting : String = "",
    val pageTitles: List<String> = emptyList(),
    val fullName: UiState<String> = UiState.Idle(),
    val userInitial : String = "",
    val showAddRoutine: Boolean = false
)

data class HomeScreenMeal(val completedTotal: Triple<String, String, Float> = Triple("0","0", .1f),
    val mealPlans : UiState<List<GetMealPlan>> = UiState.Idle(),
    val planDay: String =""
)