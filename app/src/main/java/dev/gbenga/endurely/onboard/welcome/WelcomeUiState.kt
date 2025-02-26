package dev.gbenga.endurely.onboard.welcome

data class WelcomeUiState (
    val welcomeContent: List<WelcomeContent> = emptyList(),
    val backgroundColor : Int =-1
)