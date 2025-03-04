package dev.gbenga.endurely.routines

import dev.gbenga.endurely.routines.data.UserExercise

data class RoutineDetailUiState(
    val userExercises: List<UserExercise> = emptyList()
)