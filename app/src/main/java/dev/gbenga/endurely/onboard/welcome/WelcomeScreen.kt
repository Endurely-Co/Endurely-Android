package dev.gbenga.endurely.onboard.welcome

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gbenga.endurely.R
import dev.gbenga.endurely.extensions.halfWidthModifier
import dev.gbenga.endurely.navigation.EndureNavigation
import dev.gbenga.endurely.ui.SlideIndicator
import dev.gbenga.endurely.ui.buttons.EndureButton
import dev.gbenga.endurely.ui.buttons.EndureOutlinedButton
import dev.gbenga.endurely.ui.theme.largePadding
import dev.gbenga.endurely.ui.theme.normalPadding
import dev.gbenga.endurely.ui.theme.xLargePadding
import dev.gbenga.endurely.ui.theme.xXLargePadding
import org.koin.androidx.compose.koinViewModel

@Composable
fun WelcomeScreen(navigation: EndureNavigation, welcomeViewModel: WelcomeViewModel = koinViewModel()) {
    val welcomeUiState by welcomeViewModel.welcomeUiState.collectAsStateWithLifecycle()
    WelcomeScreenContent( welcomeUiState.welcomeContent, signUpRequest ={
        navigation.gotoLogin()
    }, loginRequest = {
        navigation.gotoSignUp()
    })
}

@Composable
fun WelcomeScreenContent( welcomeContent: List<WelcomeContent>, loginRequest: () -> Unit,
                          signUpRequest: () -> Unit){
    var screenSize by remember { mutableStateOf(IntSize.Zero) }
    Scaffold(modifier = Modifier
        .fillMaxWidth()
        .onGloballyPositioned {
            screenSize = it.size
        }
    ) {
        val pageState = rememberPagerState (pageCount = {welcomeContent.size})
        val bgColor = animateColorAsState(targetValue = Color(welcomeContent[pageState
            .currentPage].colorInt),
            label = "",// animationSpec =
        )
        ConstraintLayout (modifier = Modifier
            .background(bgColor.value).fillMaxSize(),) {
            val (bottomContent, image) = createRefs()
        HorizontalPager(pageState, modifier = Modifier
            .padding(it).fillMaxSize()) { page ->
                Image(painter = painterResource(welcomeContent[pageState
                    .currentPage].clipArt), contentDescription = stringResource(
                    R.string.welcome_image),
                    modifier = Modifier
                        .fillMaxSize().constrainAs(image){
                            bottom.linkTo(parent.bottom)
                        },
                    alignment = Alignment.BottomCenter,
                    contentScale = ContentScale.FillHeight,)

            }

            BottomContent(modifier = Modifier.padding(horizontal = xLargePadding)
                .padding(bottom = xXLargePadding)
                .constrainAs(bottomContent){
                bottom.linkTo(parent.bottom)
            }, loginRequest = loginRequest,
                btnTextColor = bgColor.value,
                signUpRequest = signUpRequest, description = welcomeContent[pageState.currentPage].description,
                title = welcomeContent[pageState.currentPage].title){
                SlideIndicator(modifier = Modifier.align(Alignment.Center), count = welcomeContent.size, selection = pageState.currentPage)
            }
        }

    }

}

@Composable
fun BottomContent(modifier: Modifier, title: String, btnTextColor: Color,   description: String, loginRequest: () -> Unit,
                  signUpRequest: () -> Unit, sliderContent: @Composable BoxScope.() -> Unit){

        Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {

            Text(title, style = MaterialTheme
                .typography.headlineLarge.copy(fontWeight = FontWeight.W900,
                    color = Color.White, ),modifier = Modifier.padding(
                bottom = normalPadding), textAlign = TextAlign.Center)
            Text(description,
                style = MaterialTheme
                    .typography.bodyLarge.copy(fontWeight = FontWeight.W900, color = Color.White,),
                modifier = Modifier.padding(
                    bottom = xLargePadding), textAlign = TextAlign.Center)

            Box(modifier = Modifier.padding(bottom = xXLargePadding, top = largePadding)
                .padding(horizontal = largePadding).fillMaxWidth()) {
                sliderContent()
            }
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (signUp, login) = createRefs()
                EndureButton("Login", onClick = loginRequest,
                    modifier = Modifier.halfWidthModifier().constrainAs(login){
                        end.linkTo(parent.end)
                    }, bgColor = Color.White, textColor = btnTextColor)

                EndureOutlinedButton("Signup", onClick = signUpRequest,
                    modifier = Modifier.halfWidthModifier()
                        .constrainAs(signUp){
                            start.linkTo(parent.start)
                        })
            }
        }


}

@Composable
@Preview
fun PreviewWelcomeScreen(){
   // WelcomeScreenContent()
}