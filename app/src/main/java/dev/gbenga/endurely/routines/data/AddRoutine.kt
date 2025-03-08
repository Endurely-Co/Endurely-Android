package dev.gbenga.endurely.routines.data

import com.google.gson.annotations.SerializedName

data class AddRoutineRequest(
    val user: Int =0,
    val exercises: List<NewExercise>,
    @SerializedName("routine_name")
    val routineName: String,
    @SerializedName("start_date")
    val startDate: String,
    val completed: Boolean =false,
)

data class NewExercise(
    val duration: String,
    val id: Long,
)

data class NewExerciseName(val name: String, val exercise: NewExercise)

data class AddRoutineResponse(    val data: Data,
)

data class Data(
    val user: Long,
    val exercises: List<ExerciseResponse>,
    @SerializedName("routine_name")
    val routineName: String,
    @SerializedName("routine_set")
    val routineSet: Long,
    @SerializedName("routine_reps")
    val routineReps: Long,
    @SerializedName("routine_duration")
    val routineDuration: String,
    val completed: Boolean,
    @SerializedName("routine_id")
    val routineId: String,
)

data class ExerciseResponse(
    val id: Long,
    val duration: String,
    val completed: Boolean,
    val exercise: Exercise2,
)

data class Exercise2(
    val id: Long,
    val key: String,
    val name: String,
    val description: String,
    val category: String,
)