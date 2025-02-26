package dev.gbenga.endurely.onboard.signup

import dev.gbenga.endurely.core.Tokens
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.onboard.data.SignUpResponse

data class SignUpUiState(
    val signUp : UiState<SignUpResponse> = UiState.Idle(),
    val buttonText : String = Tokens.signUp
)