package dev.gbenga.endurely.routines

import dev.gbenga.endurely.core.UiState
import kotlin.time.Duration

data class ExerciseDetailsState(val title: String="",
                                val description: String="",
                                val duration: String="",
                           val show: Boolean = false,
    val markComplete: UiState<String> = UiState.Idle())