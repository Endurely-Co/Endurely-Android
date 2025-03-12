package dev.gbenga.endurely.meal.data

import com.google.gson.annotations.SerializedName

data class MealPlanDetailsResponse(
    val data: List<MealPlanDetailsData>,
)

data class MealPlanDetailsData(
    val id: Long,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("food_name")
    val foodName: String,
    @SerializedName("meal_plan_id")
    val mealPlanId: String,
    @SerializedName("meal_date_time")
    val mealDateTime: String,
    val user: Long,
    @SerializedName("food_item")
    val foodItem: Long,
    //val otherNutrients: String,
    val nutrients: Nutrients,
)

data class Nutrients(
    val item: String,
    val valid: Boolean,
    val macronutrients: List<Macronutrient>,
    val vitamins: List<Vitamin>,
    val minerals: List<Mineral>,
    @SerializedName("other_nutrients")
    val otherNutrients: String,
    val id: Long,
)

