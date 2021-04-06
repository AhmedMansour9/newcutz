package com.cutz

import android.app.Application
import com.cutz.utils.SharedData
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App :Application(){


    @Suppress("UNUSED_VARIABLE")
    override fun onCreate() {
        super.onCreate()
        // you can use this instance for DI or get it via Lingver.getInstance() later on
        val lingver = Lingver.init(this, ChangeLanguage.getLanguage(this))
    }
}