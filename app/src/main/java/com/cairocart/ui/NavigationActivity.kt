package com.cairocart.ui

import android.graphics.Color
import android.os.Bundle
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import com.cairocart.R
import com.cairocart.base.BaseActivity
import com.cairocart.databinding.NavigationActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class NavigationActivity : BaseActivity<NavigationActivityBinding>() {

    override var idLayoutRes: Int = R.layout.navigation_activity

    override var colorStatusBar: Int = Color.parseColor("#212121")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupLoading()

    }



}