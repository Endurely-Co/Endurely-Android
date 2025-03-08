package dev.gbenga.endurely.routines

import android.util.Log
import dev.gbenga.endurely.core.DateUtilsNames
import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.core.Tokens
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.dashboard.SettingsRepository
import dev.gbenga.endurely.onboard.data.RepoState
import dev.gbenga.endurely.routines.data.RoutineData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RoutinesViewModel(private val routineRepository : RoutineRepository,
                        private val dayNameUtil: DateUtilsNames,
                        private val settingsRepository: SettingsRepository) : EndureNavViewModel(){

    private val _routinesUi = MutableStateFlow(RoutineUiState())
    val routinesUi = _routinesUi.asStateFlow()

    private val _preservedList: ArrayList<Pair<String, RoutineData>> = ArrayList()
    private val _dayAndRoutine = MutableStateFlow<List<Pair<String, RoutineData>>>(emptyList())

    init {
        getDaysOfWeek()
        getRoutinesByUserId()
       runInScope {
           settingsRepository.theme().collect{ isDarkMode ->
               _routinesUi.update { it.copy(isDarkMode = isDarkMode) }
           }
       }
        runInScope {
            _dayAndRoutine.collect{ routines ->
                _routinesUi.update { it.copy(routines =
                UiState.Success(routines.map { pair -> pair.second }),) }
            }
        }
    }

    fun selectDay(selectedDay: String=""){
        val dayOfWeek = selectedDay.ifBlank { dayNameUtil.getToday() }.lowercase()
        val selectedIndex = Tokens.lowercaseDaysOfWeek.indexOf(dayOfWeek)
        Log.d("selectDay", "$selectedIndex")
        // Update selected date
        _routinesUi.update { it.copy( days = it.days.map { day ->
            day.copy(selected = day.name.lowercase() == dayOfWeek,
                selectedIndex = selectedIndex) }
        )}
        val cloned = ArrayList<Pair<String, RoutineData>>(_preservedList)
        // Update list
        _dayAndRoutine.update { cloned.filter {
            it.first == selectedDay.lowercase() } }
    }


    private fun getDaysOfWeek(){
        _routinesUi.update { it.copy(routines = UiState.Loading()) }
        _routinesUi.update { it.copy(days = Tokens.daysOfWeek.mapIndexed { index, day ->
            DayOfWeek(name = day, index ==-1,)
        }) }
    }

    fun getRoutinesByUserId(){
        runInScope {
            when(val routines =routineRepository.getUserRoutines()){
                is RepoState.Success ->{
                    _preservedList.clear()
                    _preservedList.addAll(routines.data.data.map {
                        dayNameUtil.getServerDay(it.startDate).lowercase() to it
                    })
                    selectDay()
                }
                is RepoState.Error ->{
                    _routinesUi.update { it.copy(routines = UiState.Failure(routines.errorMsg)) }
                }
            }
        }
    }

    override fun clearState() {
        _routinesUi.update { RoutineUiState() }
    }
}
