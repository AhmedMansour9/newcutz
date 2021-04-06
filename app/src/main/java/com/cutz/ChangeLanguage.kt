package com.cutz

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatActivity
import com.cutz.utils.SharedData
import java.util.*


class ChangeLanguage {

    companion object {


        private var data: SharedData? = null

        fun getLanguage(context: Context):String{


            var DeviceLang=""
            var shared:  SharedPreferences = context.getSharedPreferences("Language", AppCompatActivity.MODE_PRIVATE)
            val Lan = shared.getString("Lann", null)
            if(Lan!=null){
                DeviceLang = Lan
            }else {
                DeviceLang = Locale.getDefault().language

            }
            return DeviceLang
        }


    }
}