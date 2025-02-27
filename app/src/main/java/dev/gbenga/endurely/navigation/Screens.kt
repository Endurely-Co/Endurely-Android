package dev.gbenga.endurely.navigation

import kotlinx.serialization.Serializable

interface Screens
@Serializable object Welcome : Screens
@Serializable object Login : Screens
@Serializable object SignUp : Screens
@Serializable object Dashboard: Screens