package dev.gbenga.endurely.ui.buttons

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.gbenga.endurely.core.UiState
import kotlinx.coroutines.launch

@Composable
fun <T> UiState<T>.effect(
    modifier: Modifier=Modifier,
    onError: suspend (String) -> Unit,
    onOther: (() -> Unit)? = null,
    onSuccess: @Composable() (T) -> Unit,): Boolean{
    var isLoading by remember { mutableStateOf(false) }
    val successCompose = remember { mutableStateMapOf<String, T>() }
    val coroutineScope = rememberCoroutineScope()
    Log.d("loading", "state ->${this}")
    LaunchedEffect(this) {
        when(this@effect){
            is UiState.Success ->{
                successCompose[this@effect.toString()] = this@effect.data
            }
            is UiState.Failure ->{
                coroutineScope.launch {
                    onError(this@effect.message)
                    isLoading = false
                }
            }
            is UiState.Loading ->{
                isLoading = true
            }
            else ->{
                onOther?.invoke()
                isLoading = false
            }
        }
    }

    Box(modifier = modifier){
        successCompose[this@effect.toString()]?.let {
            onSuccess(it)
            isLoading = false
        }
    }
    return isLoading
}