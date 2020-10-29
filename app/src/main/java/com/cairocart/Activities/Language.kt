package com.cairocart.Activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.cairocart.ChangeLanguage
import com.cairocart.R
import com.yariksoffice.lingver.Lingver
import kotlinx.android.synthetic.main.activity_language.*

class Language : AppCompatActivity() {
    internal lateinit var share: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_language)
        Lan_Arabic()
        Lan_English()


    }




    fun Lan_Arabic() {
        Rela_Arabic.setOnClickListener(View.OnClickListener {
            share = getSharedPreferences("Language", MODE_PRIVATE).edit()
            share.putString("Lann", "ar")
            share.commit()
            Lingver.getInstance().setLocale(this, ChangeLanguage.getLanguage(this), "")
            val intent = Intent(this, BottomNavigation::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        })
    }

    fun Lan_English() {
        Rela_English.setOnClickListener(View.OnClickListener {
            share = getSharedPreferences("Language", MODE_PRIVATE).edit()
            share.putString("Lann", "en")
            share.commit()
            Lingver.getInstance().setLocale(this, ChangeLanguage.getLanguage(this), "")
            val intent = Intent(this, BottomNavigation::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        })

    }

}
