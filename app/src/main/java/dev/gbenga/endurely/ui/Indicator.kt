package dev.gbenga.endurely.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SlideIndicator( count: Int, selection: Int, modifier: Modifier = Modifier){
    var indWidth by remember{ mutableFloatStateOf(0f) }
    Spacer(modifier = modifier.drawWithContent {
        (0..<count).map {
            indWidth = if (selection ==it) 20f else 10f
            drawCircle(color = Color.White, radius = indWidth, center = Offset(it * (20f * 2f), 0f)) }
    }.wrapContentSize())
}


@Preview
@Composable
fun PreviewSlideIndicator(){
    SlideIndicator(4, 3)
}