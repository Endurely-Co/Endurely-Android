package dev.gbenga.endurely.ui.buttons

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.gbenga.endurely.ui.theme.normalPadding


// var selected by remember { mutableStateOf(false) }
@Composable
fun AppChip(modifier: Modifier = Modifier, title: String="New chip",
            selected: Boolean, enabled: Boolean = true,
            onSelect: (Boolean) -> Unit) {

    FilterChip(
        modifier = modifier
            .height(40.dp)
            .padding(horizontal = normalPadding),
        selected = selected,
        onClick = { onSelect(selected)},
        enabled = enabled,
        label = { Text(title) },
        colors = FilterChipDefaults.filterChipColors(selectedLabelColor = MaterialTheme.colorScheme.primary),
    )
}