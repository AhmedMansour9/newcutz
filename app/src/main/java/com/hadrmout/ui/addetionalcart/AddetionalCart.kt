package com.hadrmout.ui.addetionalcart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.activity.viewModels
import com.hadrmout.R
import com.hadrmout.ui.coupon.AddCopounViewModel
import com.hadrmout.utils.SharedData

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