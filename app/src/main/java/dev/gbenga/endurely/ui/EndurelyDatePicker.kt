package dev.gbenga.endurely.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.gbenga.endurely.ui.theme.appColor
import dev.gbenga.endurely.ui.theme.btnNormal
import dev.gbenga.endurely.ui.theme.largePadding
import dev.gbenga.endurely.ui.theme.normalPadding
import dev.gbenga.endurely.ui.theme.normalRadius
import dev.gbenga.endurely.ui.theme.smallPadding

@ExperimentalMaterial3Api
@Composable
fun EndurelyDatePicker(show: Boolean, isDarkTheme: Boolean, onSelectDate: (Long) -> Unit, onDismissRequest: () -> Unit){

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        selectableDates = PastOrPresentSelectableDates
        )
    if(show){


        Dialog(onDismissRequest = {
            onDismissRequest()
        }, properties = DialogProperties(usePlatformDefaultWidth = false),) {

            Box(modifier = Modifier.padding(largePadding)) {
                Column(modifier = Modifier.fillMaxWidth().background(appColor(isDarkTheme)
                    .defaultCard).clip(RoundedCornerShape(normalRadius)), horizontalAlignment = Alignment.Start) {
                    DatePicker(state = datePickerState,
                        modifier = Modifier.fillMaxWidth(),
                        colors = DatePickerDefaults.colors().copy(
                            disabledDayContentColor = Color.Gray,
                            disabledSelectedDayContentColor = Color.Gray))
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

}
/*
  TextButton(onClick = { onDismissRequest() }) {
                        Text("Dismiss")
                    }
 */