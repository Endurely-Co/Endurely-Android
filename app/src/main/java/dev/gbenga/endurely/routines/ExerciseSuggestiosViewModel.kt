package dev.gbenga.endurely.routines

import android.util.Log
import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.onboard.data.RepoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class ExerciseSuggestionsViewModel(private val routineRepository: RoutineRepository) : EndureNavViewModel() {

    private val _searchExercises = MutableStateFlow(ExerciseSuggestionsState())
    val searchExercises = _searchExercises.asStateFlow()
    private val _selection = MutableStateFlow(0)
    private val _searchText = MutableStateFlow("")

    init {
        runInScope {
            _searchText.collect{ searchText ->
                Log.d("runInScope", "$searchText == ${_selection.value}")

                _searchExercises.update {
                    it.copy(searchUi = UiState.Loading())
                }
                runInScope {
                    when(val searched = routineRepository.searchExercises(searchText)){
                        is RepoState.Success ->{
                            _searchExercises.update { it.copy(
                                searchUi = UiState.Success(searched.data)) }
                        }
                        is RepoState.Error ->{
                            _searchExercises.update { it.copy(
                                searchUi = UiState.Failure(searched.errorMsg)) }
                        }
                    }
                }
            }
        }
    }


    fun closeSuggestionPanel(){
        _searchExercises.update { it.copy(searchUi = UiState.Idle()) }
        _selection.update { 0 }
    }



    fun search(exercise: String){
        if (exercise.isBlank()){
            closeSuggestionPanel()
            return
        }
        if (_selection.value >= 1){
            _searchText.update { exercise }
        }else{
            _selection.update { _selection.value + 1 }
        }
    }
}
