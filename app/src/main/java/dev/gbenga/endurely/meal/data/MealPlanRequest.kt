package dev.gbenga.endurely.meal.data

import com.google.gson.annotations.SerializedName

data class MealPlanRequest(
    val user: Int =0,
    @SerializedName("meal_date_time")
    val mealDateTime: String,
    @SerializedName("meal_plans")
    val mealPlans: List<MealPlan>,
)

data class MealPlan(
    val meal: String,
    @SerializedName("food_item_id")
    val foodItemId: Long,
)

data class MealPlanResponse(
    val data: MealPlanData,
)

data class MealPlanData(
    val message: String,
)


//data class Macronutrient(
//    val name: String,
//    val summary: String,
//)
//
//data class Vitamin(
//    val name: String,
//    val summary: String,
//)
//
//data class Mineral(
//    val name: String,
//    val summary: String,
//)
