package dev.gbenga.endurely.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import dev.gbenga.endurely.ui.theme.appColor
import dev.gbenga.endurely.ui.theme.btnNormal
import dev.gbenga.endurely.ui.theme.normalPadding
import dev.gbenga.endurely.ui.theme.smallPadding

@ExperimentalMaterial3Api
@Composable
fun EndurelyDatePicker(show: Boolean, isDarkTheme: Boolean, onSelectDate: (Long) -> Unit, onDismissRequest: () -> Unit){

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = 1578096000000)
    if(show){
        Dialog(onDismissRequest = {
            onDismissRequest()
        }) {
            Column(modifier = Modifier.fillMaxWidth().background(appColor(isDarkTheme).defaultCard), horizontalAlignment = Alignment.Start) {
                DatePicker(state = datePickerState,
                    modifier = Modifier.fillMaxWidth())
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let {
                            onDismissRequest()
                        }
                    }, modifier = Modifier.padding(horizontal = smallPadding)) {

                        Text("Dismiss", style = MaterialTheme.typography
                            .body1.copy(fontWeight = FontWeight.W900),
                            modifier = Modifier.height(btnNormal))
                    }

                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let {
                            onSelectDate(it)
                            onDismissRequest()
                        }
                    }, modifier = Modifier.padding(horizontal = normalPadding)) {

                        Text("Done", style = MaterialTheme.typography
                            .body1.copy(fontWeight = FontWeight.W900),
                            modifier = Modifier.height(btnNormal))
                    }
                }
            }
        }

    }

}
/*
  TextButton(onClick = { onDismissRequest() }) {
                        Text("Dismiss")
                    }
 */