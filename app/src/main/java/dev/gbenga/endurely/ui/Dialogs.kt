package dev.gbenga.endurely.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.gbenga.endurely.ui.theme.appColor


@Composable
fun EndurelyAlertDialog(
    show: Boolean,
    isDarkTheme : Boolean,
    onDismiss: (() -> Unit)? = null,
    onConfirm: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    if (show){
        AlertDialog(
            modifier = Modifier.fillMaxWidth().background(
                appColor(isDarkTheme).defaultCard),
            onDismissRequest = {onDismiss?.invoke()},
            dismissButton = {
                onDismiss?.let {
                    TextButton(onClick = { onDismiss?.invoke() }) {
                        Text("Dismiss")
                    }
                }

            },
            confirmButton = {
                onConfirm?.let {
                    TextButton(onClick = { onConfirm.invoke() }) {
                        Text("OK")
                    }
                }

            },
            text = { Column {
                content()
            } }
        )
    }

}

