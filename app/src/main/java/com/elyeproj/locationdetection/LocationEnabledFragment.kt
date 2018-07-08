package com.elyeproj.locationdetection

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class LocationEnabledFragment: Fragment() {

    companion object {
        const val TAG = "LocationEnabledFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_location_enabled, container, false)
    }
}
