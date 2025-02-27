package dev.gbenga.endurely.dashboard

import dev.gbenga.endurely.R
import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.core.Tokens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DashboardViewModel(private val dashboardRepository: DashboardRepository) : EndureNavViewModel() {

    private val _dashboardUi = MutableStateFlow(DashboardUiState())
    val dashboardUi = _dashboardUi.asStateFlow()

    init {
        _dashboardUi.update {
            it.copy(dashboardMenus = listOf(
                DashboardMenu(title = Tokens.trainingPlan,
                    bgColor = 0xFFE53935.toInt(),
                    clipArt = R.drawable.training_plan_ic),
                DashboardMenu(title = Tokens.trackCalories,
                    bgColor = 0xFFE3C1A5.toInt(),
                    clipArt = R.drawable.calorie_tracker_ic),
                DashboardMenu(title = Tokens.mealPlan,
                    bgColor = 0xFF66BB6A.toInt(),
                    clipArt = R.drawable.meal_plan_ic),
                DashboardMenu(title = Tokens.fitnessRecommendation,
                    bgColor = 0xFF42A5F5.toInt(),
                    clipArt = R.drawable.fitness_recomm_ic),
            ))
        }
    }
}