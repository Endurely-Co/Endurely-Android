package dev.gbenga.endurely

import dev.gbenga.endurely.navigation.Screens
import dev.gbenga.endurely.navigation.Welcome

data class MainActivityState (val startDestination: Screens = Welcome, val isDarkMode: Boolean? = null)