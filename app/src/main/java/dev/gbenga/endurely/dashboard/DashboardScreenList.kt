package dev.gbenga.endurely.dashboard

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import dev.gbenga.endurely.ui.theme.largePadding
import dev.gbenga.endurely.ui.theme.menuCardHeight
import dev.gbenga.endurely.ui.theme.normalRadius
import dev.gbenga.endurely.ui.theme.xLargePadding


@Composable
fun DashboardScreenList(dashboardUiState: DashboardUiState, onInValidUser: () -> Unit){

    val scrollState = rememberLazyListState()

    LazyColumn(modifier = Modifier
        .padding()
        .padding(xLargePadding),
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(
            largePadding
        ), ) {
        item{
            TopBarSection(dashboardUiState.fullName, dashboardUiState.greeting, onInValidUser)
        }
        dashboardUiState.dashboardMenus.let { menus ->
            items(menus.size){ index ->
                val bgColor = Color((menus[index].bgColor))
                ConstraintLayout(modifier = Modifier
                    .clip(
                        RoundedCornerShape(normalRadius)
                    )
                    .shadow(
                        shape = RectangleShape, elevation = 3.dp,
                        spotColor = bgColor
                    )

                    .background(Color((menus[index].bgColor)))
                    .fillMaxWidth()
                    .height(menuCardHeight)
                    .clickable {

                    }) {
                    val (title, clipArt) = createRefs()
                    Text(menus[index].title, style = MaterialTheme.typography
                        .headlineLarge.copy(fontWeight = FontWeight.Bold), modifier = Modifier.constrainAs(title){
                        if (index % 2 == 0){
                            start.linkTo(parent.start, largePadding)
                            // end.linkTo(clipArt.start)
                        }else{
                            //end.linkTo(parent.end)
                            start.linkTo(clipArt.end, largePadding)
                        }
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    })
                    Image(painter = painterResource(menus[index].clipArt), modifier = Modifier
                        .constrainAs(clipArt) {
                            if (index % 2 == 0) {
                                start.linkTo(title.end, largePadding)
                                // end.linkTo(clipArt.start)
                            } else {
                                start.linkTo(parent.start, largePadding)
                            }
                            bottom.linkTo(parent.bottom)
                        }
                        .height(menuCardHeight)
                        .size(120.dp), contentDescription = menus[index].title)
                }
            }
        }

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
    )), signOutRequest = {}, onItemClick = {}){}
}