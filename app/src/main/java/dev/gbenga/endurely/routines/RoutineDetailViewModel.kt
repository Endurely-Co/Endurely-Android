package dev.gbenga.endurely.routines

import androidx.lifecycle.SavedStateHandle
import dev.gbenga.endurely.core.EndureNavViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RoutineDetailViewModel(val savedStateHandle: SavedStateHandle) :EndureNavViewModel() {

    private val _routineDetail = MutableStateFlow(RoutineDetailUiState())
    val routineDetail = _routineDetail.asStateFlow()

}