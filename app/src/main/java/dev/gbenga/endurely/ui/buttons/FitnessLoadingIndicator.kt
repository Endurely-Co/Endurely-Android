package dev.gbenga.endurely.ui.buttons

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import dev.gbenga.endurely.R

@Composable
fun FitnessLoadingIndicator(modifier: Modifier = Modifier, show: Boolean = true) {
    val initial = remember { 0f }
    val primaryColor = MaterialTheme.colorScheme.primary
    var rotation by remember { mutableFloatStateOf(initial) }
    val rotationAnim by animateFloatAsState(rotation, label = "",)
    if (show) {
        Box(modifier = Modifier.fillMaxSize()) {

            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
//        val bicepsIndicator = ImageBitmap.imageResource(R.drawable.ball)
//        Spacer(modifier = modifier.drawWithContent {
//            rotate(rotationAnim) {
//
//                drawImage(
//                    bicepsIndicator,
//
//                    dstSize = IntSize(100, 100),
//                    //colorFilter = ColorFilter.tint(primaryColor)
//                )
//                rotation = if (rotationAnim == 0f) {
//                    initial + 280f
//                } else initial
//            }
//
//        }.wrapContentSize())
    }
}