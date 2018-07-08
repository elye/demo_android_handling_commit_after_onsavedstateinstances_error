package com.elyeproj.locationdetection

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_no_permission.btn_open_permission

class NoPermissionFragment: Fragment() {

    companion object {
        const val TAG = "NoPermissionFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_no_permission, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_open_permission.setOnClickListener {
            context?.let { Intent().openSettingPage(it) }
        }
    }
}
