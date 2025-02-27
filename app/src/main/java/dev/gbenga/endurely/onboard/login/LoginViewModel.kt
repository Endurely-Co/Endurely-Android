package dev.gbenga.endurely.onboard.login

import androidx.lifecycle.SavedStateHandle
import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.core.Tokens
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.core.isValidEmail
import dev.gbenga.endurely.extensions.hasNChars
import dev.gbenga.endurely.onboard.OnboardRepository
import dev.gbenga.endurely.onboard.data.RepoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(savedStateHandle: SavedStateHandle,
                     private val onboardRepository: OnboardRepository) : EndureNavViewModel(savedStateHandle) {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()



    fun login(username: String, password: String){
        if(username.hasNChars(3) && password.hasNChars(6)){
            _loginUiState.update { it.copy(loginUi = UiState.Loading()) }
            runInScope {
                when(val result = onboardRepository.logIn(username, password)){
                    is RepoState.Success ->{
                        _loginUiState.update { it.copy(loginUi = UiState.Success(result.data)) }
                    }
                    is RepoState.Error ->{
                        _loginUiState.update { it.copy(loginUi = UiState.Failure(result.errorMsg)) }
                    }
                }
            }
        }else{
            _loginUiState.update { it.copy(loginUi = UiState.Failure("Username or Password has an incorrect length")) }
        }
        // _enableLogin

    }

    override fun clearState() {
        _loginUiState.update { it.copy(loginUi = UiState.Idle()) }
    }
}