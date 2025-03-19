package dev.gbenga.endurely.dashboard

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import dev.gbenga.endurely.R
import dev.gbenga.endurely.core.Tokens
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.navigation.EndureNavigation
import dev.gbenga.endurely.ui.theme.largePadding
import dev.gbenga.endurely.ui.theme.menuCardHeight
import dev.gbenga.endurely.ui.theme.normalRadius
import dev.gbenga.endurely.ui.theme.xLargePadding

@Composable
fun DashboardScreenList(dashboardUiState: DashboardUiState,
                        isDarkTheme: Boolean,
                        openMealScreen: (Int) -> Unit,
                        onInValidUser: () -> Unit){

    val scrollState = rememberLazyListState()
    val context = LocalContext.current
    val verticalScrollState = rememberScrollState()

    Column(modifier = Modifier
        .padding(horizontal = xLargePadding)
        .verticalScroll(verticalScrollState),
        verticalArrangement = Arrangement.SpaceBetween, ) {
        TopBarSection(dashboardUiState.fullName, dashboardUiState.greeting, onInValidUser)

        NewDashboardScreen(0,
            dashboardUiState.statsSummary,
            isDarkTheme,
            dashboardUiState.dashboardMenus, openMealScreen)

    }
}

@Composable
fun TopBarSection(fullNameVal: UiState<String>, greeting: String, onInValidUser: () -> Unit){

    Text(greeting)
    when(fullNameVal){
        is UiState.Success ->{
            Text(
                fullNameVal.data, style = MaterialTheme.typography
                    .titleLarge.copy(fontWeight = FontWeight.W900, fontSize = 30.sp))
        }
        is UiState.Failure ->{
            onInValidUser()
        }
        else ->{}
    }
}
