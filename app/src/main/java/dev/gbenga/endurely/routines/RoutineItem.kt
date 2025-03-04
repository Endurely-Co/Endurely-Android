package dev.gbenga.endurely.routines

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import dev.gbenga.endurely.R
import dev.gbenga.endurely.routines.data.RoutineData
import dev.gbenga.endurely.ui.theme.appColor
import dev.gbenga.endurely.ui.theme.menuCardHeight
import dev.gbenga.endurely.ui.theme.normalPadding
import dev.gbenga.endurely.ui.theme.normalRadius
import dev.gbenga.endurely.ui.theme.routineImageHeight
import dev.gbenga.endurely.ui.theme.routineImageWidth

@Composable
fun RoutineUiItem(modifier: Modifier, routine: RoutineData, index: Int,
                  isDarkMode: Boolean, onItemClick: (Int) -> Unit){
    Card(modifier = modifier.padding(horizontal = normalPadding)
        .fillMaxWidth()
        .clip(RoundedCornerShape(normalRadius))
        //.background(appColor(isDarkMode).defaultCard)
        .height(menuCardHeight)
        .padding(horizontal = normalPadding, vertical = normalPadding)
        .clickable {
            onItemClick(index)
        },
        elevation = 1.dp, shape = RoundedCornerShape(normalRadius),
        backgroundColor = appColor(isDarkMode).defaultCard
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()){
            val (imageBlock, textBlock) = createRefs()
            LazyColumn(modifier = Modifier//.padding(normalPadding)
                .constrainAs(textBlock){
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(imageBlock.start)
            }.fillMaxWidth(.5f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                item {

                    Text(routine.routineName,
                        style = MaterialTheme.typography.titleLarge
                            .copy(), maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth())
                    Text(routine.routineRepsStr(), style = MaterialTheme.typography.bodyMedium,)
                    Text(routine.totalDuration(), style = MaterialTheme.typography.bodyMedium,)
                }
                item {
                    // num of exercises completed - to be updated
                    Text(routine.getCompleted(), style = MaterialTheme.typography.bodySmall, maxLines = 1,
                    ) // NAME of the exercise
                    LinearProgressIndicator(progress = routine.progress(),  modifier = Modifier
                        .height(7.dp).fillMaxWidth())
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
                    .background(Color(if(index % 2 == 0) 0xFF03A9F4 else 0xFF81C784)),
                contentScale = ContentScale.Fit)

        }
    }
}

@Composable
fun PreviewRoutineItem(){
    //RoutineItem()
}