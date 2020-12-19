package com.nir.library

import android.graphics.drawable.Drawable

data class LauncherData(
    var appName: String,
    var packageName: String,
    var icon: Drawable,
    var launcherActivityName: String,
    var versionCode: Int?,
    var versionName: String
)