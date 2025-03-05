package dev.gbenga.endurely.routines

import kotlin.time.Duration

data class ExerciseDetailsState(val title: String="",
                                val description: String="",
                                val duration: String="",
                           val show: Boolean = false)