package com.elyeproj.locationdetection

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_LOCATION_PERMISSION_CODE = 1000
    }

    private val locationManager = LocationManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        if (!locationManager.checkPermissionAndRequest(REQUEST_LOCATION_PERMISSION_CODE)) {
            openFragment()
        }
    }

    private fun openFragment() {
        if (!locationManager.isLocationPermissionGranted()) {
            openFragment(NoPermissionFragment.TAG, { NoPermissionFragment() })
        } else {
            locationManager.locationDetection { onResume() }
            if (!locationManager.isLocationEnabled()) {
                openFragment(NotEnabledFragment.TAG, { NotEnabledFragment() })
            } else {
                openFragment(LocationEnabledFragment.TAG, { LocationEnabledFragment() })
            }
        }
    }

    private fun openFragment(tag: String, instantiateFragment: () -> Fragment) {
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            supportFragmentManager.beginTransaction().replace(R.id.container, instantiateFragment(), tag).commit()
        }
    }

    override fun onPause() {
        super.onPause()
        locationManager.removeSwitchStateReceiver()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION_CODE -> {
                openFragment()
                // Calling this just as demo, it doesn't do anything for now
                locationManager.requestLocationPermissionResult(grantResults, permissions,
                        onSuccess = {}, onDontAskAgain = {}, onDenied = {}
                )
            }
        }
    }
}
