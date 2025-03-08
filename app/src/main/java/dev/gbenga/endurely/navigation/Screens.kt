package dev.gbenga.endurely.navigation

import kotlinx.serialization.Serializable

interface Screens
@Serializable object Welcome : Screens
@Serializable object Login : Screens
@Serializable object SignUp : Screens
@Serializable object Dashboard: Screens
@Serializable object Settings: Screens
@Serializable object GymRoutine: Screens
@Serializable data class RoutineDetail(val routineId: String, val pageTitle: String) : Screens
@Serializable object AddNewRoutine : Screens