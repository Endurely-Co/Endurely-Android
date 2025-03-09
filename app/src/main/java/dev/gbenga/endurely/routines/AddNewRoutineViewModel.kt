package dev.gbenga.endurely.routines

import android.util.Log
import dev.gbenga.endurely.core.DateTimeUtils
import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.onboard.data.RepoState
import dev.gbenga.endurely.routines.data.AddRoutineRequest
import dev.gbenga.endurely.routines.data.NewExercise
import dev.gbenga.endurely.routines.data.NewExerciseName
import dev.gbenga.endurely.routines.data.duration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class AddNewRoutineViewModel(private val routineRepository: RoutineRepository,
                             private val dateTimeUtils: DateTimeUtils,
                             private val timeDateFormatter: DateTimeUtils) : EndureNavViewModel() {

    private val _addRoutineUi = MutableStateFlow(AddNewRoutineState())
    val addRoutineUi = _addRoutineUi.asStateFlow()
    private var updatedList  = ArrayList<NewExerciseName>()

//    private var routineNameValue: String = ""
//    private var dateValue: String = ""
//    private var timeValue: String = ""
    private var routineData = MutableStateFlow(Triple("", 0L, Pair(0,0))) // = Triple("", "", "")

    init {
        runInScope {
            routineData.collect{ payload ->
                _addRoutineUi.update { it.copy(enableSubmit = !payload.third.toList().contains(0)
                        && payload.first.isNotBlank() && payload.second != 0L) }
            }
        }
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
        val updatedList = _addRoutineUi.value.selectedExercises.apply {
            add(NewExerciseName(name, NewExercise(dateTimeUtils
                .serverDuration(duration), id)))
        }

        _addRoutineUi.update {
            it.copy(selectedExercises = updatedList) }

    }


    fun removeExercise(id: Long){
        val updatedList = ArrayList(_addRoutineUi.value.selectedExercises)
        updatedList.apply {
            removeIf { it.exercise.id == id }
        }

        _addRoutineUi.update {
            it.copy(selectedExercises = updatedList) }
    }

    override fun clearState() {
        _addRoutineUi.update { AddNewRoutineState() }
    }

    fun submitRoutine() {
        _addRoutineUi.update { it.copy(addedNewRoutine = UiState.Loading()) }
        runInScope {
            routineData.value.let{ payload ->
                when(val result =routineRepository.addRoutine(AddRoutineRequest(
                    exercises = _addRoutineUi.value.selectedExercises.map { it.exercise },
                    routineName = payload.first,
                    startDate = dateTimeUtils.getServerTime(
                        payload.third.first, payload.third.second,
                        payload.second).also {
                        Log.d("ViewModel#1", it)
                    },
                ))){
                    is RepoState.Success ->{
                        _addRoutineUi.update { it.copy(addedNewRoutine = UiState.Success("New Routine was added successfully.")) }
                    }
                    is RepoState.Error ->{
                        _addRoutineUi.update { it.copy(addedNewRoutine = UiState.Failure(result.errorMsg)) }
                    }
                }
            }



        }

    }

}