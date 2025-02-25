package dev.gbenga.endurely.ui.buttons

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import dev.gbenga.endurely.ui.theme.normalRadius
import dev.gbenga.endurely.ui.theme.smallRadius

/*
contentColor = Color.White
 */
@Composable
fun EndureButton(text: String,  bgColor: Color =  Color.White, modifier: Modifier = Modifier,  onClick: () -> Unit){
    Button(onClick = onClick, modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = bgColor),
        shape = RoundedCornerShape(
            normalRadius)){
        Text(text, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W800))
    }
}
@Composable
fun EndureOutlinedButton(text: String, modifier: Modifier = Modifier,
                         bgColor: Color = Color.Black
                             .copy(alpha = .4f), onClick: () -> Unit){

    OutlinedButton(onClick = onClick, modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors().copy(containerColor = bgColor),
        shape = RoundedCornerShape(
            normalRadius)){
        Text(text, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W800))
    }
}
// OutlinedButton