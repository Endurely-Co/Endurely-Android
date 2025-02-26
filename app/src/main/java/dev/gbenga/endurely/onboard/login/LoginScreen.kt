package dev.gbenga.endurely.onboard.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gbenga.endurely.R
import dev.gbenga.endurely.core.UiTextField
import dev.gbenga.endurely.navigation.EndureNavigation
import dev.gbenga.endurely.ui.SlideIndicator
import dev.gbenga.endurely.ui.buttons.EndureButton
import dev.gbenga.endurely.ui.theme.btnNormal
import dev.gbenga.endurely.ui.theme.largePadding
import dev.gbenga.endurely.ui.theme.normalPadding
import dev.gbenga.endurely.ui.theme.xLargePadding
import dev.gbenga.endurely.ui.theme.xXLargePadding
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen (nav: EndureNavigation, viewModel: LoginViewModel = koinViewModel()){
    val loginInUi by viewModel.loginUiState.collectAsStateWithLifecycle()
    LoginScreenContent(buttonText = loginInUi.buttonText,){

    }
}

@Composable
fun LoginScreenContent(buttonText: String, loginRequest: () -> Unit){
    Scaffold  {
        ConstraintLayout(modifier = Modifier.padding(it).padding(xLargePadding).fillMaxSize()) {
            val (topLayout, editTextLayout, loginBtn) = createRefs()
            Column(modifier = Modifier.constrainAs(topLayout){
                top.linkTo(parent.top)
            }) {
                Text(stringResource(R.string.hello_msg),
                    style = MaterialTheme.typography.headlineLarge)
                Text(stringResource(R.string.sign_in_to_continue),
                    style = MaterialTheme.typography.titleLarge)
            }

            Column(modifier = Modifier.constrainAs(editTextLayout){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, verticalArrangement = Arrangement.spacedBy(normalPadding)) {
                var email by remember{ mutableStateOf("") }
                var password by remember{ mutableStateOf("") }

                TextField(value = email, onValueChange = { value ->
                    email = value
                }, modifier = Modifier.fillMaxWidth())
                TextField(value = password, onValueChange = { value->
                    password = value
                }, modifier = Modifier.fillMaxWidth())
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