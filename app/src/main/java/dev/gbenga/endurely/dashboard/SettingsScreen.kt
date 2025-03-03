package dev.gbenga.endurely.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gbenga.endurely.ui.theme.largePadding
import dev.gbenga.endurely.ui.theme.normalPadding
import dev.gbenga.endurely.ui.theme.xLargePadding
import org.koin.androidx.compose.koinViewModel

@Composable //
fun SettingsScreen(viewModel: SettingsViewModel, signOutRequest: () -> Unit) {
    val settingsUi by viewModel.settingsUi.collectAsStateWithLifecycle()
    LazyColumn(modifier = Modifier.padding(horizontal = xLargePadding, vertical = xLargePadding).fillMaxSize()) {

        items(settingsUi.settingsItem.size){
            val isDarkMode = isSystemInDarkTheme()

            var toggleTheme by remember { mutableStateOf(settingsUi.isDarkTheme ?: isDarkMode ) }
            ConstraintLayout(modifier = Modifier.fillMaxWidth().clickable {
                when(it){
                    0 ->{
                        toggleTheme = !toggleTheme
                        viewModel.toggleThemeMode(toggleTheme)
                    }
                    1 -> {
                        signOutRequest()
                    }
                }
            }) {
                val (title, subTitle, button) = createRefs()
                Text(settingsUi.settingsItem[it].title, modifier = Modifier.constrainAs(title){
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                })

                Text(settingsUi.settingsItem[it].subTitle, style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                    modifier = Modifier.constrainAs(subTitle){
                    start.linkTo(parent.start)
                    top.linkTo(title.bottom)
                    bottom.linkTo(parent.bottom)
                })
                Box(modifier = Modifier.constrainAs(button){
                        end.linkTo(parent.end)
                        top.linkTo(title.top)
                        bottom.linkTo(parent.bottom)
                    }) {
                    when(settingsUi.settingsItem[it].buttonType){
                        ButtonType.SWITCH ->{
                            Switch(onCheckedChange = {
                                toggleTheme = it
                                viewModel.toggleThemeMode(toggleTheme)
                            }, checked = toggleTheme)
                        }
                        ButtonType.ACTION ->{
                            Icon(Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = settingsUi.settingsItem[it].title,
                                tint = MaterialTheme.typography.bodyLarge.color,
                                modifier = Modifier.size(30.dp))
                        }
                        else ->{
                            /*Notthin*/
                        }
                    }
                }
            }

            Divider(modifier = Modifier.fillMaxWidth().padding(top = normalPadding), thickness = 1.dp, color = Color.DarkGray.copy(alpha = .5f))
        }
    }
}