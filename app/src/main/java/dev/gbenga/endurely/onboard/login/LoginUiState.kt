package dev.gbenga.endurely.onboard.login

import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.core.UiTextField
import dev.gbenga.endurely.onboard.data.LoginResponse

data class LoginUiState(val buttonText: String="",
    val loginTextFields: List<UiTextField> = emptyList(),
    val loginUi : UiState<LoginResponse> = UiState.Idle()
)

data class EmailTextField(override val label: String= "Email", ): UiTextField()
data class PasswordTextField(override val label: String ="Password",
                             override var hideContent: Boolean = true): UiTextField()