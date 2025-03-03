package dev.gbenga.endurely.dashboard

import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.core.Tokens
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.onboard.data.RepoState
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
                SettingsItem(Tokens.darkMode, Tokens.darkModeDescrip, buttonType = ButtonType.SWITCH),
                SettingsItem(Tokens.signOut, Tokens.signOutDescrip, buttonType = ButtonType.ACTION)
            ))
        }
    }

    fun signOut(){
        runInScope {
            when(settingsRepository.logout() ){
                is RepoState.Success ->{
                    _settingsUi.update { it.copy(signOut = UiState. Success("Successfully signed out"))}
                }
                is RepoState.Error ->{
                    _settingsUi.update { it.copy(signOut = UiState.Failure("Something went wrong. Failed to log out") )}
                }
            }
        }
    }

    fun toggleThemeMode(isDark: Boolean){

       runInScope {
           _settingsUi.update {
               it.copy(settingsItem = it.settingsItem.mapIndexed { index, item ->
                   if(index == 0){
                       item.copy(
                           title = if(isDark) Tokens.darkMode else Tokens.lightMode,
                           subTitle = if (isDark) Tokens.darkModeDescrip else Tokens.lightModeDescrip)
                   }else{
                       item
                   }
               })
           }

           settingsRepository.changeTheme(isDark)

       }
    }

}