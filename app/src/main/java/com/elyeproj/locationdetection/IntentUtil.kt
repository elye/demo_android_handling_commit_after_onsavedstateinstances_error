package com.elyeproj.locationdetection

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun Intent.openSettingPage(context: Context) {
    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    addCategory(Intent.CATEGORY_DEFAULT)
    data = Uri.parse("package:" + context.packageName)
    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
    addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
    context.startActivity(this)
}

fun Intent.openLocationSetting(context: Context) {
    action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
    context.startActivity(this)
}
