package dev.gbenga.endurely.routines

import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.onboard.data.RepoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RoutineDetailViewModel(private val routineRepository: RoutineRepository) :EndureNavViewModel() {

    private val _routineDetail = MutableStateFlow(RoutineDetailUiState())
    val routineDetail = _routineDetail.asStateFlow()

    fun getRoutineDetails(routineId: String){
        runInScope {
            _routineDetail.update { it.copy(userExercises = UiState.Loading()) }
            when(val routine = routineRepository.getUserRoutineById(routineId)){

                is RepoState.Success ->{
                    val routine = routine.data.data.first()
                    _routineDetail.update { it.copy(userExercises = UiState.Success(routine.userExercises)) }
                }
                is RepoState.Error ->{
                    _routineDetail.update { it.copy(userExercises = UiState.Failure(routine.errorMsg)) }
                }
            }
        }
    }

}