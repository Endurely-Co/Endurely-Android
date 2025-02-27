package dev.gbenga.endurely.dashboard

import dev.gbenga.endurely.core.UiState

data class DashboardUiState (
    val dashboardMenus : List<DashboardMenu> = listOf(),
    val greeting : String = "",
    val fullName: UiState<String> = UiState.Idle()
)