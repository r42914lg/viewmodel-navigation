package com.r42914lg.vmnav

import android.app.Application
import com.r42914lg.vmnav.models.BreedViewModel
import com.r42914lg.vmnav.models.PictureViewModel
import com.r42914lg.vmnav.nav.MyRouteNavigator
import com.r42914lg.vmnav.nav.RouteNavigator
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

private val appModule = module {
    single<RouteNavigator> { MyRouteNavigator() }
    viewModel { BreedViewModel(get()) }
    viewModel { params ->
        PictureViewModel(
            params[0],
            params[1],
            get()
        )
    }
}

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }
}