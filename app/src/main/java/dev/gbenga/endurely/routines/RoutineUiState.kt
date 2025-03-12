package dev.gbenga.endurely.routines

import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.routines.data.RoutineData
import dev.gbenga.endurely.routines.data.RoutineResponse

data class RoutineUiState(val routines: UiState<List<RoutineData>> = UiState.Idle(),
                          val isDarkMode: Boolean? = null,
    val days: List<DayOfWeek> = emptyList(),
)

data class DayOfWeek(val name: String, val selected: Boolean=false,
                     val enabled: Boolean=true, val selectedIndex: Int =-1)