package com.cairocart.Activities

import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.cairocart.R
import kotlinx.android.synthetic.main.activity_log_out.*


class LogOut : AppCompatActivity() {
    private lateinit var dataSaver: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_log_out)
        dataSaver = PreferenceManager.getDefaultSharedPreferences(this);

        Rela_Refuse.setOnClickListener(){
            finish()
        }
        Rela_Accept.setOnClickListener(){
            dataSaver.edit().putString("token", null).apply()
            val intent = Intent(this, TabsLayout::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }


    }
}
