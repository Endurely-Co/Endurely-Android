package dev.gbenga.endurely.routines

import android.util.Log
import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.onboard.data.RepoState
import dev.gbenga.endurely.routines.data.EditRoutineResponse
import dev.gbenga.endurely.routines.data.RoutineResponse
import dev.gbenga.endurely.routines.data.UserExercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RoutineDetailViewModel(private val routineRepository: RoutineRepository) :EndureNavViewModel() {

    private val _routineDetail = MutableStateFlow(RoutineDetailUiState())
    val routineDetail = _routineDetail.asStateFlow()

    private val _exerciseDetailsUi = MutableStateFlow(ExerciseDetailsState())
    val exerciseDetailsUi = _exerciseDetailsUi.asStateFlow()

    var routine : RoutineResponse? =null

    fun showDetails(userExercise: UserExercise){
        _exerciseDetailsUi.update { it.copy(title =userExercise.exercise.name,
            description=userExercise.exercise.description, duration = userExercise.duration, show = true) }
    }

    fun markComplete(name: String){
        _exerciseDetailsUi.update { it.copy(
            markComplete = UiState.Loading()) }
       runInScope {
           routine?.let { routine->
               val exercises = (_routineDetail.value.userExercises as UiState.Success).data.map { ue ->
                   if (ue.exercise.name == name) {
                       ue.copy(completed = true)
                   } else ue
               }
               routineRepository.editRoutine(routine.data.first()
                   .toRequest(exercises)).also { result ->
                       when(result){
                           is RepoState.Success ->{
                               _exerciseDetailsUi.update { it.copy(
                                   markComplete = UiState.Success("Exercise was completed")) }

                               _routineDetail.update { it.copy(userExercises
                               = UiState.Success(exercises)) }
                           }
                           is RepoState.Error ->{
                               Log.d("RepoState", "noew: ${result.errorMsg}")
                               _exerciseDetailsUi.update { it.copy(markComplete = UiState.Failure(result.errorMsg)) }
                           }
                       }
               }
           }
           hideDetails()
       }
    }

    fun hideDetails(){
        _exerciseDetailsUi.update {it.copy(show = false)}
    }

    fun removeRoutine(routineId: String){
        _routineDetail.update { it.copy(
            deleteRoutine = UiState.Loading()) }
        runInScope {
            when(val deleted =routineRepository.deleteRoutine(routineId)){
                is RepoState.Success ->{
                    _routineDetail.update { it.copy(
                        deleteRoutine = UiState.Success(deleted.data.data)) }
                }
                is RepoState.Error ->{
                    _routineDetail.update { it.copy(
                        deleteRoutine = UiState.Failure(deleted.errorMsg)) }
                }

            }
        }
    }

    fun getRoutineDetails(routineId: String){
        runInScope {
            _routineDetail.update { it.copy(userExercises = UiState.Loading()) }
            when(val routine = routineRepository.getUserRoutineById(routineId)){
                is RepoState.Success ->{
                    this.routine = routine.data
                    _routineDetail.update { it.copy(userExercises
                    = UiState.Success(routine.data.data.first().exercises)) }
                }
                is RepoState.Error ->{
                    _routineDetail.update { it.copy(userExercises = UiState.Failure(routine.errorMsg)) }
                }
            }
        }
    }

}