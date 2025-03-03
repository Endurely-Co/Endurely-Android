package dev.gbenga.endurely.routines

import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.dashboard.SettingsRepository
import dev.gbenga.endurely.onboard.data.RepoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

class RoutinesViewModel(private val routineRepository : RoutineRepository,
                        private val settingsRepository: SettingsRepository) : EndureNavViewModel(){

    private val _routinesUi = MutableStateFlow(RoutineUiState())
    val routinesUi = _routinesUi.asStateFlow()

    init {
        getRoutinesByUserId()
       runInScope {
           settingsRepository.theme().collect{ isDarkMode ->
               _routinesUi.update { it.copy(isDarkMode = isDarkMode) }
           }
       }
    }

    fun getRoutinesByUserId(){
        _routinesUi.update { it.copy(routines = UiState.Loading()) }
        runInScope {
            _routinesUi.update { it.copy(routines = repoToVMState {
                routineRepository.getUserRoutines() })
            }
        }
    }

    override fun clearState() {
        _routinesUi.update { RoutineUiState() }
    }
}
