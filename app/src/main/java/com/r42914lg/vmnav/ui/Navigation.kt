package com.r42914lg.vmnav.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = screenListRoute,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        screenListNavGraphBuilder(
            navController::navigateToDetails,
            navController::popBackStack,
        )
        screenDetailsNavGraphBuilder(
            navController::popBackStack,
        )
    }
}

/**
 * Screen List navigation
 */

const val screenListRoute = "screen_list"

fun NavGraphBuilder.screenListNavGraphBuilder(
    onToDetails: (String) -> Unit,
    onBack: () -> Unit
) {
    composable(screenListRoute) {
        MainScreen(
            onToDetails = onToDetails,
            onBack = onBack,
        )
    }
}

/**
 * Screen Details navigation
 */

const val screenDetailsRoute = "screen_Details"
const val someMandatoryArg = "arg_1"
const val someOptionalArg = "arg_2"

fun NavGraphBuilder.screenDetailsNavGraphBuilder(
    onBack: () -> Unit
) {
    composable(
        route = "$screenDetailsRoute/{$someMandatoryArg}?$someOptionalArg={$someOptionalArg}",
        arguments = listOf(
            navArgument(someMandatoryArg) { type = NavType.StringType },
            navArgument(someOptionalArg) {
                type = NavType.StringType
                nullable = false
                defaultValue = "some default value"
            }
        )
    ) {
        val mandatoryArg = it.arguments?.getString(someMandatoryArg) ?: throw IllegalStateException()
        DetailsScreen(
            onBack = onBack,
            breedId = mandatoryArg,
        )
    }
}

fun NavController.navigateToDetails(breed: String) {
    navigate("$screenDetailsRoute/${breed}")
}
