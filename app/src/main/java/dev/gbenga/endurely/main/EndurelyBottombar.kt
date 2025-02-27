package dev.gbenga.endurely.main

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gbenga.endurely.ui.theme.xLargePadding
import org.koin.androidx.compose.koinViewModel

@SuppressLint("RestrictedApi")
@Composable
fun EndurelyBottomBar(onItemClick: (Int) -> Unit,
                      viewModel: EndurelyBottomBarViewModel = koinViewModel()) {

    val uiState by viewModel.bbUiState.collectAsStateWithLifecycle()
    var selectedIndex by remember { mutableIntStateOf(0) }
    val iconDefaultColor =  Color.DarkGray.copy(alpha = .4f)
    val primary =  MaterialTheme.colorScheme.secondary


    BottomAppBar(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(horizontal = xLargePadding)) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            uiState.bottomBarItems.forEachIndexed { index,  bottomRoute ->

                    Column(modifier = Modifier.clickable {
                        selectedIndex = index
                        onItemClick(index)
                    }.wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(painter = painterResource(bottomRoute.icon),
                            contentDescription = "", tint = if (index == selectedIndex) primary else iconDefaultColor )
                        Text(bottomRoute.name, style = MaterialTheme.typography
                            .bodySmall.copy(fontSize = 9.sp,
                                fontWeight = if (index == selectedIndex) FontWeight.Bold else FontWeight.Normal,
                                color = if (index == selectedIndex) primary else iconDefaultColor))
                    }
                }
        }
    }

}