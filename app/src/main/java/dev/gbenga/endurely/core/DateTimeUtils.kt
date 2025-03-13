package dev.gbenga.endurely.core

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateTimeUtils(private val dateFormat: DateFormat = SimpleDateFormat.getDateInstance(),
                    private val serverDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'",
                        Locale.getDefault()),
    private val timeFormat: DateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault()),
    private val calendar : Calendar= Calendar.getInstance()) {

    private var dateInMillis: Long= 0L

    fun getDate(dateInMillis: Long): String {
        this.dateInMillis = dateInMillis
        return dateFormat.format(Date(dateInMillis)) ?: ""
    }

    fun getDateMillis(): Long = dateInMillis

    fun parseDateTime(hour: Int,minute: Int, date: Long) = calendar.apply {
        timeInMillis = date
        this[Calendar.MINUTE] = minute
        this[Calendar.HOUR_OF_DAY] = hour
    }.timeInMillis

    fun getServerTime(hour: Int,minute: Int, date: Long): String{
        return serverDateFormat.format(Date(parseDateTime(hour, minute, date)))
    }

    fun getServerDate(dateInMillis: Long): String{
        return serverDateFormat.format(dateInMillis)
    }

    fun getTime(timeInMillis: Long): String {
        return timeFormat.format(Date(timeInMillis))
    }

    fun getTime(timeStr: String): Pair<Int, Int>{
        val time = timeFormat.parse(timeStr)
        val hourMin = Pair(0, 0)
        return time?.let {
            calendar.time = it
            hourMin.copy(calendar[Calendar.MINUTE], calendar[Calendar.MINUTE])
        } ?: hourMin
    }


    fun getServerTime(timeStr: String, dateStr: String): String{
        val date = dateFormat.parse(dateStr) ?: Date()
        val (hour, minute) = getTime(timeStr)
//        calendar.time = date
//        calendar[Calendar.MINUTE] = minute
//        calendar[Calendar.HOUR_OF_DAY] = hour
        return getServerTime(hour, minute, date.time)
    }

    //dateFormat


    fun getTime(minute: Int, hour: Int): String = calendar.let {
        Log.d("TimeUtils", "$minute $hour")
        it[Calendar.MINUTE] = minute
        it[Calendar.HOUR_OF_DAY] = hour
        return getTime(it.timeInMillis)
    }


    fun serverDuration(duration: String): String {
       // val timeFormat: DateFormat = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
        val durationSegs = duration.split(":")
        return if (durationSegs.size > 2) duration else  duration.plus(":00")
    }

}

class DateUtils( private val serverDateFormat:
                 DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",
        Locale.getDefault()),
                 private val calendar : Calendar= Calendar.getInstance(),
                 private val dateTimeUtils: DateTimeUtils = DateTimeUtils()){
    fun reverseServerTime(timeDate: String): String{
        val validSeg = timeDate.split(".")
        return serverDateFormat.parse(validSeg[0]).let {
            it?.let { date -> dateTimeUtils.getTime(date.time)  } ?: ""
        }
    }

    fun reverseServerDate(timeDate: String): String{
        val validSeg = timeDate.split(".")
        return serverDateFormat.parse(validSeg[0]).let {
            it?.let { date -> dateTimeUtils.getDate(date.time)  } ?: ""
        }
    }

    fun parse(timeDate: String): Date?{
        val validSeg = timeDate.split(".")
        return serverDateFormat.parse(validSeg[0])
    }
}

class DateUtilsNames( private val dateUtils: DateUtils,
                      private val dayNameFormat:
                      DateFormat = SimpleDateFormat("EEEE",
                          Locale.getDefault())){

    fun getServerDay(timeDate: String)
    = dateUtils.parse(timeDate) ?.let {
        dayNameFormat.format(it)
    } ?: ""

    fun getToday(): String {
        return dayNameFormat.format(System.currentTimeMillis())
    }
}


@Composable
fun rememberDateUtils(): DateUtils = remember { DateUtils() }

@Composable
fun rememberDateTimeUtils(): DateTimeUtils{
    return remember { DateTimeUtils() }
}