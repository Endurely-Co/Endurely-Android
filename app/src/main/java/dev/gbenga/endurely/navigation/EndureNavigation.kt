package dev.gbenga.endurely.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import dev.gbenga.endurely.core.Tokens
import dev.gbenga.endurely.routines.data.RoutineData


@Composable
fun rememberEndureNavigation(): EndureNavigation{
    return rememberNavController().let {
        remember { EndureNavigation(it) }
    }
}


class EndureNavigation(val navHostController:  NavHostController, private val gson: Gson = Gson()) {

    fun gotoWelcome(){
        navHostController.navigate(Welcome){
            popUpTo(navHostController.graph.id){
                inclusive = true
            }
        }
    }

    fun gotoMealPlanDetails(planId: String) = navHostController.navigate(MealPlanDetails(planId))

    fun gotoMealPlan(){
        navHostController.navigate(MealPlan)
    }

    private val savedStateHandle: SavedStateHandle? = navHostController
        .previousBackStackEntry
        ?.savedStateHandle

    fun popAndRefresh(){
        navHostController.navigate(Dashboard)
    }


    fun pop() = navHostController.popBackStack()

    fun navUp() = navHostController.navigateUp()

    fun gotoRoutineDetails(routineId: String, pageTitle: String) = navHostController.navigate(RoutineDetail(routineId = routineId, pageTitle))

    fun gotoLogin(onTop: Boolean =false){
        //navHostController.popBackStack<T>(true)=
        navHostController.navigate(Login){
            if (onTop) {
                popUpTo(navHostController.graph.id){
                    inclusive = true
                }
            }
            //launchSingleTop = true
        }
    }

    private fun pop(after: () -> Unit){
        pop()
        after.invoke()
    }

    fun gotoEditRoutine(routineData: RoutineData){
        navHostController.navigate(EditRoutine(gson.toJson(routineData)))
    }

    fun gotoSignUp() = navHostController.navigate(SignUp)

    fun gotoDashboard() = pop { navHostController.navigate(Dashboard) }

    fun gotoAddNewRoutine() = navHostController.navigate(AddNewRoutine)
}

// r@T419

val json = Gson()
inline fun <reified T> String.toType(): T{
    return json.fromJson(this, T::class.java)
}