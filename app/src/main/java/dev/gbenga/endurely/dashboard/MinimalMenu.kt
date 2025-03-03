package dev.gbenga.endurely.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MinimalDropdownMenu(expanded: Boolean, onClickItem: (Boolean, Int) -> Unit) {
    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        DropdownMenu(

            expanded = expanded,
            onDismissRequest = { onClickItem(false, -1) }
        ) {
            DropdownMenuItem(
                text = { Text("Sign Out", style = MaterialTheme.typography.bodyMedium) },
                onClick = { onClickItem(false, 0) }
            )
        }
    }
}