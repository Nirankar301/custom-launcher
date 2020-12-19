package com.nir.launcher

import android.app.Application
import com.nir.launcher.module.dataModule
import com.nir.launcher.module.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LauncherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LauncherApp)
            modules(arrayListOf(dataModule, appModule))
        }
    }
}