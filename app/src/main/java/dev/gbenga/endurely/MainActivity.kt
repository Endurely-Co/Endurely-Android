package dev.gbenga.endurely

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {

        //val splashscreen = installSplashScreen()
        // var keepSplashScreen = true

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

       // splashscreen.setKeepOnScreenCondition { keepSplashScreen }
//        lifecycleScope.launch {
//            delay(5000)
//            keepSplashScreen = false
//        }

        setContent {
            val navHost = rememberEndureNavigation()
            val viewModel : MainActivityViewModel = koinViewModel()
            val maiUiState by viewModel.maiUiState.collectAsStateWithLifecycle()

            EndurelyTheme {
                NavHost(navController = navHost.navHostController,
                    startDestination = maiUiState.startDestination){
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
