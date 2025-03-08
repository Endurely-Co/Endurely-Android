package dev.gbenga.endurely.routines

import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.routines.data.ExercisesData
import dev.gbenga.endurely.routines.data.ExercisesResponse

data class ExerciseSuggestionsState(val searchUi: UiState<List<ExercisesData>> = UiState.Idle(),
    val hideSuggestion: Boolean =false)