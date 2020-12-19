package com.nir.launcher.model

import android.graphics.drawable.Drawable

data class AppData(
    var appName: String,
    var packageName: String,
    var icon: Drawable,
    var launcherActivityName: String,
    var versionCode: Int?,
    var versionName: String
)
