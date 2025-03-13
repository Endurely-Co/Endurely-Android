package dev.gbenga.endurely.routines

import dev.gbenga.endurely.core.Tokens
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.routines.data.AddRoutineResponse
import dev.gbenga.endurely.routines.data.NewExerciseName

data class AddNewRoutineState(val submitResult: UiState<AddRoutineResponse> = UiState.Idle(),

                              val addedNewRoutine: UiState<String> = UiState.Idle(),
                              val selectedExercises: ArrayList<NewExerciseName> = arrayListOf(),
                              override val enableSubmit: Boolean = false,
                              override val actionButton: String = Tokens.save
): RoutineCommonState


interface RoutineCommonState{
    val enableSubmit: Boolean
    val actionButton: String
}