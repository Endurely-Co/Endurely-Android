package dev.gbenga.endurely.onboard.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gbenga.endurely.R
import dev.gbenga.endurely.navigation.EndureNavigation
import dev.gbenga.endurely.onboard.login.LoginViewModel
import dev.gbenga.endurely.ui.buttons.EndureButton
import dev.gbenga.endurely.ui.buttons.EndurelyTextField
import dev.gbenga.endurely.ui.buttons.GymScaffold
import dev.gbenga.endurely.ui.theme.xLargePadding
import dev.gbenga.endurely.ui.theme.xXLargePadding
import org.koin.androidx.compose.koinViewModel


@Composable
fun SigUpScreen (nav: EndureNavigation, viewModel: LoginViewModel = koinViewModel()){
    val loginInUi by viewModel.loginUiState.collectAsStateWithLifecycle()
    SigUpScreenContent(buttonText = loginInUi.buttonText, onBackRequest ={
        nav.pop()
    },) {

    }
}

@Composable
fun SigUpScreenContent(buttonText: String,onBackRequest: () -> Unit, loginRequest: () -> Unit){
    GymScaffold(pageTitle = stringResource(R.string.sign_up_title_title),
        onBackRequest = onBackRequest)  {
        ConstraintLayout(modifier = Modifier.padding(xLargePadding).fillMaxSize()) {
            val (topLayout, editTextLayout, loginBtn) = createRefs()
            Column(modifier = Modifier.constrainAs(topLayout){
                top.linkTo(parent.top)
            }) {
                Text(
                    stringResource(R.string.hello_msg),
                    style = MaterialTheme.typography.headlineLarge)
                Text(
                    stringResource(R.string.create_new_acct),
                    style = MaterialTheme.typography.titleSmall)
            }

            Column(modifier = Modifier.constrainAs(editTextLayout){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, verticalArrangement = Arrangement.spacedBy(xLargePadding)) {
                var email by remember{ mutableStateOf("") }
                var password by remember{ mutableStateOf("") }
                var firstName by remember{ mutableStateOf("") }
                var lastName by remember{ mutableStateOf("") }

                EndurelyTextField(value = firstName, label = R.string.firstname_lb){ value ->
                    firstName = value
                }
                EndurelyTextField(value = lastName, label = R.string.lastname_lb){ value ->
                    lastName = value
                }
                EndurelyTextField(value = email, label = R.string.email_lb){ value ->
                    email = value
                }
                EndurelyTextField(value = password, isPassword = true, label = R.string.password_lb){ value ->
                    password = value
                }
            }

            EndureButton(text = buttonText, onClick = loginRequest,
                modifier = Modifier.constrainAs(loginBtn){
                    bottom.linkTo(parent.bottom, margin = xXLargePadding)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.fillMaxWidth().padding(vertical = xXLargePadding))
        }
    }
}
