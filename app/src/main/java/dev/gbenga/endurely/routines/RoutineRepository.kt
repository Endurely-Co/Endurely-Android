package dev.gbenga.endurely.routines

import android.util.Log
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
        routinesService.getRoutinesByUserId(getUser().userId)
    }

    suspend fun getUserRoutineById(routineId: String) = repoContext(){

        Log.d("RepoState_RepoState", "$routineId")
        routinesService.getRoutinesById(getUser().userId, routineId)
    }

    suspend fun getExercises() = repoContext {
        routinesService.getExercises()
    }

    suspend fun deleteRoutine() = repoContext {

    }

}