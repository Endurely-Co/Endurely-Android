package dev.gbenga.endurely.meal.data

data class NutrientFromMealRequest(
    val meal: String,
    val user: Int=0,
)
