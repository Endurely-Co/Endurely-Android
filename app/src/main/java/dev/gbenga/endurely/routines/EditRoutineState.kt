package dev.gbenga.endurely.routines

import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.routines.data.NewExerciseName
import dev.gbenga.endurely.routines.data.RoutineData
import dev.gbenga.endurely.routines.data.RoutineResponse

data class EditRoutineState (val oldEditRoutine: UiState<RoutineData> = UiState.Idle(),
                             override val enableSubmit: Boolean = false,
                             val addedNewRoutine: UiState<String> = UiState.Idle(),
                             val selectedExercises: ArrayList<NewExerciseName> = arrayListOf()): RoutineCommonState