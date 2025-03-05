package dev.gbenga.endurely.routines

import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.onboard.data.RepoState
import dev.gbenga.endurely.routines.data.UserExercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RoutineDetailViewModel(private val routineRepository: RoutineRepository) :EndureNavViewModel() {

    private val _routineDetail = MutableStateFlow(RoutineDetailUiState())
    val routineDetail = _routineDetail.asStateFlow()

    private val _exerciseDetailsUi = MutableStateFlow(ExerciseDetailsState())
    val exerciseDetailsUi = _exerciseDetailsUi.asStateFlow()

    fun showDetails(userExercise: UserExercise){
        _exerciseDetailsUi.update { it.copy(title =userExercise.exercise.name,
            description=userExercise.exercise.description, duration = userExercise.duration, show = true) }
    }

    fun hideDetails(){
        _exerciseDetailsUi.update {it.copy(show = false)}
    }

    fun getRoutineDetails(routineId: String){
        runInScope {
            _routineDetail.update { it.copy(userExercises = UiState.Loading()) }
            when(val routine = routineRepository.getUserRoutineById(routineId)){
                is RepoState.Success ->{
                    _routineDetail.update { it.copy(userExercises = UiState.Success(routine.data.data.first().userExercises)) }
                }
                is RepoState.Error ->{
                    _routineDetail.update { it.copy(userExercises = UiState.Failure(routine.errorMsg)) }
                }
            }
        }
    }

}