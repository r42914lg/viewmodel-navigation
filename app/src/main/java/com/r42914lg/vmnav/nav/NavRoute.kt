package com.r42914lg.vmnav.nav

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.koin.core.parameter.parametersOf

abstract class NavRoute<T : RouteNavigator> {

    protected open val route: String = ""
    protected var vmParams = parametersOf()

    @Composable
    abstract fun Content(viewModel: T)

    @Composable
    abstract fun viewModel(): T

    abstract fun getRoute(vararg params: Any): String

    protected open fun getMandatoryArgumentKeys(): List<String> = listOf()
    protected open fun getOptionalArgumentKeys(): List<String> = listOf()

    fun composable(
        builder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        builder.composable(route) {
            getMandatoryArgumentKeys().forEach { key ->
                val mandatoryArg = it.arguments?.getString(key) ?: throw IllegalStateException()
                vmParams.add(mandatoryArg)
            }
            getOptionalArgumentKeys().forEach { key ->
                val optionalArg = it.arguments?.getString(key)
                optionalArg?.let { vmParams.add(it) }
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
                navHostController.navigateUp()
                onNavigated(navigationState)
            }
            is NavigationState.Idle -> {
            }
        }
    }
}
