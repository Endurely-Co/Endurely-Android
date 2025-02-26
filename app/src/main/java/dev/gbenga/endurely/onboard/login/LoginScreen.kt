package dev.gbenga.endurely.onboard.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gbenga.endurely.R
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.navigation.EndureNavigation
import dev.gbenga.endurely.ui.buttons.EndureButton
import dev.gbenga.endurely.ui.buttons.EndurelyTextField
import dev.gbenga.endurely.ui.buttons.GymScaffold
import dev.gbenga.endurely.ui.theme.xLargePadding
import dev.gbenga.endurely.ui.theme.xXLargePadding
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen (nav: EndureNavigation, viewModel: LoginViewModel = koinViewModel()){
    val loginInUi by viewModel.loginUiState.collectAsStateWithLifecycle()
    LoginScreenContent(buttonText = loginInUi.buttonText, onBackRequest ={
        nav.pop()
    }, logInUi = loginInUi, loginRequest = {
        // Open the dashboard
    } , onErrorRequest = {
        viewModel.clearState()
    }){username, password ->
        viewModel.login(username, password)
    }

}

@Composable
fun LoginScreenContent(buttonText: String, logInUi: LoginUiState,
                       onBackRequest: () -> Unit,
                       loginRequest: () -> Unit,
                       onErrorRequest: () -> Unit,
                       onLoginClick: (String, String) -> Unit){

    val snackbarHostState = remember { SnackbarHostState() }
    GymScaffold(pageTitle = stringResource(R.string.login_title),
        snackbarHostState = snackbarHostState,
        onBackRequest = onBackRequest)  {

        val coroutineScope = rememberCoroutineScope()

        ConstraintLayout(modifier = Modifier.padding(xLargePadding).fillMaxSize()) {
            val (topLayout, editTextLayout, loginBtn, loadingInd) = createRefs()
            Column(modifier = Modifier.constrainAs(topLayout){
                top.linkTo(parent.top)
            }) {
                Text(stringResource(R.string.hello_msg),
                    style = MaterialTheme.typography.headlineLarge)
                Text(stringResource(R.string.sign_in_to_continue),
                    style = MaterialTheme.typography.titleLarge)
            }

            var username by remember{ mutableStateOf("") }
            var password by remember{ mutableStateOf("") }

            Column(modifier = Modifier.constrainAs(editTextLayout){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom, margin = xXLargePadding)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, verticalArrangement = Arrangement.spacedBy(xLargePadding)) {

                EndurelyTextField(value = username, label = R.string.email_lb){ value ->
                    username = value
                }
                EndurelyTextField(value = password, isPassword = true, label = R.string.password_lb){ value ->
                    password = value
                }
            }

            logInUi.loginUi.let { uiState ->
                when(uiState){
                    is UiState.Success ->{
                        loginRequest()
                    }
                    is UiState.Failure ->{
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(uiState.message)
                            onErrorRequest()
                        }

                    }
                    is UiState.Loading ->{
                        CircularProgressIndicator(modifier = Modifier.constrainAs(loadingInd){
                            bottom.linkTo(parent.bottom,
                                margin = xXLargePadding)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },)
                    }
                    else -> { /* Nothing*/}
                }


                EndureButton(text = buttonText, onClick = {
                    onLoginClick(username, password)
                }, visible = uiState !is UiState.Loading,
                    modifier = Modifier.constrainAs(loginBtn){
                        bottom.linkTo(parent.bottom, margin = xXLargePadding)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }.fillMaxWidth().padding(vertical = xXLargePadding))
            }


        }
    }
}