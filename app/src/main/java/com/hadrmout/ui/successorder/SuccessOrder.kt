package com.hadrmout.ui.successorder

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hadrmout.R
import com.hadrmout.ui.bottomnavigate.BottomNavigateActivity
import kotlinx.android.synthetic.main.activity_success_order.*

class SuccessOrder : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_order)
        orderid.text=resources.getString(R.string.orderid)+" "+intent.getStringExtra("id")
    }

    fun Btn_finish(view: View) {
        val intent= Intent(this, BottomNavigateActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent= Intent(this, BottomNavigateActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}