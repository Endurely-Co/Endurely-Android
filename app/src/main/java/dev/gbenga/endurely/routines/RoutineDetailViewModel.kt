package dev.gbenga.endurely.routines

import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
            description=userExercise.exercise.description,
            duration = userExercise.duration, show = true,
            isNotComplete = !userExercise.completed) }
    }

    private fun updateStatusCount(complete: Int, inProgress: Int){
        _routineDetail.update { it.copy(statusCount = Pair(complete, inProgress)) }
    }

    fun markComplete(name: String){
        _exerciseDetailsUi.update { it.copy(
            markComplete = UiState.Loading()) }
       runInScope {
           routine?.let { routine->
               val exercises = (_routineDetail.value.userExercises as UiState.Success).data.map { userExercise ->
                   if (userExercise.exercise.name == name) {
                       userExercise.copy(completed = true)
                   } else userExercise
               }
               routineRepository.editRoutine(routine.data.first()
                   .toRequest(exercises)).also { result ->
                       when(result){
                           is RepoState.Success ->{
                               var completedCount = 0
                               var inProgressCount = 0
                               exercises.forEach {
                                   if (it.completed){
                                       completedCount += 1
                                   }else inProgressCount += 1
                               }
                               updateStatusCount(completedCount, inProgressCount)
                               _exerciseDetailsUi.update { it.copy(
                                   markComplete = UiState.Success("Exercise was completed"), isNotComplete = false) }

                               _routineDetail.update { it.copy(userExercises
                               = UiState.Success(exercises)) }
                           }
                           is RepoState.Error ->{
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

                    var completedCount = 0
                    var inProgressCount = 0

                    routine.data.data.first().exercises.forEach {
                        if (it.completed){
                            completedCount += 1
                        }else{
                            inProgressCount += 1
                        }
                    }

                    this@RoutineDetailViewModel.routine = routine.data
                    _routineDetail.update { it.copy(userExercises
                    = UiState.Success(routine.data.data.first().exercises),
                        statusCount = Pair(completedCount, inProgressCount),
                        routineDataForEdit = routine.data.data.first()) }
                }
                is RepoState.Error ->{
                    _routineDetail.update { it.copy(userExercises = UiState.Failure(routine.errorMsg)) }
                }
            }
        }
    }

}