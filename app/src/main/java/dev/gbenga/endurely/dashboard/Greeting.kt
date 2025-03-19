package dev.gbenga.endurely.dashboard

import java.util.Calendar
import java.util.TimeZone

class Greeting {
    fun getGreeting(): String{
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        return calendar[Calendar.HOUR_OF_DAY].let { hour ->
            "Good ${when (hour) {
                in 0..< 12 -> {
                    "morning \uD83C\uDF05"
                }
                in 12..<17 -> {
                    "afternoon \uD83C\uDF07"
                }
                else -> {
                    "evening \uD83C\uDF05"
                }
            }},"
        }
    }
}