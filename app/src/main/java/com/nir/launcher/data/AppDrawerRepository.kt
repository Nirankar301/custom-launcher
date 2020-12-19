package com.nir.launcher.data

import android.content.Context
import com.nir.launcher.model.AppData
import com.nir.library.CustomLauncher

class AppDrawerRepository(private val context: Context) : IAppDrawerRepository{

    override fun getAppLaunchers(): List<AppData> {
        val appList = mutableListOf<AppData>()
        CustomLauncher.getAppLaunchers(context).forEach {
            appList.add(AppData(
                it.appName,
                it.packageName,
                it.icon,
                it.launcherActivityName,
                it.versionCode,
                it.versionName
            ))
        }
        return appList
    }
}