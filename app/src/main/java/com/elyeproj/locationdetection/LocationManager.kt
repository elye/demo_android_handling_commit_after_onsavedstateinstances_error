package com.elyeproj.locationdetection

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.support.v4.content.ContextCompat

class LocationManager(private val activity: Activity) {

    private var askedForPermission = false
    private var gpsSwitchStateReceiver: BroadcastReceiver? = null

    fun checkPermissionAndRequest(permissionCode: Int): Boolean {
        if (!askedForPermission) {
            activity.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
            askedForPermission = true
            return true
        }
        return false
    }

    fun isLocationPermissionGranted(): Boolean {
        return (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
    }

    fun requestLocationPermissionResult(grantResults: IntArray,
                                        permissions: Array<String>, onSuccess: () -> Unit = {},
                                        onDontAskAgain: () -> Unit = {}, onDenied: () -> Unit = {}) {
        if (permissions.isEmpty()) return
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onSuccess()
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !activity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            onDontAskAgain()
        } else {
            onDenied()
        }
    }

    fun isLocationEnabled(): Boolean {
        val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun removeSwitchStateReceiver() {
        gpsSwitchStateReceiver?.let {
            activity.unregisterReceiver(gpsSwitchStateReceiver)
            gpsSwitchStateReceiver = null
        }
    }

    fun locationDetection(onDetectChange: () -> Unit) {
        gpsSwitchStateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action.matches("android.location.PROVIDERS_CHANGED".toRegex())) {
                    onDetectChange()
                }
            }
        }
        activity.registerReceiver(gpsSwitchStateReceiver, IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION))
    }
}
