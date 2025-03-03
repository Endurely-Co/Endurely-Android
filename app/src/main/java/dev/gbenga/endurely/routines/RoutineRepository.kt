package dev.gbenga.endurely.routines

import dev.gbenga.endurely.core.Repository
import dev.gbenga.endurely.core.data.UserDataStore
import kotlinx.coroutines.flow.first
import kotlin.coroutines.CoroutineContext

class RoutineRepository(private val routinesService: RoutinesService,
                        private val ioContext: CoroutineContext,
    private val userDataStore: UserDataStore
) : Repository(ioContext) {

    private suspend fun getUser() = userDataStore.login.first ().data

    suspend fun getUserRoutines() = repoContext(){
        routinesService.getRoutinesByUserId(getUser().userId).data
    }

    suspend fun getExercises() = repoContext {
        routinesService.getExercises()
    }

}