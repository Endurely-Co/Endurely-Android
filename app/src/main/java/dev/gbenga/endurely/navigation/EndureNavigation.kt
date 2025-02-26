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
        navHostController.navigate(Welcome)
    }

    fun gotoLogin() = navHostController.navigate(Login)

    fun gotoSignUp() = navHostController.navigate(SignUp)

}