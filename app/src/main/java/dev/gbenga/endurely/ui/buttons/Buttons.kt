package dev.gbenga.endurely.ui.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
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
import dev.gbenga.endurely.ui.theme.btnNormal
import dev.gbenga.endurely.ui.theme.normalRadius
import dev.gbenga.endurely.ui.theme.normalThickness
import dev.gbenga.endurely.ui.theme.smallRadius

/*
contentColor = Color.White
 */
@Composable
fun EndureButton(text: String, textColor: Color = MaterialTheme.typography.bodyLarge.color,  bgColor: Color? =  null,
                 modifier: Modifier = Modifier,  onClick: () -> Unit){
    Button(onClick = onClick, modifier = modifier.height(btnNormal),
        colors = if(bgColor == null )  ButtonDefaults.buttonColors() else ButtonDefaults.buttonColors(containerColor = bgColor),
        shape = RoundedCornerShape(
            normalRadius)){ //Color.White
        Text(text, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W800, color = textColor))
    }
}
@Composable
fun EndureOutlinedButton(text: String, modifier: Modifier = Modifier,
                         textColor: Color = MaterialTheme.typography.bodyLarge.color,
                         bgColor: Color = Color.White
                             .copy(alpha = .4f), onClick: () -> Unit){

    OutlinedButton(onClick = onClick, modifier = modifier,
        border = BorderStroke(normalThickness, color = textColor),
        colors = ButtonDefaults.outlinedButtonColors().copy(containerColor = bgColor, contentColor = textColor),
        shape = RoundedCornerShape(
            normalRadius)){
        Text(text, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W800))
    }
}
// OutlinedButton