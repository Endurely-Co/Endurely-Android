package dev.gbenga.endurely.dashboard

import android.util.Log
import dev.gbenga.endurely.R
import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.core.Tokens
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.extensions.titleCase
import dev.gbenga.endurely.onboard.data.RepoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DashboardViewModel(private val dashboardRepository: DashboardRepository, private val greeting: Greeting) : EndureNavViewModel() {

    private val _dashboardUi = MutableStateFlow(DashboardUiState())
    val dashboardUi = _dashboardUi.asStateFlow()

    private val _signOut = MutableStateFlow<UiState<String>>(UiState.Idle<String>())
    val signOut = _signOut.asStateFlow()

    init {

        runInScope {

            dashboardRepository.getUser().let{ loginData ->
                _dashboardUi.update { it.copy(
                    fullName = UiState.Success(loginData.data.firstName.titleCase())) }
            }


        }

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
            ), greeting = greeting.getGreeting(), pageTitles = listOf(Tokens.dashboard, Tokens.gymRoutine, Tokens.settings)
            )
        }

    }

    fun signOut(){
        runInScope {
            when(val logout = dashboardRepository.logout() ){
                is RepoState.Success ->{
                    _signOut.update { UiState. Success("Successfully signed out")}
                }
                is RepoState.Error ->{
                    _signOut.update { UiState.Failure("Something went wrong. Failed to log out") }
                }
            }
        }
    }
}