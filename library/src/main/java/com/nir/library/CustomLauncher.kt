package com.nir.library

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import java.util.Locale

/**
 * CustomLauncher singleton class is responsible for providing app launchers detail
 * and also notifies about apps installation & un installation
 */
object CustomLauncher {

    private lateinit var onInstallation: (String) -> Unit
    private lateinit var onUnInstallation: (String) -> Unit

     fun getAppLaunchers(context: Context): List<LauncherData>{
        val appList = mutableListOf<LauncherData>()
        val pManager: PackageManager = context.packageManager ?: throw IllegalStateException("Context can't be null")
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val allApps = pManager.queryIntentActivities(intent, 0)
        for (resolveInfo in allApps) {
            val pInfo = context.packageManager?.getPackageInfo(resolveInfo.activityInfo.packageName, 0)
            val app = LauncherData(
                resolveInfo.loadLabel(pManager).toString(),
                resolveInfo.activityInfo.packageName,
                resolveInfo.activityInfo.loadIcon(
                    pManager
                ),
                resolveInfo.activityInfo.name,
                pInfo?.versionCode,
                pInfo?.versionName.toString()
            )
            appList.add(app)
        }
        appList.sortBy { it.appName.toLowerCase(Locale.getDefault()) }
        return appList
    }

    private val installReceiver: BroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val packageName = intent?.data?.encodedSchemeSpecificPart
            packageName?.let { onInstallation(it) }
        }
    }

    fun registerInstallReceiver(context: Context?, onResult: (String) -> Unit) {
        onInstallation = onResult
        context?.registerReceiver(installReceiver, getIntentFilter(Intent.ACTION_PACKAGE_ADDED))
    }

    private val uninstallReceiver: BroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val packageName = intent?.data?.encodedSchemeSpecificPart
            packageName?.let { onUnInstallation(it) }
        }
    }

    fun registerUninstallReceiver(context: Context?, onResult: (String) -> Unit) {
        onUnInstallation = onResult
        context?.registerReceiver(uninstallReceiver, getIntentFilter(Intent.ACTION_PACKAGE_REMOVED))
    }

    private fun getIntentFilter(action: String): IntentFilter {
        val intentFilter = IntentFilter()
        intentFilter.addAction(action)
        intentFilter.addDataScheme("package")
        return intentFilter
    }

    fun unRegisterReceivers(context: Context?) {
        context?.unregisterReceiver(installReceiver)
        context?.unregisterReceiver(uninstallReceiver)
    }
}