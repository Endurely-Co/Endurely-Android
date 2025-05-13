package dev.gbenga.endurely.meal

import dev.gbenga.endurely.meal.data.DeleteMealPlanResponse
import dev.gbenga.endurely.meal.data.GetMealPlanResponse
import dev.gbenga.endurely.meal.data.MealPlanDetailsResponse
import dev.gbenga.endurely.meal.data.MealPlanRequest
import dev.gbenga.endurely.meal.data.MealPlanResponse
import dev.gbenga.endurely.meal.data.NutrientFromMealRequest
import dev.gbenga.endurely.meal.data.NutrientResponse
import dev.gbenga.endurely.onboard.data.ApiEndpoint
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MealService {

//MealRecommReponse

    @POST(ApiEndpoint.MEAL_NUTRIENT)
    suspend fun requestFoodNutrients(@Body nutrients: NutrientFromMealRequest): NutrientResponse

    @POST(ApiEndpoint.MEAL_PLAN)
    suspend fun planMeal(@Body request: MealPlanRequest): MealPlanResponse

    @GET(ApiEndpoint.MEAL_PLAN.plus("/{userId}"))
    suspend fun getMealPlanForUser(@Path("userId") userId: Int): GetMealPlanResponse

    @GET(ApiEndpoint.MEAL_PLAN.plus("/{userId}"))
    suspend fun getMealPlanForUserById(@Path("userId") userId: Int,
                                       @Query("plan_id") planId: Int): MealPlanDetailsResponse

    @DELETE(ApiEndpoint.MEAL_PLAN.plus("/{userId}"))
    suspend fun deleteMealPlan(@Path("userId") userId: Int,
                       @Query("plan_id") planId: Int): DeleteMealPlanResponse

    //http://127.0.0.1:8000/meal/plan/20?plan_id=4142
    //GetMealPlanResponse
}