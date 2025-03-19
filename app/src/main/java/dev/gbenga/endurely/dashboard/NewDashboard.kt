package dev.gbenga.endurely.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import dev.gbenga.endurely.ui.theme.appColor
import dev.gbenga.endurely.ui.theme.largePadding
import dev.gbenga.endurely.ui.theme.menuCardHeight
import dev.gbenga.endurely.ui.theme.normalPadding
import dev.gbenga.endurely.ui.theme.normalRadius
import dev.gbenga.endurely.ui.theme.xXLargePadding

@Composable
fun NewDashboardScreen(index: Int,
                       completedTotal: Triple<String, String, Float>,
                       isDarkTheme: Boolean,
                       menus: List<DashboardMenu>,
                       openMealScreen: (Int) -> Unit){
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (statsSection, mealPlanBtn, titleStats, divider, bottomDivider) = createRefs()
        // .shadow(elevation = 5.dp)
        Divider(thickness = 1.dp, modifier = Modifier.constrainAs(divider){
            top.linkTo(parent.top, largePadding)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, )
        Text("\uD83C\uDFCB\uD83C\uDFFE Keep up the good work! Your completed and in progress exercises",
            modifier = Modifier.constrainAs(titleStats){
            top.linkTo(parent.top, xXLargePadding)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
        ConstraintLayout(modifier = Modifier.constrainAs(statsSection){
            top.linkTo(titleStats.bottom, largePadding)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            val (completedExercises, progressCircle) = createRefs()
            CircularProgressIndicator(progress = { completedTotal.third },
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
                    append(completedTotal.first)
                    // pop the green text style
                    pushStyle(SpanStyle(fontSize = MaterialTheme.typography.titleLarge.fontSize))
                    // append new string, this string will be default color
                    append("/${completedTotal.second}")
                    toAnnotatedString()
                }, style = MaterialTheme.typography
                    .headlineLarge.copy(fontWeight = FontWeight.W900,
                        fontSize = 70.sp))
                Text("Completed/Total Exercises",
                    style = MaterialTheme.typography
                        .titleSmall)
            }
        }

        Divider(thickness = 1.dp, modifier = Modifier.constrainAs(bottomDivider){
            top.linkTo(statsSection.bottom, largePadding)
            bottom.linkTo(mealPlanBtn.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        },)

        DashboardItem(modifier = Modifier
            .constrainAs(mealPlanBtn) {
                top.linkTo(bottomDivider.bottom, largePadding)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .fillMaxWidth()
            .height(menuCardHeight),
            index= index, menus= menus,
            isDarkTheme =isDarkTheme,
            openMealScreen = openMealScreen)
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