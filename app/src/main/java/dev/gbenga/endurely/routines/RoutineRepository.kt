package dev.gbenga.endurely.routines

import android.util.Log
import com.google.gson.Gson
import dev.gbenga.endurely.core.Repository
import dev.gbenga.endurely.core.data.UserDataStore
import dev.gbenga.endurely.routines.data.AddRoutineRequest
import dev.gbenga.endurely.routines.data.AddRoutineResponse
import dev.gbenga.endurely.routines.data.EditRoutineRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlin.coroutines.CoroutineContext

class RoutineRepository(private val routinesService: RoutinesService,
                        private val ioContext: CoroutineContext,
    private val userDataStore: UserDataStore
) : Repository(ioContext) {

    private suspend fun getUser() = userDataStore.login.first ().data

    private suspend fun getUserId() = getUser().userId

    suspend fun getUserRoutines() = repoContext(){
        routinesService.getRoutinesByUserId(getUser().userId)
    }

    suspend fun getUserRoutineById(routineId: String) = repoContext(){

        Log.d("RepoState_RepoState", "$routineId")
        routinesService.getRoutinesById(getUser().userId, routineId)
    }

    suspend fun searchExercises(exercise: String) = repoContext {
        delay(500)
        if (exercise.isEmpty()){
            emptyList()
        }else{
            routinesService.getExercises().data.filter { it.name
                .lowercase().contains(exercise.lowercase()) }
        }

    }

    suspend fun deleteRoutine(routineId: String) = repoContext {
        routinesService.deleteRoutineById(getUser().userId, routineId)
    }

    suspend fun addRoutine(routine: AddRoutineRequest) = repoContext {
        routinesService.addNewRoutine(routine.copy(user = getUser().userId))
    }

    suspend fun editRoutine(routine: EditRoutineRequest) = repoContext {
        Log.d("addRoutine", "routine: ${Gson().toJson(routine)}")
        routinesService.editRoutine(getUser().userId, routine.copy(user = getUser().userId))
    }

}