package dev.gbenga.endurely.ui.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import dev.gbenga.endurely.R
import dev.gbenga.endurely.ui.theme.Orange
import dev.gbenga.endurely.ui.theme.appColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GymScaffold(
    modifier: Modifier =Modifier,
    isDarkMode: Boolean = isSystemInDarkTheme(),
    pageTitle: String? = null,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    backIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    actions: @Composable RowScope.() -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    onBackRequest: (() -> Unit)? = null, content: @Composable () -> Unit){


    Scaffold (
        floatingActionButton = floatingActionButton,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                // custom snackbar with the custom colors
                Snackbar(
                    contentColor = Color.White,
                    containerColor = appColor(isDarkMode).snackBg,
                    snackbarData = data
                )
            }
        },
        modifier = modifier.fillMaxSize(),
        topBar = {
            pageTitle?.let { TopAppBar(title = {
                Text(pageTitle)
            }, navigationIcon = {

                IconButton(onClick = {
                    onBackRequest?.invoke()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }, actions = actions) }
        },

    ) {
        ConstraintLayout(modifier = Modifier.padding(it).fillMaxSize()) {
            val (image, mainLayout) = createRefs()
            Image(painter = painterResource(R.drawable.white_page_ca),
                modifier = Modifier.constrainAs(image){
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                },
                contentDescription = "Gym clip art",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary))
            Box(modifier = Modifier.constrainAs(mainLayout){
                bottom.linkTo(parent.bottom)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
            }){
                content()
            }

        }
    }
}