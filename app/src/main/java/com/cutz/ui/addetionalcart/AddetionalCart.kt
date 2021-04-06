package com.cutz.ui.addetionalcart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import com.cutz.R
import com.cutz.data.remote.model.MessageEvent
import com.cutz.ui.coupon.AddCopounViewModel
import com.cutz.utils.SharedData
import com.cutz.utils.Status
import kotlinx.android.synthetic.main.activity_add_coupon.*
import org.greenrobot.eventbus.EventBus

class AddetionalCart :AppCompatActivity() {

    private var data: SharedData? = null
    private val mViewModel: AddCopounViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_add_coupon)
        val v = window.decorView
        v.setBackgroundResource(android.R.color.transparent)



    }



}