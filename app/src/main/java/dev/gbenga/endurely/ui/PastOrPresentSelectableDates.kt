package dev.gbenga.endurely.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
object PastOrPresentSelectableDates: SelectableDates {
    @ExperimentalMaterial3Api
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis >= System.currentTimeMillis()
    }

    override fun isSelectableYear(year: Int): Boolean {
        return year >= LocalDate.now().year
    }
}