package dev.gbenga.endurely.meal.data

import com.google.gson.annotations.SerializedName

data class GetMealPlanResponse(
    val data: List<GetMealPlan>,
)

data class GetMealPlan(
    val id: Long,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("food_name")
    val foodName: String,
    @SerializedName("meal_plan_id")
    val mealPlanId: String,
    val user: Long,
    @SerializedName("food_item")
    val foodItem: Long,
    @SerializedName("other_nutrients")
    val otherNutrients: String,
    @SerializedName("meal_date_time")
    val mealDateTime: String,
)
