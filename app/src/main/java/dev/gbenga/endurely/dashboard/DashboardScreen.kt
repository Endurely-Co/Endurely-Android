package dev.gbenga.endurely.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gbenga.endurely.R
import dev.gbenga.endurely.core.Tokens
import dev.gbenga.endurely.navigation.EndureNavigation
import dev.gbenga.endurely.ui.theme.Orange
import dev.gbenga.endurely.ui.theme.largePadding
import dev.gbenga.endurely.ui.theme.menuCardHeight
import dev.gbenga.endurely.ui.theme.normalPadding
import dev.gbenga.endurely.ui.theme.normalRadius
import dev.gbenga.endurely.ui.theme.xLargePadding
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(nav: EndureNavigation, viewModel: DashboardViewModel = koinViewModel()) {
    val dashboardUi by viewModel.dashboardUi.collectAsStateWithLifecycle()
    DashboardScreenContent(dashboardUi)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreenContent(dashboardUiState: DashboardUiState){
    Scaffold(
        topBar = {
            TopAppBar(title = {

            }, navigationIcon = {
                IconButton(onClick = {

                }) {
                    Icon(painter = painterResource(R.drawable.four_squares_menu), "", modifier = Modifier.size(30.dp))
                }
            }, actions = {
                TextButton(onClick = {}) {
                    Text("T", textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.drawBehind {
                            drawCircle(color = Color(Orange), radius = 5f)
                        }.size(80.dp))
                }
            })
        }
    ) {
       DashboardScreenList(it, dashboardUiState.dashboardMenus)
    }
}

@Composable
fun DashboardScreenList(paddingValues: PaddingValues, dashboardMenus: List<DashboardMenu>){
    LazyColumn(modifier = Modifier.padding(paddingValues).padding(largePadding),
        verticalArrangement = Arrangement.spacedBy(
        normalPadding), ) {
        item{
            Text("Good morning")
            Text("John Doe", style = MaterialTheme.typography
                .titleLarge.copy(fontWeight = FontWeight.W900, fontSize = 30.sp))
        }
        dashboardMenus.let { menus ->
            items(menus.size){ index ->
                ConstraintLayout(modifier = Modifier
                    .clip(
                        RoundedCornerShape(normalRadius)
                    )
                    .background(Color((menus[index].bgColor)))
                    .fillMaxWidth().height(menuCardHeight)) {
                    val (title, clipArt) = createRefs()
                    Text(menus[index].title, style = MaterialTheme.typography
                        .headlineLarge.copy(fontWeight = FontWeight.Bold), modifier = Modifier.constrainAs(title){
                        if (index % 2 == 0){
                            start.linkTo(parent.start, largePadding)
                           // end.linkTo(clipArt.start)
                        }else{
                           //end.linkTo(parent.end)
                            start.linkTo(clipArt.end,largePadding)
                        }
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    })
                    Image(painter = painterResource(menus[index].clipArt), modifier = Modifier.constrainAs(clipArt){
                        if (index % 2 == 0){
                            start.linkTo(title.end, largePadding)
                            // end.linkTo(clipArt.start)
                        }else{
                            start.linkTo(parent.start, largePadding)
                        }
                        bottom.linkTo(parent.bottom)
                    }.height(menuCardHeight).size(120.dp), contentDescription = menus[index].title)
                }
            }
        }

    }
}

@Preview(device = Devices.PIXEL)
@Composable
fun PreviewDashboardScreenContent(){
    DashboardScreenContent(DashboardUiState(dashboardMenus = listOf(
        DashboardMenu(title = Tokens.trainingPlan,
            bgColor = 0xFFE53935.toInt(),
            clipArt = R.drawable.training_plan_ic),
        DashboardMenu(title = Tokens.mealPlan,
            bgColor = 0xFF66BB6A.toInt(),
            clipArt = R.drawable.meal_plan_ic),
        DashboardMenu(title = Tokens.fitnessRecommendation,
            bgColor = 0xFF42A5F5.toInt(),
            clipArt = R.drawable.fitness_recomm_ic),
        DashboardMenu(title = Tokens.trackCalories,
            bgColor = 0xFFE3C1A5.toInt(),
            clipArt = R.drawable.calorie_tracker_ic)
    )))
}