package dev.gbenga.endurely

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.gbenga.endurely.dashboard.DashboardScreen
import dev.gbenga.endurely.navigation.Dashboard
import dev.gbenga.endurely.navigation.Login
import dev.gbenga.endurely.navigation.SignUp
import dev.gbenga.endurely.navigation.Welcome
import dev.gbenga.endurely.navigation.rememberEndureNavigation
import dev.gbenga.endurely.onboard.login.LoginScreen
import dev.gbenga.endurely.onboard.signup.SigUpScreen
import dev.gbenga.endurely.onboard.welcome.WelcomeScreen
import dev.gbenga.endurely.ui.theme.EndurelyTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContent {
            val navHost = rememberEndureNavigation()
            EndurelyTheme {
                NavHost(navController = navHost.navHostController, startDestination = Welcome){
                    composable<Welcome> {
                        WelcomeScreen(navHost)
                    }
                    composable<Login> {
                        LoginScreen(navHost)
                    }

                    composable<SignUp> { SigUpScreen(navHost) }

                    composable<Dashboard> { DashboardScreen(navHost) }
                }
            }
        }
    }
}
