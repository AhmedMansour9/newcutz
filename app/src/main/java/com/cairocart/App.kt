package com.cairocart

import android.app.Application
import android.util.Log
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import com.cairocart.ChangeLanguage
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class App :Application(){

    @Suppress("UNUSED_VARIABLE")
    override fun onCreate() {
        super.onCreate()
        // you can use this instance for DI or get it via Lingver.getInstance() later on
        val lingver = Lingver.init(this, ChangeLanguage.getLanguage(this))
    }
}