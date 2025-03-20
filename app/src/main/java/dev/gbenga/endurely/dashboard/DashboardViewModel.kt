package dev.gbenga.endurely.dashboard

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import dev.gbenga.endurely.R
import dev.gbenga.endurely.core.DateUtilsNames
import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.core.Tokens
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.extensions.titleCase
import dev.gbenga.endurely.meal.MealPlanRepository
import dev.gbenga.endurely.meal.data.GetMealPlan
import dev.gbenga.endurely.onboard.data.RepoState
import dev.gbenga.endurely.routines.DashboardPages
import dev.gbenga.endurely.routines.RoutineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(private val dashboardRepository: DashboardRepository,
                         private val greeting: Greeting,
                         private val routineRepository: RoutineRepository,
                         private val mealPlanRepository: MealPlanRepository,
                         private val dateUtil : DateUtilsNames,
                         private val savedStateHandle: SavedStateHandle) : EndureNavViewModel() {

    private val _dashboardUi = MutableStateFlow(DashboardUiState())
    val dashboardUi = _dashboardUi.asStateFlow()

    private val _signOut = MutableStateFlow<UiState<String>>(UiState.Idle<String>())
    val signOut = _signOut.asStateFlow()

    companion object{
        const val CUR_PAGE ="Current_Screen"
    }

    init {

        runInScope {
            dashboardRepository.getUser().let{ loginData ->
                if (loginData is RepoState.Success) {
                    _dashboardUi.update { it.copy(
                        fullName = UiState.Success(loginData.data.data.firstName.titleCase()),
                        userInitial = loginData.data.data.firstName.first().uppercase()
                    ) }
                }
            }


        }


        runInScope {
            _dashboardUi.update { it.copy(homeScreenMeal = it.homeScreenMeal.copy(mealPlans = UiState.Loading(),
                planDay = "${dateUtil.getToday()}'s meal plan")) }
            launch {
                when(val routines =routineRepository.getUserRoutines()){
                    is RepoState.Success ->{
                        var completed = 0
                        var total = 0
                        routines.data.data.forEach {
                            it.exercises.forEach { ex ->
                                if (ex.completed){
                                    completed += 1
                                }
                                total += 1
                            }

                        }
                        Log.d("_dashboardUi", "$completed")
                        _dashboardUi.update { it.copy(homeScreenMeal
                        = it.homeScreenMeal.copy(completedTotal =  Triple("$completed", "$total",
                            completed* (1f / total)))) }
                    }
                    is RepoState.Error ->{
                        _dashboardUi.update { it.copy(homeScreenMeal
                        = it.homeScreenMeal.copy(completedTotal =  Triple("_", "_",
                            0f))) }
                    }
                }
            }
            mealPlanRepository.getMealPlanForUser().let { mealPlanState ->
                when(mealPlanState){
                    is RepoState.Success ->{
                        _dashboardUi.update { it.copy(homeScreenMeal =
                        it.homeScreenMeal.copy(mealPlans = UiState.Success(
                            mealPlanState.data.data.filter { plan ->
                                dateUtil.getToday() == dateUtil.getServerDay(plan.mealDateTime)
                            }.let { p ->
                                p.subList(0, p.size.coerceAtMost(4))
                            }.map {  mealPlan ->
                                mealPlan.copy(mealDateTime = dateUtil.getDayMonth(mealPlan.mealDateTime))
                            }
                        ))) }
                    }
                    is RepoState.Error ->{
                        _dashboardUi.update { it.copy(homeScreenMeal
                        = it.homeScreenMeal.copy(mealPlans = UiState.Failure(mealPlanState.errorMsg))) }
                    }
                }
            }
        }

        _dashboardUi.update {
            it.copy(dashboardMenus = listOf(
                DashboardMenu(title = Tokens.mealPlan,
                    bgColor = 0xFF5C6BC0.toInt(),
                    clipArt = R.drawable.meal_plan_ic)
            ), greeting = greeting.getGreeting(),
                pageTitles = listOf(Tokens.dashboard, Tokens.gymRoutine, Tokens.settings)
            )
        }

    }


    fun setCurrentPage(){
        showAddRoutine(savedStateHandle.get<Int>(CUR_PAGE) ?: 0)
    }

    fun showAddRoutine(page: Int){
        _dashboardUi.update { it.copy(showAddRoutine = page == DashboardPages.GYM_ROUTINE) }
        savedStateHandle[CUR_PAGE] = page
    }


}