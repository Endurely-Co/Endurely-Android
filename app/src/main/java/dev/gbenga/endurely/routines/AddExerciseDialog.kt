package dev.gbenga.endurely.routines

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.gbenga.endurely.ui.EndurelyAlertDialog
import dev.gbenga.endurely.ui.buttons.EndurelyTextField
import dev.gbenga.endurely.ui.theme.btnNormal
import dev.gbenga.endurely.ui.theme.normalPadding

@Composable
fun AddExerciseDialog(showDialog: Boolean, isDarkTheme: Boolean){

    var minsValue by remember { mutableStateOf("") }
    var hrsValue by remember { mutableStateOf("") }

    EndurelyAlertDialog(show=showDialog, onConfirm = {

    }, onDismiss = {

    }, isDarkTheme = isDarkTheme) {

        Row(horizontalArrangement = Arrangement.spacedBy(normalPadding)) {
            EndurelyTextField(minsValue,
                modifier = Modifier.size(btnNormal)) {
                minsValue = it
            }
            EndurelyTextField(hrsValue,
                modifier = Modifier.size(btnNormal)) {
                hrsValue = it
            }
        }
    }
}