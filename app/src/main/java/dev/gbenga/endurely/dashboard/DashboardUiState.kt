package dev.gbenga.endurely.dashboard

import dev.gbenga.endurely.core.UiState

data class DashboardUiState (
    val dashboardMenus : List<DashboardMenu> = listOf(),
    val greeting : String = "",
    val pageTitles: List<String> = emptyList(),
    val fullName: UiState<String> = UiState.Idle(),
    val userInitial : String = "",
    val showAddRoutine: Boolean = false
)