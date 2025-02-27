package dev.gbenga.endurely.dashboard

import java.util.Calendar
import java.util.TimeZone

class Greeting {
    fun getGreeting(): String{
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        return calendar[Calendar.HOUR_OF_DAY].let { hour ->
            "Good ${when (hour) {
                in 0..< 12 -> {
                    return "morning"
                }
                in 12..<17 -> {
                    "afternoon"
                }
                else -> {
                    "evening"
                }
            }},"
        }
    }
}