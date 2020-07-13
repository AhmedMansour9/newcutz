package com.mgh.Activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mgh.R
import com.mgh.SharedPrefManager
import kotlinx.android.synthetic.main.activity_countries.*

class Countries : AppCompatActivity() {
    internal lateinit var share: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries)



    }

    fun English(view: View) {
        share = getSharedPreferences("Language", MODE_PRIVATE).edit()
        share.putString("Lann", "en")
        share.commit()
        ImgTrue_Arabic.visibility=View.GONE
        ImgTrue_English.visibility=View.VISIBLE
    }

    fun Arabic(view: View) {
        share = getSharedPreferences("Language", MODE_PRIVATE).edit()
        share.putString("Lann", "ar")
        share.commit()
        ImgTrue_Arabic.visibility=View.VISIBLE
        ImgTrue_English.visibility=View.GONE
    }
    fun Saudi(view: View) {
        SharedPrefManager.getInstance(this).saveCurrncy("SAR")
        ImgTrue_Saudi.visibility=View.VISIBLE
        ImgTrue_Emarat.visibility=View.GONE
        ImgTrue_Kewait.visibility=View.GONE
        ImgTrue_Egypt.visibility=View.GONE

    }
    fun Emarat(view: View) {
        SharedPrefManager.getInstance(this).saveCurrncy("AED")
        ImgTrue_Saudi.visibility=View.GONE
        ImgTrue_Emarat.visibility=View.VISIBLE
        ImgTrue_Kewait.visibility=View.GONE
        ImgTrue_Egypt.visibility=View.GONE

    }
    fun Kewait(view: View) {
        SharedPrefManager.getInstance(this).saveCurrncy("KWD")
        ImgTrue_Saudi.visibility=View.GONE
        ImgTrue_Emarat.visibility=View.GONE
        ImgTrue_Kewait.visibility=View.VISIBLE
        ImgTrue_Egypt.visibility=View.GONE

    }
    fun Egypt(view: View) {
        SharedPrefManager.getInstance(this).saveCurrncy("EGP")
        ImgTrue_Saudi.visibility=View.GONE
        ImgTrue_Emarat.visibility=View.GONE
        ImgTrue_Kewait.visibility=View.GONE
        ImgTrue_Egypt.visibility=View.VISIBLE

    }
    fun Next(view: View) {
        val intent = Intent(this, Navigation::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()

    }
}
