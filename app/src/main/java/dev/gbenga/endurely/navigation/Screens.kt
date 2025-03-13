package dev.gbenga.endurely.navigation

import dev.gbenga.endurely.routines.data.RoutineData
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
@Serializable object MealPlan: Screens
@Serializable data class EditRoutine(val data: String): Screens
@Serializable data class MealPlanDetails(val planId: String): Screens