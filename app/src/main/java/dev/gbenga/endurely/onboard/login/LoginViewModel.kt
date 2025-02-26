package dev.gbenga.endurely.onboard.login

import androidx.lifecycle.SavedStateHandle
import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.core.Tokens
import dev.gbenga.endurely.onboard.OnboardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(savedStateHandle: SavedStateHandle,
                     repository: OnboardRepository) : EndureNavViewModel(savedStateHandle) {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    init {
        _loginUiState.update { it.copy(buttonText = Tokens.logIn,
            loginTextFields = listOf(EmailTextField(),
                PasswordTextField()
            )
        ) }
    }
}