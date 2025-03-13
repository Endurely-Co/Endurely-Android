package dev.gbenga.endurely.routines

import android.util.Log
import dev.gbenga.endurely.core.DateTimeUtils
import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.onboard.data.RepoState
import dev.gbenga.endurely.routines.data.EditRoutineRequest
import dev.gbenga.endurely.routines.data.NewExercise
import dev.gbenga.endurely.routines.data.NewExerciseName
import dev.gbenga.endurely.routines.data.RoutineData
import dev.gbenga.endurely.routines.data.RoutineExercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EditRoutineViewModel(
    private val dateTimeUtils: DateTimeUtils,
    private val routineRepository: RoutineRepository) : EndureNavViewModel() {

    private val _editRoutineState = MutableStateFlow(EditRoutineState())
    val editRoutineState = _editRoutineState.asStateFlow()
    private var routineData = MutableStateFlow(Triple("", 0L, Pair(0,0))) // = Triple("", "", "")

    fun preloadEdit(routineData: RoutineData){
        val exercises = ArrayList(routineData.exercises.map {  exercise ->
            NewExerciseName(name = exercise.exercise.name, NewExercise(dateTimeUtils
                .serverDuration(exercise.duration), exercise.id), )
        })
        _editRoutineState.update { it.copy(oldEditRoutine = routineData,
            selectedExercises = exercises) }
    }

    fun setTime(hour: Int,minute: Int,){
        routineData.update { it.copy(third = Pair(hour, minute)) }
    }

    fun setDate(date: Long){
        routineData.update { it.copy(second = date) }
    }

    fun validate(routineName: String, startDate: String, time: String){
        Log.d("validate_", "$routineName $startDate $time")
        _editRoutineState.update { it.copy(
            enableSubmit = routineName.length > 2
                    && _editRoutineState.value.selectedExercises.isNotEmpty()
                    && startDate.isNotBlank()
                    && time.isNotBlank()) }
    }

    fun setRoutineName(routineNameValue: String){
        routineData.update { it.copy(first = routineNameValue) }
    }


    fun addExercise(name: String, duration: String, id: Long){
        val updatedList = _editRoutineState.value.selectedExercises.apply {
            add(
                NewExerciseName(name, NewExercise(dateTimeUtils
                .serverDuration(duration), id)
                )
            )
        }

        _editRoutineState.update {
            it.copy(selectedExercises = updatedList) }

    }


    fun removeExercise(id: Long){
        val updatedList = ArrayList(_editRoutineState.value.selectedExercises)
        updatedList.apply {
            removeIf { it.exercise.id == id }
        }

        _editRoutineState.update {
            it.copy(selectedExercises = updatedList) }
    }

    fun editRoutine(newRoutineName: String,){
        _editRoutineState.update { it.copy(updateRoutine = UiState.Loading()) }

        runInScope {
           val selectedExIds = _editRoutineState.value.selectedExercises
               .map { it.exercise.id }
           when(val editComplete = routineRepository.editRoutine(
               EditRoutineRequest(
                   routineName = newRoutineName,
                   routineId = _editRoutineState.value.oldEditRoutine.routineId,
                   exercises = _editRoutineState.value.oldEditRoutine.exercises.map { userExercise ->
                       RoutineExercise(userExerciseId = userExercise.id,
                           duration = userExercise.duration,
                           id = userExercise.exercise.id,
                           completed = userExercise.completed)
                   }//.filter { ex -> selectedExIds.contains(ex.id) }
               ))){
               is RepoState.Success ->{
                   _editRoutineState.update { it.copy(updateRoutine = UiState.Success("Routine was updated successfully")) }
               }
               is RepoState.Error ->{
                   _editRoutineState.update { it.copy(updateRoutine = UiState.Failure(editComplete.errorMsg)) }
               }
           }
       }
    }
}