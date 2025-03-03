package dev.gbenga.endurely.routines

import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.routines.data.RoutineData
import dev.gbenga.endurely.routines.data.RoutineResponse

data class RoutineUiState(val routines: UiState<List<RoutineData>> = UiState.Idle(), val isDarkMode: Boolean? = null)