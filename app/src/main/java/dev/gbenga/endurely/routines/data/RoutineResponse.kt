package dev.gbenga.endurely.routines.data

import com.google.gson.annotations.SerializedName


data class RoutineResponse(
    val data: List<RoutineData>,
)

data class RoutineData(
    val user: Long,
    val exercise: Long,
    @SerializedName("routine_name")
    val routineName: String,
    @SerializedName("routine_set")
    val routineSet: Long,
    @SerializedName("routine_reps")
    val routineReps: Long,
    @SerializedName("routine_duration")
    val routineDuration: String,
    val completed: Boolean,
    @SerializedName("created_at")
    val createdAt: String,
){
    fun routineRepsStr() ="$routineReps Reps"
    fun routineSetStr() ="Sets: $routineSet"

    fun duration() : String {
        val durations = routineDuration.split(":")
        var humanTime = ""
        if (durations[0] !="00"){
            humanTime = "${durations[0]} Hrs "
        }

        if (durations[1] !="00"){
            humanTime += "${durations[1]} Mins "
        }

        return humanTime
    }
}
