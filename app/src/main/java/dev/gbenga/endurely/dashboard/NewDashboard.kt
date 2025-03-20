package dev.gbenga.endurely.dashboard

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import dev.gbenga.endurely.R
import dev.gbenga.endurely.core.rememberDateTimeUtils
import dev.gbenga.endurely.extensions.titleCase
import dev.gbenga.endurely.meal.data.GetMealPlan
import dev.gbenga.endurely.meal.data.MealPlan
import dev.gbenga.endurely.ui.buttons.EndureOutlinedButton
import dev.gbenga.endurely.ui.buttons.FitnessLoadingIndicator
import dev.gbenga.endurely.ui.buttons.effect
import dev.gbenga.endurely.ui.theme.appColor
import dev.gbenga.endurely.ui.theme.iconSize
import dev.gbenga.endurely.ui.theme.largePadding
import dev.gbenga.endurely.ui.theme.menuCardHeight
import dev.gbenga.endurely.ui.theme.normalPadding
import dev.gbenga.endurely.ui.theme.normalRadius
import dev.gbenga.endurely.ui.theme.smallPadding
import dev.gbenga.endurely.ui.theme.xLargePadding
import dev.gbenga.endurely.ui.theme.xXLargePadding

@Composable
fun NewDashboardScreen(dashboardUiState: DashboardUiState,
                       homeScreenMeal: HomeScreenMeal,
                       onInValidUser: () -> Unit,
                       openMealScreen: (Int) -> Unit,
                       onOpenMealDetails: (String) -> Unit){
    val verticalScrollState = rememberScrollState()

    ConstraintLayout(modifier = Modifier.padding(horizontal = xLargePadding).fillMaxSize()
        .verticalScroll(verticalScrollState)) {
        val (statsSection, titleStats, divider,
            todaysMealSection, topSection) = createRefs()
        // .shadow(elevation = 5.dp)


        TopBarSection(modifier = Modifier.constrainAs(topSection){
            top.linkTo(parent.top, largePadding)
            start.linkTo(parent.start)
        },
            dashboardUiState.fullName, dashboardUiState.greeting, onInValidUser)

        Divider(thickness = 1.dp, modifier = Modifier.constrainAs(divider){
            top.linkTo(topSection.bottom, largePadding)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, )



        Text("\uD83C\uDFCB\uD83C\uDFFE Keep up the good work! Your completed and in progress exercises",
            modifier = Modifier.constrainAs(titleStats){
            top.linkTo(divider.bottom, largePadding)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
        ConstraintLayout(modifier = Modifier.constrainAs(statsSection){
            top.linkTo(titleStats.bottom, largePadding)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            val animateStatProgress = animateFloatAsState(homeScreenMeal.completedTotal.third, label = "animateStatProgress")
            val (completedExercises, progressCircle) = createRefs()
            CircularProgressIndicator(progress = { animateStatProgress.value },
                strokeCap = StrokeCap.Round,
                strokeWidth = 10.dp,
                trackColor = MaterialTheme.colorScheme.primary.copy(alpha = .4f),
                modifier = Modifier.constrainAs(progressCircle){
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.size(250.dp))
            Column(modifier = Modifier.constrainAs(completedExercises){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, horizontalAlignment = Alignment.CenterHorizontally) {

                Text(   buildAnnotatedString {
                    // append string, this text will be rendered green
                    append(homeScreenMeal.completedTotal.first)
                    // pop the green text style
                    pushStyle(SpanStyle(fontSize = MaterialTheme.typography.titleLarge.fontSize))
                    // append new string, this string will be default color
                    append("/${homeScreenMeal.completedTotal.second}")
                    toAnnotatedString()
                }, style = MaterialTheme.typography
                    .headlineLarge.copy(fontWeight = FontWeight.W900,
                        fontSize = 70.sp))
                Text("Completed/Total Exercises",
                    style = MaterialTheme.typography
                        .titleSmall)
            }
        }

        Column(modifier = Modifier.constrainAs(todaysMealSection){
            top.linkTo(statsSection.bottom, largePadding)
            start.linkTo(parent.start)
        }.fillMaxSize(),) {

           Row(horizontalArrangement = Arrangement.SpaceBetween,
               modifier = Modifier.fillMaxWidth().padding(bottom = normalPadding),
               verticalAlignment = Alignment.CenterVertically) {
               Text(homeScreenMeal.planDay, style = MaterialTheme
                   .typography.titleMedium.copy(fontWeight = FontWeight.Bold))
               TextButton(onClick = {
                   openMealScreen(0)
               }){
                   Text("View All", style = MaterialTheme
                       .typography.titleMedium.copy(fontWeight = FontWeight.Bold))
               }
           }

            FitnessLoadingIndicator(show = homeScreenMeal.mealPlans.effect(onError = {
                // show error
            }) {
                if (it.isEmpty()){
                    Box(modifier = Modifier.fillMaxSize()){
                        Text("No meal plan was set for today",
                            modifier = Modifier.align(Alignment.Center))
                    }
                    return@effect
                }
                Column {
                    it.mapIndexed{i, plan ->
                        Log.d("homeScreenMeal", "homeScreenMeal-homeScreenMeal")
                        MealPlanItems(i, plan, divideContent = {
                            if (i < (it.size -1)){
                                Divider(modifier = Modifier.fillMaxWidth())
                            }
                        }){
                            // click
                            onOpenMealDetails(plan.mealPlanId)
                        }
                    }
                }
            },)
        }

    }
}


@Composable
fun MealPlanItems(index: Int, mealPlan: GetMealPlan,
                  divideContent: @Composable () -> Unit,
                  onClick: (Int) -> Unit){
    val timeUtils = rememberDateTimeUtils()
    Column(verticalArrangement = Arrangement.spacedBy(normalPadding), modifier = Modifier.clickable {
        onClick(index)
    }) {
        Row(horizontalArrangement = Arrangement.spacedBy(largePadding),
            verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(R.drawable.food_bar),
                contentDescription = "Food items", modifier = Modifier.size(iconSize))
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(
                smallPadding)) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(mealPlan.foodName.titleCase(), style = MaterialTheme.typography
                            .titleMedium.copy(fontWeight = FontWeight.Bold, color = Color.Black),)

                    Text(mealPlan.mealDateTime, style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray))
                }
                Text(mealPlan.otherNutrients, style = MaterialTheme.typography.bodySmall, maxLines = 2)
            }

        }
        divideContent()
    }
}

@Composable
fun DashboardItem(modifier: Modifier= Modifier, index: Int,
                  isDarkTheme: Boolean,
                                menus: List<DashboardMenu>, openMealScreen: (Int) -> Unit){
    val bgColor = Color((menus[index].bgColor))
    ConstraintLayout(modifier = modifier
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
            if (index == 0) {
                openMealScreen(index)
            }

        }) {
        val (title, clipArt, chevron) = createRefs()
        Text(menus[index].title, style = MaterialTheme.typography
            .headlineLarge.copy(fontWeight = FontWeight.Bold), modifier = Modifier.constrainAs(title){
            start.linkTo(parent.start, largePadding)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        })
        Image(painter = painterResource(menus[index].clipArt), modifier = Modifier
            .constrainAs(clipArt) {
                start.linkTo(title.end, largePadding)
                bottom.linkTo(parent.bottom)
            }
            .height(menuCardHeight)
            .size(120.dp), contentDescription = menus[index].title)

        IconButton(onClick = {}, modifier = Modifier
            .constrainAs(chevron) {
                end.linkTo(parent.end, margin = normalPadding)
                bottom.linkTo(parent.bottom)
                top.linkTo(parent.top)
            }
            .padding(20.dp)
            .size(60.dp), colors = IconButtonDefaults.iconButtonColors().copy(containerColor = appColor(isDarkTheme).iconBgColor)){
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "View meal plan",
                modifier = Modifier.size(60.dp))
        }
    }
}