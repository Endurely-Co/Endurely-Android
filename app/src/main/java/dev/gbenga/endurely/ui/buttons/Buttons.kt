package dev.gbenga.endurely.ui.buttons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gbenga.endurely.ui.theme.btnNormal
import dev.gbenga.endurely.ui.theme.largePadding
import dev.gbenga.endurely.ui.theme.normalPadding
import dev.gbenga.endurely.ui.theme.normalRadius
import dev.gbenga.endurely.ui.theme.normalThickness
import dev.gbenga.endurely.ui.theme.textFieldNormal

/*
contentColor = Color.White
 */
@Composable
fun EndureButton(text: String, textColor: Color = MaterialTheme.typography.bodyLarge.color,
                 bgColor: Color? =  null,
                 modifier: Modifier = Modifier,
                 visible: Boolean = true,
                 onClick: () -> Unit){
    AnimatedVisibility(visible, modifier = modifier.height(btnNormal)) {
        Button(onClick = onClick,
            colors = if(bgColor == null )  ButtonDefaults.buttonColors() else ButtonDefaults.buttonColors(containerColor = bgColor),
            shape = RoundedCornerShape(
                normalRadius)){ //Color.White
            Text(text, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W800, color = textColor))
        }
    }
}
@Composable
fun EndureOutlinedButton(text: String, modifier: Modifier = Modifier,
                         textColor: Color = MaterialTheme.typography.bodyLarge.color,
                         enabled: Boolean = true,
                         bgColor: Color = Color.White
                             .copy(alpha = .4f), onClick: () -> Unit){

    OutlinedButton(onClick = onClick, modifier = modifier,
        enabled =enabled,
        border = BorderStroke(normalThickness, color = textColor),
        colors = ButtonDefaults.outlinedButtonColors().copy(containerColor = bgColor, contentColor = textColor),
        shape = RoundedCornerShape(
            normalRadius)){
        Text(text, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W800))
    }
}
// OutlinedButton

@Composable
fun TextFieldButton(title: String,
                    modifier: Modifier =Modifier,
                    bgColor: Color= Color.Gray.copy(alpha = .3f),
                    onClick: () -> Unit){

    Column(modifier = modifier
        .clickable {
            onClick()
        }
        .clip(RoundedCornerShape(normalRadius))
        .background(bgColor)
        .height(textFieldNormal)
        .padding(horizontal = largePadding), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start) {
        Text(title, style = MaterialTheme.typography.bodyMedium,)
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EndurelyBottomSheet(showDialog: Boolean, sheetState: SheetState) {
    var showBottomSheet by remember { mutableStateOf(false) }

    if (showDialog){
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(),
            sheetState = sheetState,
            onDismissRequest = { showBottomSheet = false }
        ) {
            Text(
                "Swipe up to open sheet. Swipe down to dismiss.",
                modifier = Modifier.padding(16.dp)
            )
        }
    }

}

@Composable
@Preview
fun PreviewTextFieldButton(){
    TextFieldButton("12 May, 2023"){}
}