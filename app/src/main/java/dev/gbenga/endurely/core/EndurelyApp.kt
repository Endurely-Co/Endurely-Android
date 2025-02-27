package dev.gbenga.endurely.core

import android.app.Application
import dev.gbenga.endurely.di.appModule
import dev.gbenga.endurely.di.onboardModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class EndurelyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@EndurelyApp)
            modules(appModule, onboardModule)
        }
    }
}