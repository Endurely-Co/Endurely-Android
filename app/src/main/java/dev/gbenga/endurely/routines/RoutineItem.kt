package dev.gbenga.endurely.routines

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import dev.gbenga.endurely.R
import dev.gbenga.endurely.routines.data.RoutineData
import dev.gbenga.endurely.ui.theme.Orange
import dev.gbenga.endurely.ui.theme.appColor
import dev.gbenga.endurely.ui.theme.largePadding
import dev.gbenga.endurely.ui.theme.menuCardHeight
import dev.gbenga.endurely.ui.theme.normalPadding
import dev.gbenga.endurely.ui.theme.normalRadius
import dev.gbenga.endurely.ui.theme.routineImageHeight
import dev.gbenga.endurely.ui.theme.routineImageWidth
import dev.gbenga.endurely.ui.theme.smallPadding
import dev.gbenga.endurely.ui.theme.xXLargePadding

@Composable
fun RoutineUiItem(routine: RoutineData, isDarkMode: Boolean){
    Card(modifier = Modifier.padding(horizontal = normalPadding)
        .fillMaxWidth()
        .clip(RoundedCornerShape(normalRadius))
        //.background(appColor(isDarkMode).defaultCard)
        .height(menuCardHeight).padding(horizontal = normalPadding, vertical = normalPadding),
        elevation = 1.dp, shape = RoundedCornerShape(normalRadius),
        backgroundColor = appColor(isDarkMode).defaultCard
    ) {
        ConstraintLayout(modifier = Modifier){
            val (imageBlock, textBlock) = createRefs()
            LazyColumn(modifier = Modifier.padding(normalPadding).constrainAs(textBlock){
                start.linkTo(parent.start,)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(imageBlock.start, margin = largePadding)
            }, verticalArrangement = Arrangement.spacedBy(5.dp)) {
                item {
                    Text(routine.routineName,
                        style = MaterialTheme.typography.titleLarge)
                    Text(routine.routineRepsStr(), style = MaterialTheme.typography.bodyMedium,)
                    Text(routine.duration(), style = MaterialTheme.typography.bodyMedium,)
                }
                item {
                    // num of exercises completed - to be updated
                    Text("3/${routine.routineReps}", style = MaterialTheme.typography.bodySmall.copy(fontSize = 8.sp),
                    ) // NAME of the exercise
                    LinearProgressIndicator(progress = 3/routine.routineReps.toFloat(),  modifier = Modifier
                        .height(7.dp).fillMaxWidth(.5f))
                }
            }

            Image(painter = painterResource(R.drawable.gym_routine),
                contentDescription = routine.routineName,
                modifier = Modifier.constrainAs(imageBlock){
                    end.linkTo(parent.end, margin = normalPadding)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }.width(routineImageWidth).height(routineImageHeight)
                    .clip(RoundedCornerShape(normalRadius))
                    .background(Color(Orange)),
                contentScale = ContentScale.Fit)

        }
    }
}

@Composable
fun PreviewRoutineItem(){
    //RoutineItem()
}