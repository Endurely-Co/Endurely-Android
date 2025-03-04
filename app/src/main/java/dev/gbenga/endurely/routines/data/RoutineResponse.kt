package dev.gbenga.endurely.routines.data

import android.util.Log
import com.google.gson.annotations.SerializedName

data class TimeVal(val hrs: Int, val mins: Int, val secs: Int, ){

    fun hrs(): String{
        if (hrs < 1){
            return ""
        }
        return "${if(hrs < 10) "0$hrs" else hrs.toString()} Hrs "
    }

    fun mins(): String{
        return "${if(mins < 10) "0$mins" else mins.toString()} Mins "
    }

    fun secs(): String{
        return "${if(secs < 10) "0$secs" else secs.toString()} Secs "
    }

}

data class RoutineResponse(
    val data: List<RoutineData>,
)

data class RoutineData(
    val user: Long,
    @SerializedName("exercises")
    val userExercises: List<UserExercise>,
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
) {
    fun routineRepsStr() = "$routineReps Reps"



    fun totalDuration(): String {
        var hrs = 0
        var mins = 0
        var secs = 0

        return userExercises.map { exercise ->
            val durations = exercise.duration.split(":").map { it.toInt() }

            hrs += durations[0]
            mins += durations[1]
            secs += durations[2]

            if (secs >= 60 ){
                mins += (secs / 60)
                secs = (secs % 60)
            }
            if (mins >= 60){
                hrs += (mins / 60)
                mins = (mins % 60)
            }

            TimeVal(hrs, mins, secs)
        }.last().let {
            "${it.hrs()}${it.mins()}${it.secs()}"
        }

    }


    fun getCompleted(): String{
        return "${userExercises.filter { it.completed }.size}/${userExercises.size}"
    }

    fun progress() = userExercises.filter { it.completed }.size / userExercises.size.toFloat()
}


data class UserExercise(
    val id: Long,
    val duration: String,
    val exercise: Exercise,
    val completed: Boolean
){

    val status = if (completed) {
        "Completed"
    }else{
        "In Progress"
    }
}

data class Exercise(
    val id: Long,
    val key: String,
    val name: String,
    val category: String,
)


fun String.duration(): String {
    val durations = split(":")
    var humanTime = ""
    if (durations[0] != "00") {
        humanTime = "${durations[0]} Hrs "
    }

    if (durations[1] != "00") {
        humanTime += "${durations[1]} Mins "
    }

    return humanTime
}