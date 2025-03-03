package dev.gbenga.endurely.dashboard

data class SettingsUiState(val settingsItem: List<SettingsItem> = emptyList(),
                           val isDarkTheme: Boolean? = false)