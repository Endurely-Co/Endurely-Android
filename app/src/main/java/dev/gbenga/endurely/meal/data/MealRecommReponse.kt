package dev.gbenga.endurely.meal.data

import com.google.gson.annotations.SerializedName

data class NutrientResponse(
    val data: NutrientData,
)

data class NutrientData(
    val user: Long,
    val nutrients: List<NutrientItem>,
)

data class NutrientItem(
    val item: String,
    val valid: Boolean,
    val macronutrients: List<Macronutrient>,
    val vitamins: List<Vitamin>,
    val minerals: List<Mineral>,
    @SerializedName("other_nutrients")
    val otherNutrients: String,
    val id: Long,
)

data class Macronutrient(
    val name: String,
    val summary: String,
)

data class Vitamin(
    val name: String,
    val summary: String,
)

data class Mineral(
    val name: String,
    val summary: String,
)
