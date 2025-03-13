package dev.gbenga.endurely.routines

import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.routines.data.RoutineData
import dev.gbenga.endurely.routines.data.UserExercise

data class RoutineDetailUiState(
    val userExercises: UiState<List<UserExercise>> = UiState.Idle(),
    val deleteRoutine: UiState<String> = UiState.Idle(),
    val statusCount: Pair<Int, Int> = Pair(0, 0),
    val routineDataForEdit: RoutineData = RoutineData()
)