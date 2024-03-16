package com.r42914lg.details

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import org.koin.androidx.compose.koinViewModel

object DetailsPageRoute : com.r42914lg.navigation.NavRoute<PictureViewModel>() {
    private const val screenDetailsRoute = "screen_details"
    private const val someMandatoryArg = "arg_1"
    private const val someOptionalArg = "arg_2"

    override val route = "$screenDetailsRoute/{$someMandatoryArg}?$someOptionalArg={$someOptionalArg}"

    override fun getRoute(vararg params: Any) = if (params.size > 1) {
        "$screenDetailsRoute/${params[0]}?$someOptionalArg=${params[1]}"
        } else {
        "$screenDetailsRoute/${params[0]}"
        }

    override fun getArguments() = listOf(
        navArgument(someMandatoryArg) { type = NavType.StringType },
        navArgument(someOptionalArg) {
            type = NavType.StringType
            defaultValue = "Default value for optional arg.:"
        }
    )

    @Composable
    override fun viewModel(): PictureViewModel = koinViewModel { vmParams }
    @Composable
    override fun Content(viewModel: PictureViewModel) = DetailsScreen(viewModel)
}