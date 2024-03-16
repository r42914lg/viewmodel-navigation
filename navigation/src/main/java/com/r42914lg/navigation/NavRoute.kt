package com.r42914lg.navigation

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.koin.core.parameter.parametersOf

abstract class NavRoute<T : RouteNavigator> {

    protected var vmParams = parametersOf()

    @Composable
    abstract fun Content(viewModel: T)

    @Composable
    abstract fun viewModel(): T

    abstract fun getRoute(vararg params: Any): String
    protected open fun getArguments(): List<NamedNavArgument> = listOf()

    protected abstract val route: String

    fun composable(
        builder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        builder.composable(
            route = route,
            arguments = getArguments(),
        ) {
            vmParams = parametersOf()
            getArguments().forEach { navArg ->
                // TODO - distinguish between mandatory & optional arguments
                val arg = it.arguments?.getString(navArg.name) ?: throw IllegalStateException()
                vmParams.add(arg)
            }

            val viewModel = viewModel()
            val viewStateAsState by viewModel.navigationState.collectAsState()

            LaunchedEffect(viewStateAsState) {
                Log.d("Nav", "${this@NavRoute} updateNavigationState to $viewStateAsState")
                updateNavigationState(navHostController, viewStateAsState, viewModel::onNavigated)
            }

            Content(viewModel)
        }
    }

    private fun updateNavigationState(
        navHostController: NavHostController,
        navigationState: NavigationState,
        onNavigated: (navState: NavigationState) -> Unit,
    ) {
        when (navigationState) {
            is NavigationState.NavigateToRoute -> {
                navHostController.navigate(navigationState.route)
                onNavigated(navigationState)
            }
            is NavigationState.PopToRoute -> {
                navHostController.popBackStack(navigationState.staticRoute, false)
                onNavigated(navigationState)
            }
            is NavigationState.NavigateUp -> {
                val res = navHostController.navigateUp()
                onNavigated(navigationState)
                if (!res) {
                    (navHostController.context as Activity).finish()
                }
            }
            is NavigationState.Idle -> {
            }
        }
    }
}
