package dev.gbenga.endurely.main

import dev.gbenga.endurely.navigation.Dashboard
import dev.gbenga.endurely.navigation.Screens

data class BbRouteState(val bottomBarItems: List<EndurelyBottomBar> = emptyList())

data class EndurelyBottomBar(val icon: Int =-1, val name: String ="",
                             val route: Screens = Dashboard)