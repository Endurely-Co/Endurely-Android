package dev.gbenga.endurely.routines

import dev.gbenga.endurely.onboard.data.ApiEndpoint
import dev.gbenga.endurely.routines.data.ExercisesResponse
import dev.gbenga.endurely.routines.data.RoutineResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RoutinesService {

    @GET("${ApiEndpoint.USER_ROUTINES}/{userId}")
    suspend fun getRoutinesByUserId(@Path("userId") userId: Int): RoutineResponse

    @GET(ApiEndpoint.EXERCISES)
    suspend fun getExercises(): ExercisesResponse

}