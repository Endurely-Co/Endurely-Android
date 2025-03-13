package dev.gbenga.endurely.routines.data

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

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
    val id: Long= 0,
    val userExerciseId: Long? =null,
    val completed: Boolean = false,
)

data class NewExerciseName(val name: String, val exercise: NewExercise)

@Serializable data class AddRoutineResponse(    val data: Data,
)

@Serializable  data class Data(
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

@Serializable  data class ExerciseResponse(
    val id: Long,
    val duration: String,
    val completed: Boolean,
    val exercise: Exercise2,
)

@Serializable data class Exercise2(
    val id: Long,
    val key: String,
    val name: String,
    val description: String,
    val category: String,
)