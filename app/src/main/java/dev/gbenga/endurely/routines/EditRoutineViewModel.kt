package dev.gbenga.endurely.routines

import dev.gbenga.endurely.core.DateTimeUtils
import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.routines.data.NewExercise
import dev.gbenga.endurely.routines.data.NewExerciseName
import dev.gbenga.endurely.routines.data.RoutineData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EditRoutineViewModel(
    private val dateTimeUtils: DateTimeUtils,) : EndureNavViewModel() {

    private val _editRoutineState = MutableStateFlow(EditRoutineState())
    val editRoutineState = _editRoutineState.asStateFlow()
    private var routineData = MutableStateFlow(Triple("", 0L, Pair(0,0))) // = Triple("", "", "")

    fun preloadEdit(routineData: RoutineData){
        _editRoutineState.update { it.copy(oldEditRoutine = UiState.Success(routineData)) }
    }

    fun setTime(hour: Int,minute: Int,){
        routineData.update { it.copy(third = Pair(hour, minute)) }
    }

    fun setDate(date: Long){
        routineData.update { it.copy(second = date) }
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
}