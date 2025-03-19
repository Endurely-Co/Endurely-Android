package dev.gbenga.endurely.dashboard

import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.meal.data.GetMealPlan

data class DashboardUiState (
    val dashboardMenus : List<DashboardMenu> = listOf(),
    val statsSummary: Triple<String, String, Float> = Triple("0","0", .1f),
    val planMeals : List<GetMealPlan> = listOf(),
    val greeting : String = "",
    val pageTitles: List<String> = emptyList(),
    val fullName: UiState<String> = UiState.Idle(),
    val userInitial : String = "",
    val showAddRoutine: Boolean = false
)