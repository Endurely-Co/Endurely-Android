package dev.gbenga.endurely.core

import dev.gbenga.endurely.onboard.data.RepoState
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class Repository(private val ioContext: CoroutineContext) {

    suspend fun <RESULT> repoContext(coroutineContext: CoroutineContext =ioContext,
                                     block: suspend  () -> RESULT) : RepoState<RESULT> =  withContext(coroutineContext){
        try {
            RepoState.Success(block())
        }catch (error: Exception){
            error.printStackTrace()
            RepoState.Error((error.message ?: "App couldn't understand Api response."))
        }
    }
}