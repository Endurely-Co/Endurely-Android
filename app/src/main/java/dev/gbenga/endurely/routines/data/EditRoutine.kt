package dev.gbenga.endurely.routines.data

import com.google.gson.annotations.SerializedName

data class EditRoutineRequest(val user: Int,
                       val exercises: List<RoutineExercise>,
                       @SerializedName("routine_name")
                       val routineName: String,
                       @SerializedName("routine_id")
                       val routineId: String,
)

data class RoutineExercise(
    @SerializedName("user_exercise_id")
    val userExerciseId: Long,
    val duration: String,
    val id: Long,
    val completed: Boolean,
)





// EDIT RESPONSE

data class EditRoutineResponse(
    val data: EditRoutineData,
)

data class EditRoutineData(
    val user: Long,
    val exercise: Long,
    @SerializedName("routine_name")
    val routineName: String,
    val completed: Boolean,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("routine_id")
    val routineId: String,
    val exercises: List<EditExercise>,
)

data class EditExercise(
    @SerializedName("id") val id: Int,
    @SerializedName("duration") val duration: String,
    @SerializedName("completed") val completed: Boolean,
    @SerializedName("exercise") val exercise: Int
)



//data class Exercise(
//    val id: Long,
//    val duration: String,
//    val completed: Boolean,
//    val exercise: Long,
//)
