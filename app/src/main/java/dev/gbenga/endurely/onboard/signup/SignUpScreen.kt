package dev.gbenga.endurely.onboard.signup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import dev.gbenga.endurely.navigation.SignUp
import dev.gbenga.endurely.ui.buttons.EndureButton
import dev.gbenga.endurely.ui.buttons.EndurelyTextField
import dev.gbenga.endurely.ui.buttons.GymScaffold
import dev.gbenga.endurely.ui.theme.largePadding
import dev.gbenga.endurely.ui.theme.normalPadding
import dev.gbenga.endurely.ui.theme.xLargePadding
import dev.gbenga.endurely.ui.theme.xXLargePadding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun SigUpScreen (nav: EndureNavigation, viewModel: SignUpViewModel = koinViewModel()){
    val signUpUi by viewModel.signUpState.collectAsStateWithLifecycle()
    SigUpScreenContent(buttonText = signUpUi.buttonText, signUpUi = signUpUi,  onBackRequest ={
        nav.pop()
    }, signUpRequest ={
        nav.gotoLogin()
        viewModel.clearState()
    }, sigUpErrorRequest ={
        viewModel.clearState()
    }) { email, firstName, lastName, password, username ->
        viewModel.signUp(email, firstName, lastName, password, username)
    }
}

@Composable
fun SigUpScreenContent(buttonText: String,onBackRequest: () -> Unit,
                       signUpUi: SignUpUiState,
                       signUpRequest: () -> Unit,
                       sigUpErrorRequest: () -> Unit,
                       onClickSignUp: (String, String, String, String, String) -> Unit){
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val verticalScrollState = rememberScrollState()

    GymScaffold(pageTitle = stringResource(R.string.sign_up_title_title),
        snackbarHostState = snackbarHostState,
        onBackRequest = onBackRequest)  {
        ConstraintLayout(modifier = Modifier
            .padding(xLargePadding)
            .fillMaxSize()) {
            val (topLayout, editTextLayout, loginBtn, loadingInd) = createRefs()
            Column(modifier = Modifier.constrainAs(topLayout){
                top.linkTo(parent.top)
            }) {
                Text(
                    stringResource(R.string.hello_msg),
                    style = MaterialTheme.typography.headlineLarge)
                Text(
                    stringResource(R.string.create_new_acct),
                    style = MaterialTheme.typography.titleSmall
                )
            }

            var email by remember{ mutableStateOf("") }
            var password by remember{ mutableStateOf("") }
            var firstName by remember{ mutableStateOf("") }
            var lastName by remember{ mutableStateOf("") }
            var username by remember{ mutableStateOf("") }

            Column(modifier = Modifier.verticalScroll(verticalScrollState)
                .constrainAs(editTextLayout){
                top.linkTo(topLayout.bottom, largePadding)
                bottom.linkTo(loginBtn.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, verticalArrangement = Arrangement.spacedBy(xLargePadding)) {


                EndurelyTextField(value = firstName, label = R.string.firstname_lb){ value ->
                    firstName = value
                }
                EndurelyTextField(value = lastName, label = R.string.lastname_lb){ value ->
                    lastName = value
                }
                EndurelyTextField(value = email, label = R.string.email_lb){ value ->
                    email = value
                }

                EndurelyTextField(value = username, label = R.string.username_lb){ value ->
                    username = value
                }

                EndurelyTextField(value = password, isPassword = true, label = R.string.password_lb){ value ->
                    password = value
                }
            }


            signUpUi.signUp.let { signUp ->

                var isLoading by remember { mutableStateOf(false) }

                AnimatedVisibility(isLoading, modifier = Modifier.constrainAs(loadingInd){
                    bottom.linkTo(parent.bottom,
                        margin = xXLargePadding)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                    CircularProgressIndicator(modifier = Modifier,)
                }


                LaunchedEffect(signUp) {
                    when(signUp){
                        is UiState.Success -> {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(signUp.data)
                                delay(3000)
                            }
                            signUpRequest()
                        }
                        is UiState.Failure ->{
                            coroutineScope.launch { snackbarHostState.showSnackbar(signUp.message) }
                            sigUpErrorRequest()
                        }

                        else ->{
                            isLoading = signUp is UiState.Loading
                        }
                    }
                }



                EndureButton(text = buttonText, onClick = {
                    onClickSignUp(email, firstName, lastName, password, username)
                },
                    visible = signUp !is UiState.Loading,
                    modifier = Modifier
                        .constrainAs(loginBtn) {
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .fillMaxWidth())
            }


        }
    }
}
