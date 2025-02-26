package dev.gbenga.endurely.onboard.data

import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


sealed interface RepoState<T>{
    data class Success<T>(val data: T) : RepoState<T>
    data class Error<T>(val errorMsg: String): RepoState<T>
}

suspend fun <T> repoContext(coroutineContext: CoroutineContext,
                            block: suspend  () -> ApiResponse<T>) : RepoState<T> =  withContext(coroutineContext){
    try {
        block().let { b ->
            b.apiSuccess?.data?.let {  RepoState.Success(it) }
                ?:  throw Exception(b.error?.message ?: "App couldn't understand Api response.")
        }
    }catch (error: Exception){
        RepoState.Error((error.message ?: "App couldn't understand Api response."))
    }
}