package com.r42914lg.vmnav.routes

import androidx.compose.runtime.Composable
import com.r42914lg.vmnav.models.BreedViewModel
import com.r42914lg.vmnav.models.PictureViewModel
import com.r42914lg.vmnav.nav.NavRoute
import com.r42914lg.vmnav.ui.DetailsScreen
import com.r42914lg.vmnav.ui.MainScreen
import org.koin.androidx.compose.koinViewModel

object ListPageRoute : NavRoute<BreedViewModel>() {
    override val route = "screen_list"

    @Composable
    override fun viewModel(): BreedViewModel = koinViewModel()
    override fun getRoute(vararg params: Any) = route

    @Composable
    override fun Content(viewModel: BreedViewModel) = MainScreen(viewModel)
}

object DetailsPageRoute : NavRoute<PictureViewModel>() {
    private const val screenDetailsRoute = "screen_details"
    private const val someMandatoryArg = "arg_1"
    private const val someOptionalArg = "arg_2"

    override val route = "$screenDetailsRoute/$someMandatoryArg?$someOptionalArg=$someOptionalArg"

    override fun getRoute(vararg params: Any) = route
            .replace(someMandatoryArg, params[0] as String)
            .replace(someOptionalArg, params[1] as String)

    override fun getMandatoryArgumentKeys() = listOf(someMandatoryArg)
    override fun getOptionalArgumentKeys() = listOf(someOptionalArg)

    @Composable
    override fun viewModel(): PictureViewModel = koinViewModel { vmParams }
    @Composable
    override fun Content(viewModel: PictureViewModel) = DetailsScreen(viewModel)
}