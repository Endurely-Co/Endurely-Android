package dev.gbenga.endurely.main

import dev.gbenga.endurely.R
import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.core.Tokens
import dev.gbenga.endurely.navigation.Dashboard
import dev.gbenga.endurely.navigation.GymRoutine
import dev.gbenga.endurely.navigation.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EndurelyBottomBarViewModel : EndureNavViewModel() {

    private val _bbUiState = MutableStateFlow(BbRouteState())
    val bbUiState = _bbUiState.asStateFlow()

    init {
        _bbUiState.update {
            it.copy(bottomBarItems = listOf(
                EndurelyBottomBar(icon =R.drawable.dashboard_ic,
                    name = Tokens.dashboard, route = Dashboard),
                EndurelyBottomBar(icon =R.drawable.triceps,
                    name = Tokens.gymRoutine, route = GymRoutine
                ),
                EndurelyBottomBar(icon =R.drawable.settings_ic,
                    name = Tokens.settings, route = Settings
                )
            ))
        }
    }
}