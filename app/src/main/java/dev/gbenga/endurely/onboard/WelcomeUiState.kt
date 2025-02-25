package dev.gbenga.endurely.onboard

data class WelcomeUiState (
    val welcomeContent: List<WelcomeContent> = emptyList(),
    val backgroundColor : Int =-1
)