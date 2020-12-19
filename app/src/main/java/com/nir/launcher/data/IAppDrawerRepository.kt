package com.nir.launcher.data

import com.nir.launcher.model.AppData

interface IAppDrawerRepository {
    /**
     * Fetches the details of all app launchers
     * @return list of [AppData]
     */
    fun getAppLaunchers(): List<AppData>
}