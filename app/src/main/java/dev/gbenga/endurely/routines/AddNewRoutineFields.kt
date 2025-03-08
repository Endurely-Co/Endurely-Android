package dev.gbenga.endurely.routines

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import dev.gbenga.endurely.routines.data.NewExercise


data class AddNewRoutineFields(val routineName: MutableState<String> = mutableStateOf(""),
                               val date: MutableState<String> = mutableStateOf("Date"),
                               val time: MutableState<String> = mutableStateOf("Time"),
                               val completed: MutableState<Boolean> = mutableStateOf(false),
                               //val exercises : ArrayList<NewExercise> = arrayListOf()
)