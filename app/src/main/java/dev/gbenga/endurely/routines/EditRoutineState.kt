package dev.gbenga.endurely.routines

import dev.gbenga.endurely.core.Tokens
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.routines.data.NewExerciseName
import dev.gbenga.endurely.routines.data.RoutineData
import dev.gbenga.endurely.routines.data.RoutineResponse

data class EditRoutineState (val oldEditRoutine: RoutineData = RoutineData(),
                             override val enableSubmit: Boolean = false,
                             val updateRoutine: UiState<String> = UiState.Idle(),
                             val selectedExercises: ArrayList<NewExerciseName> = arrayListOf(),
                             override val actionButton: String = Tokens.edit,

                             ): RoutineCommonState