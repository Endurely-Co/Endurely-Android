package dev.gbenga.endurely.meal

import androidx.lifecycle.viewModelScope
import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.meal.data.MessageSharedPref
import dev.gbenga.endurely.onboard.data.RepoState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MealPlanDetailsViewModel(private val mealDetailRepository: MealPlanRepository,
    private val messageSharedPref: MessageSharedPref) : EndureNavViewModel() {

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

    fun deleteMealPlan(planId: String){
        // deleteDeleteMealPlan
        _mealPlanDetailsState.update { it.copy(
            delete = UiState.Loading()) }
        runInScope {
            when(val mealPlanDelete = mealDetailRepository.deleteDeleteMealPlan(planId)){
                is RepoState.Success ->{
                    messageSharedPref.setMessage(mealPlanDelete.data.data)
                    _mealPlanDetailsState.update { it.copy(
                        delete = UiState.Success(mealPlanDelete.data.data)) }
                }
                is RepoState.Error ->{
                    _mealPlanDetailsState.update { it.copy(delete = UiState.Failure(mealPlanDelete.errorMsg)) }

                }
            }
            clearState();
        }
    }

    override fun clearState() {
        viewModelScope.launch {
            launch(Dispatchers.IO) {
                delay(500)
                _mealPlanDetailsState.update { it.copy(
                   // details = UiState.Idle(),
                    delete = UiState.Idle()
                ) }
            }
        }
    }

}