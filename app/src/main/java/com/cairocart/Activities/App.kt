package com.cairocart.Activities

import android.app.Application
import com.cairocart.ChangeLanguage
import com.yariksoffice.lingver.Lingver

class App :Application(){

//    override fun attachBaseContext(base: Context?) {
//        super.attachBaseContext(base)
//    }

    @Suppress("UNUSED_VARIABLE")
    override fun onCreate() {
        super.onCreate()
        // you can use this instance for DI or get it via Lingver.getInstance() later on
        val lingver = Lingver.init(this, ChangeLanguage.getLanguage(this))
    }
}