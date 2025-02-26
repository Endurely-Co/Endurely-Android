package dev.gbenga.endurely.onboard.signup

import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.extensions.hasNChars
import dev.gbenga.endurely.onboard.OnboardRepository
import dev.gbenga.endurely.onboard.data.RepoState
import dev.gbenga.endurely.onboard.data.SignUpRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel(private val onboardRepo: OnboardRepository) : EndureNavViewModel() {
    private val _signUpState = MutableStateFlow(SignUpUiState())
    val signUpState = _signUpState.asStateFlow()

    fun signUp(email: String, firstName: String,
               lastName: String, password: String, username: String){
        if (email.hasNChars(5) && lastName.hasNChars(2)
            && username.hasNChars(2)
            && lastName.hasNChars(2) && password.hasNChars(6)){
            _signUpState.update { it.copy(signUp = UiState.Loading()) }
            runInScope {
                when(val repoState = onboardRepo.signUp(SignUpRequest(email, firstName, lastName, password, username))){
                    is RepoState.Success ->{
                        _signUpState.update { it.copy(signUp = UiState.Success(repoState.data)) }
                    }
                    is RepoState.Error -> {
                        _signUpState.update { it.copy(signUp = UiState.Failure(repoState.errorMsg)) }
                    }
                }
            }
        }else{
            _signUpState.update { it.copy(signUp = UiState.Failure("Every field is compulsory. Check your input and try again.")) }
        }

    }

    override fun clearState() {
        _signUpState.update { it.copy(signUp = UiState.Idle()) }
    }
}