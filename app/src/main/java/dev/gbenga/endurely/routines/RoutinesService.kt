package dev.gbenga.endurely.routines

import dev.gbenga.endurely.onboard.data.ApiEndpoint
import dev.gbenga.endurely.routines.data.ExercisesResponse
import dev.gbenga.endurely.routines.data.RoutineResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RoutinesService {

    @GET("${ApiEndpoint.USER_ROUTINES}/{userId}")
    suspend fun getRoutinesByUserId(@Path("userId") userId: Int): RoutineResponse

    @GET("${ApiEndpoint.USER_ROUTINES}/{userId}")
    suspend fun getRoutinesById(@Path("userId") userId: Int,
                                @Query("routine") routine: String): RoutineResponse


    @GET(ApiEndpoint.EXERCISES)
    suspend fun getExercises(): ExercisesResponse

}