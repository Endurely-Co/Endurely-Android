package dev.gbenga.endurely.core

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


abstract class EndureNavViewModel( savedStateHandle: SavedStateHandle? = null) : ViewModel() {

    fun runInScope(run: suspend () -> Unit): Job = viewModelScope.launch { run() }

    open fun clearState(){}
}

abstract class EndureNavAppViewModel(private val savedStateHandle: SavedStateHandle, app: Application)
    : AndroidViewModel(app) {

    fun runInScope(run: suspend () -> Unit): Job = viewModelScope.launch { run() }
}