package dev.gbenga.endurely.meal

import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.onboard.data.RepoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MealPlanDetailsViewModel(private val mealDetailRepository: MealPlanRepository) : EndureNavViewModel() {

    private val _mealPlanDetailsState = MutableStateFlow(MealPlanDetailsState())
    val mealPlanDetailsState = _mealPlanDetailsState.asStateFlow()

    fun getMealPlanForUserById(planId: String){
        _mealPlanDetailsState.update { it.copy(
            details = UiState.Loading()) }
        runInScope {
            when(val mealPlanDetails = mealDetailRepository.getMealPlanForUserById(planId)){
                is RepoState.Success ->{
                    _mealPlanDetailsState.update { it.copy(
                        details = UiState.Success(mealPlanDetails.data.data.first())) }
                }
                is RepoState.Error ->{
                    _mealPlanDetailsState.update { it.copy(details = UiState.Failure(mealPlanDetails.errorMsg)) }
                }
            }
        }
    }

}