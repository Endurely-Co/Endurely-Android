package dev.gbenga.endurely.routines.data

data class SuggestedExercisesResponse (
    val data: List<ExerciseData>, )

data class ExerciseData(
    val id: Long,
    val key: String,
    val name: String,
    val description: String,
    val category: String,
)