package com.gazr.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.navigation.Navigation
import com.gazr.R
import kotlinx.android.synthetic.main.activity_congratulation.*

class Congratulation : AppCompatActivity() {
    var status:String?= String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_congratulation)

        T_Message.text=intent.getStringExtra("msg")
         status=intent.getStringExtra("status")
         if(status!=null){
             T_Follow.visibility= View.VISIBLE
         }
        btnDone.setOnClickListener(){
            if(status!=null){
                val intent = Intent(
                    this,
                    TabsLayout::class.java
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("status","true")
                startActivity(intent)

//                this!!.let { Navigation.findNavController(it, R.id.fragment).navigate(R.id.T_Setting) };

            }else {
                val intent = Intent(
                    this,
                    TabsLayout::class.java
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(
            this,
            TabsLayout::class.java
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}