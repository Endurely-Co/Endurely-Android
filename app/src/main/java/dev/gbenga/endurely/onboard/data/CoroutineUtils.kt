package dev.gbenga.endurely.onboard.data

import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


sealed interface RepoState<T>{
    data class Success<T>(val data: T) : RepoState<T>
    data class Error<T>(val errorMsg: String): RepoState<T>
}

suspend fun <RESULT> repoContext(coroutineContext: CoroutineContext,
                            block: suspend  () -> RESULT) : RepoState<RESULT> =  withContext(coroutineContext){
    try {
        RepoState.Success(block())
    }catch (error: Exception){
        error.printStackTrace()
        RepoState.Error((error.message ?: "App couldn't understand Api response."))
    }
}