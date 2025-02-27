package dev.gbenga.endurely

import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.navigation.Dashboard
import dev.gbenga.endurely.navigation.Login
import dev.gbenga.endurely.navigation.Welcome
import dev.gbenga.endurely.onboard.OnboardRepository
import dev.gbenga.endurely.onboard.data.RepoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainActivityViewModel(
    private val onboardRepository: OnboardRepository
): EndureNavViewModel() {


    private val _maiUiState = MutableStateFlow(MainActivityState())
    val maiUiState = _maiUiState.asStateFlow()


    init {
        runInScope {
            onboardRepository.checkLogin().let { login ->
                _maiUiState.update { it.copy(
                    startDestination = if(login is RepoState.Success){
                        Dashboard
                    } else{
                        Welcome
                    }
                ) }

            }
        }

    }
}