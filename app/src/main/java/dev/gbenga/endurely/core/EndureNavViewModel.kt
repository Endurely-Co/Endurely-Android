package dev.gbenga.endurely.core

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gbenga.endurely.onboard.data.RepoState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


abstract class NewEndureNavViewModel<T>() : EndureNavViewModel() {
    abstract val uiField: T
}

abstract class EndureNavViewModel( savedStateHandle: SavedStateHandle? = null) : ViewModel() {

    inline fun runInScope(crossinline run: suspend CoroutineScope. () -> Unit): Job = viewModelScope.launch { run() }


    protected suspend fun <T> repoToVMState(block: suspend () -> RepoState<T>): UiState<T>{
        return when(val userRoutines = block()){
            is RepoState.Success ->{
                UiState.Success(userRoutines.data)
            }
            is RepoState.Error ->{
                UiState.Failure(userRoutines.errorMsg)
            }

        }
    }

    protected fun <T> repoToVMState(userRoutines: RepoState<T>): UiState<T>{
        return when(userRoutines){
            is RepoState.Success ->{
                UiState.Success(userRoutines.data)
            }
            is RepoState.Error ->{
                UiState.Failure(userRoutines.errorMsg)
            }

        }
    }

    open fun clearState(){}
}

abstract class EndureNavAppViewModel(private val savedStateHandle: SavedStateHandle, app: Application)
    : AndroidViewModel(app) {

    fun runInScope(run: suspend () -> Unit): Job = viewModelScope.launch { run() }
}