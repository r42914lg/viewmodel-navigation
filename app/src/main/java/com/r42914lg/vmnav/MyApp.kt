package com.r42914lg.vmnav

import android.app.Application
import com.r42914lg.details.detailsModule
import com.r42914lg.list.listModule
import com.r42914lg.navigation.navModule
import org.koin.core.context.startKoin

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                navModule,
                detailsModule,
                listModule,
            )
        }
    }
}