package dev.gbenga.endurely.meal

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import dev.gbenga.endurely.core.DateTimeUtils
import dev.gbenga.endurely.core.DateUtils
import dev.gbenga.endurely.core.DateUtilsNames
import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.core.Tokens
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.meal.data.GetMealPlan
import dev.gbenga.endurely.meal.data.MealPlan
import dev.gbenga.endurely.meal.data.MealPlanRequest
import dev.gbenga.endurely.meal.data.MessageSharedPref
import dev.gbenga.endurely.meal.data.NutrientFromMealRequest
import dev.gbenga.endurely.meal.data.NutrientItem
import dev.gbenga.endurely.onboard.data.RepoState
import dev.gbenga.endurely.routines.DayOfWeek
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MealPlanViewModel(private val mealPlanRepository: MealPlanRepository,
                        private val dayNameUtil: DateUtilsNames,
                        private val dateTimeUtil: DateTimeUtils,
                        private val messageSharedPref: MessageSharedPref,
                        private val savedStateHandle: SavedStateHandle
) : EndureNavViewModel() {

    private var _preservedList: List<Pair<String, GetMealPlan>>? = null
    private val _mealPlanUi = MutableStateFlow(MealPlanUiState())

    private val _message = MutableSharedFlow<MessageData>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val message = _message.asSharedFlow()

    private var nutrients: List<NutrientItem>? = null

    val mealPlanUi = _mealPlanUi.asStateFlow()

    companion object{

        private const val DAY_OF_WEEK: String ="days_OfWeek"
    }

    init {
        _mealPlanUi.update { it.copy(days = Tokens
            .daysOfWeek.map { day ->
            DayOfWeek(day)
        }, colors = listOf(0xFFE53935,0xff2E7D32,0xFFFFA000, 0xFFE0E0E0)
        ) }

    }

    fun deliverUiMessage(){
        runInScope {
            messageSharedPref.getMessage()?.let {
                _message.emit(MessageData(feedbackMsg = it))
                messageSharedPref.clear()
            }
        }

//        runInScope {
//            messageSharedPref.isReload().let {
//                _message.emit(MessageData(reload = it))
//                messageSharedPref.clear()
//            }
//        }

    }

    fun selectDay(selectedDay: String=""){
        val dayOfWeek = selectedDay.ifBlank { dayNameUtil.getToday() }.lowercase()
        savedStateHandle[DAY_OF_WEEK] = dayOfWeek
        val selectedIndex = Tokens.lowercaseDaysOfWeek.indexOf(dayOfWeek)
        Log.d("selectDay", "${savedStateHandle.get<String>(DAY_OF_WEEK)}")
        // Update selected date
        _mealPlanUi.update { it.copy( days = it.days.map { day ->
            day.copy(selected = day.name.lowercase() == dayOfWeek,
                selectedIndex = selectedIndex) }
        )}

        _preservedList?.let {pL ->
            val filtered = pL.filter {
                it.first == dayOfWeek.lowercase() }.map {
                    pair -> pair.second }
            _mealPlanUi.update {
                it.copy(mealPlan =
                UiState.Success(filtered),)
            }
        }
    }

    fun planMeal(mealDateMillis: Long){
        _mealPlanUi.update { it.copy(plannedMeal = UiState.Loading()) }
        runInScope {
            nutrients?.let { nuts ->
                when(val plannedMeal = mealPlanRepository.planNewMeal(
                    MealPlanRequest( mealPlans=nuts.map { nutrient ->
                        MealPlan(meal=nutrient.item,
                            foodItemId = nutrient.id)
                    }, mealDateTime = dateTimeUtil.getServerDate(mealDateMillis)))){
                    is RepoState.Success ->{
                        _mealPlanUi.update { it.copy(plannedMeal = UiState.Success(plannedMeal.data)) }
                        _message.emit(MessageData(reload = true))
                    }
                    is RepoState.Error ->{
                        _mealPlanUi.update { it.copy(plannedMeal = UiState.Failure(plannedMeal.errorMsg)) }
                    }
                }
            }

        }
    }

    fun getMealPlanByUserId(){
        Log.d("RepoState", "RepoState - RepoState - RepoState")
        _mealPlanUi.update { it.copy(mealPlan = UiState.Loading()) }
        runInScope {
            when(val mealPlan = mealPlanRepository.getMealPlanForUser()){
                is RepoState.Success ->{
                    _preservedList = mealPlan.data.data.map {
                        dayNameUtil.getServerDay(it.mealDateTime).lowercase() to it
                    }
                    val savedWeek = savedStateHandle.get<String>(DAY_OF_WEEK)
                    savedWeek?.let { selectDay(it) } ?: selectDay()
                }
                is RepoState.Error ->{
                    _mealPlanUi.update { it.copy(mealPlan = UiState.Failure(mealPlan.errorMsg)) }
                }
            }
        }
    }


    fun requestFoodNutrients(meal: String){
        _mealPlanUi.update { it.copy(mealNutrients = UiState.Loading()) }
       runInScope {
           when(val nutrients = mealPlanRepository
               .requestFoodNutrients(NutrientFromMealRequest(meal = meal))){
               is RepoState.Success ->{
                   this@MealPlanViewModel.nutrients = nutrients.data.data.nutrients.apply {
                       _mealPlanUi.update { it.copy(
                           mealNutrients = UiState.Success(this),
                           button = ButtonAction.AddMeal())
                       }
                   }
               }
               is RepoState.Error ->{
                   _mealPlanUi.update { it.copy(mealNutrients = UiState.Failure(nutrients.errorMsg)) }
               }
           }
       }
    }

    override fun clearState() {
        _mealPlanUi.update { it.copy(
            mealNutrients = UiState.Idle(),
            plannedMeal =UiState.Idle(),
        )}
    }
}