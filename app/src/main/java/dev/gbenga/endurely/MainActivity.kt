package dev.gbenga.endurely

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import dev.gbenga.endurely.dashboard.DashboardScreen
import dev.gbenga.endurely.navigation.AddNewRoutine
import dev.gbenga.endurely.navigation.Dashboard
import dev.gbenga.endurely.navigation.Login
import dev.gbenga.endurely.navigation.RoutineDetail
import dev.gbenga.endurely.navigation.SignUp
import dev.gbenga.endurely.navigation.Welcome
import dev.gbenga.endurely.navigation.rememberEndureNavigation
import dev.gbenga.endurely.onboard.login.LoginScreen
import dev.gbenga.endurely.onboard.signup.SigUpScreen
import dev.gbenga.endurely.onboard.welcome.WelcomeScreen
import dev.gbenga.endurely.routines.AddNewRoutineScreen
import dev.gbenga.endurely.routines.RoutineDetailScreen
import dev.gbenga.endurely.ui.theme.EndurelyTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        setContent {
            val navHost = rememberEndureNavigation()
            val viewModel : MainActivityViewModel = koinViewModel()
            val maiUiState by viewModel.maiUiState.collectAsStateWithLifecycle()
            val isSystemInDarkTheme = isSystemInDarkTheme()
            val isDarkMode by remember { derivedStateOf { maiUiState.isDarkMode ?: isSystemInDarkTheme } }

            LaunchedEffect(maiUiState.isDarkMode) {
                WindowCompat.getInsetsController(window, window.decorView)
                    .isAppearanceLightStatusBars = !(maiUiState.isDarkMode ?: isSystemInDarkTheme)
            }

            EndurelyTheme(
                darkTheme = isDarkMode
            ) {
                NavHost(navController = navHost.navHostController,
                    startDestination = maiUiState.startDestination){
                    composable<Welcome> {
                        WelcomeScreen(navHost)
                    }
                    composable<Login> {
                        LoginScreen(navHost, isDarkMode = maiUiState.isDarkMode ?: isSystemInDarkTheme())
                    }

                    composable<SignUp> { SigUpScreen(navHost) }

                    composable<Dashboard> {

                        DashboardScreen(nav = navHost, isDarkTheme = isDarkMode)
                    }

                    composable<AddNewRoutine>(){
                        AddNewRoutineScreen(navHost, isDarkMode)
                    }

                    composable<RoutineDetail>() {
                        val args = it.toRoute<RoutineDetail>()

                        RoutineDetailScreen(navHost, routineId = args.routineId,
                            title = args.pageTitle) }
                }
            }
        }
    }
}
