package dev.gbenga.endurely.dashboard

import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.core.Tokens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel(private val settingsRepository: SettingsRepository) : EndureNavViewModel() {

    private val _settingsUi = MutableStateFlow(SettingsUiState())
    val settingsUi = _settingsUi.asStateFlow()

    init {
        runInScope {
            settingsRepository.theme().collect{ isDark ->
                _settingsUi.update {
                    it.copy(isDarkTheme = isDark)
                }
            }
        }
        _settingsUi.update {
            it.copy(settingsItem = listOf(
                SettingsItem(Tokens.darkMode, Tokens.darkModeDescrip)
            ))
        }
    }

    fun toggleThemeMode(isDark: Boolean){

       runInScope {
           _settingsUi.update {
               it.copy(settingsItem = listOf(
                   if (isDark){
                       SettingsItem(Tokens.darkMode, Tokens.darkModeDescrip)
                   }else{
                       SettingsItem(Tokens.lightMode, Tokens.lightModeDescrip)
                   }
               ))
           }

           settingsRepository.changeTheme(isDark)

       }
    }

}