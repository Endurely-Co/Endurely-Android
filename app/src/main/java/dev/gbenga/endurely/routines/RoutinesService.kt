package dev.gbenga.endurely.routines

import dev.gbenga.endurely.onboard.data.ApiEndpoint
import dev.gbenga.endurely.onboard.data.ApiResponse
import dev.gbenga.endurely.routines.data.AddRoutineRequest
import dev.gbenga.endurely.routines.data.AddRoutineResponse
import dev.gbenga.endurely.routines.data.DeleteRoutineResponse
import dev.gbenga.endurely.routines.data.EditRoutineRequest
import dev.gbenga.endurely.routines.data.EditRoutineResponse
import dev.gbenga.endurely.routines.data.ExercisesResponse
import dev.gbenga.endurely.routines.data.RoutineResponse
import dev.gbenga.endurely.routines.data.SuggestedExercisesResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @DELETE(ApiEndpoint.ROUTINES.plus("/{userId}"))
    suspend fun deleteRoutineById(@Path("userId") userId: Int,
                                  @Query("routine") routine: String): DeleteRoutineResponse

    @PUT(ApiEndpoint.ROUTINES.plus("/{userId}"))
    suspend fun editRoutine(@Path("userId") userId: Int,
                                  @Body request: EditRoutineRequest): EditRoutineResponse

    @POST(ApiEndpoint.ADD_ROUTINES)
    suspend fun addNewRoutine(@Body routine: AddRoutineRequest): AddRoutineResponse

//    @POST(ApiEndpoint.USER_ROUTINES)
//    suspend fun addNewRoutine(@Body routine: EditRoutineRequest): EditRoutineResponse

    //EditRoutineRequest
}