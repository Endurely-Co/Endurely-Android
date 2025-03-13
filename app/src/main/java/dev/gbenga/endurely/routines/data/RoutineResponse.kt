package dev.gbenga.endurely.routines.data

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

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

@Serializable data class RoutineData(
    val user: Long=0,
    @SerializedName("routine_name")
    val routineName: String="",
    val completed: Boolean = false,
    @SerializedName("start_date")
    val startDate: String="",
    @SerializedName("created_at")
    val createdAt: String="",
    @SerializedName("routine_id")
    val routineId: String="",
    val exercises: List<UserExercise> = emptyList(),
) {

    fun toRequest(exercises: List<UserExercise>): EditRoutineRequest{
        return EditRoutineRequest(
            routineName = this.routineName,
            routineId = this.routineId,
            exercises = exercises.map { exercise ->
                RoutineExercise(
                    duration = exercise.duration,
                    completed = exercise.completed,
                    id = exercise.exercise.id,
                    userExerciseId = exercise.id
                    )
            },
            user = 0
        )
    }

    fun totalDuration(): String {
        var hrs = 0
        var mins = 0
        var secs = 0

        return exercises.map { exercise ->
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
            "${it.hrs()}${it.mins()}"
        }

    }


    fun getCompleted(): String{
        return "${exercises.filter { it.completed }.size}/${exercises.size}"
    }

    fun progress() = exercises.filter { it.completed }.size / exercises.size.toFloat()
}


@Serializable data class UserExercise(
    val id: Long,
    val duration: String,
    val exercise: Exercise,
    val completed: Boolean
){

    fun status() = if (completed) {
        "Completed"
    }else{
        "In Progress"
    }
}



@Serializable data class Exercise(
    val id: Long,
    val key: String,
    val name: String,
    val category: String,
    val description: String
)


fun String.duration(): String {
    if (this.isEmpty()) return ""
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

