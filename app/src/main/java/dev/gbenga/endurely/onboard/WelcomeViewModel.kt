package dev.gbenga.endurely.onboard

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.SavedStateHandle
import dev.gbenga.endurely.R
import dev.gbenga.endurely.core.EndureNavAppViewModel
import dev.gbenga.endurely.core.EndureNavViewModel
import dev.gbenga.endurely.ui.theme.DyColor
import dev.gbenga.endurely.ui.theme.Maroon
import dev.gbenga.endurely.ui.theme.Orange
import dev.gbenga.endurely.ui.theme.Purple
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WelcomeViewModel(savedStateHandle: SavedStateHandle, private val app: Application)
    : EndureNavAppViewModel(savedStateHandle, app) {
    private val _welcomeUiState = MutableStateFlow(WelcomeUiState())
    val welcomeUiState = _welcomeUiState.asStateFlow()

    init {
        _welcomeUiState.update {
            it.copy(welcomeContent = listOf(
                WelcomeContent(
                    title = "Workout Anywhere",
                    clipArt = R.drawable.fitness_girl2,
                    colorInt = Maroon.toInt(),
                    description = "Choose your preferred location and do your workouts anytime that suits you"
            ), WelcomeContent(
                    title = "Meet Your Goals",
                    clipArt = R.drawable.gym_guy,
                    colorInt = Purple.toInt(),
                    description = "Choose your preferred location and do your workouts anytime that suits you"
                )))
        }
    }

    fun changePage(pos: Int){
        runInScope {
            _welcomeUiState.update { it.copy(backgroundColor = Color.MAGENTA)
            }
        }
    }
}