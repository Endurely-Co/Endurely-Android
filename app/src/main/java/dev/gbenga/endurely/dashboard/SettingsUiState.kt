package dev.gbenga.endurely.dashboard

import dev.gbenga.endurely.core.UiState

data class SettingsUiState(val settingsItem: List<SettingsItem> = emptyList(),
                           val isDarkTheme: Boolean? = false,
    val signOut: UiState<String> = UiState.Idle())