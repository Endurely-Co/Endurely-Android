package dev.gbenga.endurely.onboard.login

import dev.gbenga.endurely.core.UiTextField

data class LoginUiState(val buttonText: String="",
    val loginTextFields: List<UiTextField> = emptyList()
)

data class EmailTextField(override val label: String= "Email", ): UiTextField()
data class PasswordTextField(override val label: String ="Password",
                             override var hideContent: Boolean = true): UiTextField()