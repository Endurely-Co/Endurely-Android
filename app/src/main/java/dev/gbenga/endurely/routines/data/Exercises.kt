package dev.gbenga.endurely.routines.data



data class ExercisesResponse(
    val data: List<ExercisesData>,
)

data class ExercisesData(
    val id: Long,
    val key: String,
    val name: String,
    val category: String,
)