package dev.gbenga.endurely.ui.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import dev.gbenga.endurely.ui.theme.normalRadius


@Composable
fun EndurelyTextField(value: String, isPassword: Boolean = false, label: Int? =null, onValueChanged: (String) -> Unit){
    TextField(value = value,
        singleLine = true,
        shape = RoundedCornerShape(normalRadius),
        label = {
            label?.let { Text(stringResource(it)) }
        },
        textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700),
        visualTransformation = if (isPassword) PasswordVisualTransformation()  else VisualTransformation.None,
        colors  = TextFieldDefaults.colors().copy(focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent),
        onValueChange = { value ->
            onValueChanged(value)
        }, modifier = Modifier.fillMaxWidth())
}