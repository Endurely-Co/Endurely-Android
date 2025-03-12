package dev.gbenga.endurely.meal

import android.util.Log
import dev.gbenga.endurely.core.Repository
import dev.gbenga.endurely.core.data.UserDataStore
import dev.gbenga.endurely.meal.data.MealPlanRequest
import dev.gbenga.endurely.meal.data.NutrientFromMealRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlin.coroutines.CoroutineContext

class MealPlanRepository(private val mealService: MealService,
                         private val userDataStore: UserDataStore,
                         ioContext: CoroutineContext = Dispatchers.IO) : Repository(ioContext){

    private suspend fun getUser() = userDataStore.login.first ().data

    private suspend fun getUserId() = getUser().userId

    suspend fun requestFoodNutrients(nutrients: NutrientFromMealRequest) = repoContext {
        mealService.requestFoodNutrients(nutrients.copy(user = getUserId()))
    }

    suspend fun planNewMeal(request: MealPlanRequest) = repoContext {
        Log.d("request_request", "request -> $request")
        mealService.planMeal(request.copy(user = getUserId()))
    }

    suspend fun getMealPlanForUser() = repoContext {
        mealService.getMealPlanForUser(getUserId())
    }

    suspend fun getMealPlanForUserById(planId: String) = repoContext {
        mealService.getMealPlanForUserById(getUserId(), planId.toInt())
    }
}