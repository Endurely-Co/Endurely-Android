package dev.gbenga.endurely.dashboard

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import dev.gbenga.endurely.R
import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.core.Tokens
import dev.gbenga.endurely.core.UiState
import dev.gbenga.endurely.extensions.titleCase
import dev.gbenga.endurely.onboard.data.RepoState
import dev.gbenga.endurely.routines.DashboardPages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DashboardViewModel(private val dashboardRepository: DashboardRepository,
                         private val greeting: Greeting,
    private val savedStateHandle: SavedStateHandle) : EndureNavViewModel() {

    private val _dashboardUi = MutableStateFlow(DashboardUiState())
    val dashboardUi = _dashboardUi.asStateFlow()

    private val _signOut = MutableStateFlow<UiState<String>>(UiState.Idle<String>())
    val signOut = _signOut.asStateFlow()

    companion object{
        const val CUR_PAGE ="Current_Screen"
    }

    init {

        runInScope {

            dashboardRepository.getUser().let{ loginData ->
                if (loginData is RepoState.Success) {
                    _dashboardUi.update { it.copy(
                        fullName = UiState.Success(loginData.data.data.firstName.titleCase()),
                        userInitial = loginData.data.data.firstName.first().uppercase()
                    ) }
                }
            }


        }

        _dashboardUi.update {
            it.copy(dashboardMenus = listOf(
                DashboardMenu(title = Tokens.mealPlan,
                    bgColor = 0xFF5C6BC0.toInt(),
                    clipArt = R.drawable.meal_plan_ic)
            ), greeting = greeting.getGreeting(),
                pageTitles = listOf(Tokens.dashboard, Tokens.gymRoutine, Tokens.settings)
            )
        }

    }


    fun setCurrentPage(){
        showAddRoutine(savedStateHandle.get<Int>(CUR_PAGE) ?: 0)
    }

    fun showAddRoutine(page: Int){
        _dashboardUi.update { it.copy(showAddRoutine = page == DashboardPages.GYM_ROUTINE) }
        savedStateHandle[CUR_PAGE] = page
    }


}