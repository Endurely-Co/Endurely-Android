package dev.gbenga.endurely.routines

import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.routines.data.UserExercise

data class RoutineDetailUiState(
    val userExercises: UiState<List<UserExercise>> = UiState.Idle()
)