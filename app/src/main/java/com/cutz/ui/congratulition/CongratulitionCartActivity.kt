package com.cutz.ui.congratulition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import com.bumptech.glide.Glide
import com.cutz.R
import com.cutz.ui.bottomnavigate.BottomNavigateActivity
import kotlinx.android.synthetic.main.activity_congratulition.*

class CongratulitionCartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_congratulition)

        Glide.with(this)
            .load(R.raw.successcart)
            .into(imgSuccess);
    }

    fun Btn_Cart(view: View) {
     val intent=Intent(this,BottomNavigateActivity::class.java)
        intent.putExtra("data","cart")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    fun Btn_finish(view: View) {
        finish()

    }
}