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
        pop()
        navHostController.navigate(Welcome)
    }

    fun pop() = navHostController.popBackStack()

    fun navUp() = navHostController.navigateUp()

    fun gotoRoutineDetails(routineId: String, pageTitle: String) = navHostController.navigate(RoutineDetail(routineId = routineId, pageTitle))

    fun gotoLogin(onTop: Boolean =false){
        if (onTop){
            navHostController.popBackStack()
        }
        navHostController.navigate(Login)
    }

    private fun pop(after: () -> Unit){
        pop()
        after.invoke()
    }

    fun gotoSignUp() = navHostController.navigate(SignUp)

    fun gotoDashboard() = pop { navHostController.navigate(Dashboard) }

}