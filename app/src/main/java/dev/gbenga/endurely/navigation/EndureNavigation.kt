package dev.gbenga.endurely.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun rememberEndureNavigation(): EndureNavigation{
    return rememberNavController().let {
        remember { EndureNavigation(it) }
    }
}


class EndureNavigation(val navHostController:  NavHostController) {

    fun gotoWelcome(){
        navHostController.navigate(Welcome){
            popUpTo(navHostController.graph.id){
                inclusive = true
            }
        }
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

    fun gotoSignUp() = navHostController.navigate(SignUp)

    fun gotoDashboard() = pop { navHostController.navigate(Dashboard) }

    fun gotoAddNewRoutine() = navHostController.navigate(AddNewRoutine)
}

// r@T419