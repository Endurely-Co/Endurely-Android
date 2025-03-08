package dev.gbenga.endurely.ui.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import dev.gbenga.endurely.ui.theme.normalRadius


@Composable
fun EndurelyTextField(value: String, modifier: Modifier = Modifier.fillMaxWidth(),
                      keyboardType: KeyboardOptions = KeyboardOptions.Default,
                      readOnly: Boolean = false, isPassword: Boolean = false, label: Int? =null, onValueChanged: (String) -> Unit){
    TextField(value = value,
        singleLine = true,
        readOnly = readOnly,
        shape = RoundedCornerShape(normalRadius),
        label = {
            label?.let { Text(stringResource(it)) }
        },
        keyboardActions = KeyboardActions(),
        keyboardOptions = keyboardType,
        textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700),
        visualTransformation = if (isPassword) PasswordVisualTransformation()  else VisualTransformation.None,
        colors  = TextFieldDefaults.colors().copy(focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent),
        onValueChange = { value ->
            onValueChanged(value)
        }, modifier = modifier)
}


@Composable
fun NoLabelTextField(value: String, modifier: Modifier = Modifier.fillMaxWidth(),
                      keyboardType: KeyboardOptions = KeyboardOptions.Default,
                      readOnly: Boolean = false, isPassword: Boolean = false, onValueChanged: (String) -> Unit){
    TextField(value = value,
        singleLine = true,
        readOnly = readOnly,
        shape = RoundedCornerShape(normalRadius),
        keyboardActions = KeyboardActions(),
        keyboardOptions = keyboardType,
        textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700),
        visualTransformation = if (isPassword) PasswordVisualTransformation()  else VisualTransformation.None,
        colors  = TextFieldDefaults.colors().copy(focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent),
        onValueChange = { value ->
            onValueChanged(value)
        }, modifier = modifier)
}