package dev.gbenga.endurely.routines.data

import com.google.gson.annotations.SerializedName

data class RoutineCategory(
    val data: List<RoutineCategoryData>,
)

data class RoutineCategoryData(
    val category: String,
    @SerializedName("category_name")
    val categoryName: String,
)
