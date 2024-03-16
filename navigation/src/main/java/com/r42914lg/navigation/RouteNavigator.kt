package com.r42914lg.navigation

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface RouteNavigator {
    fun onNavigated(state: NavigationState)
    fun navigateUp()
    fun popToRoute(route: String)
    fun navigateToRoute(route: String)

    val navigationState: StateFlow<NavigationState>
}

class MyRouteNavigator : RouteNavigator {

    /**
     * TODO #1 - consider collection instead of single state to handle multiple subsequent navigation commands (TBD)
     */
    override val navigationState: MutableStateFlow<NavigationState> =
        MutableStateFlow(NavigationState.Idle)

    override fun onNavigated(state: NavigationState) {
        navigationState.compareAndSet(state, NavigationState.Idle)
    }

    override fun popToRoute(route: String) = navigate(NavigationState.PopToRoute(route))

    override fun navigateUp() = navigate(NavigationState.NavigateUp())


    override fun navigateToRoute(route: String) = navigate(NavigationState.NavigateToRoute(route))

    /**
     * TODO #2 - consider to implement also "navigate with result" (TBD)
     */
    @VisibleForTesting
    fun navigate(state: NavigationState) {
        navigationState.value = state
    }
}