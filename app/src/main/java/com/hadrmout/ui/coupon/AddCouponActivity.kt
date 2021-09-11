package com.hadrmout.ui.coupon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.hadrmout.R
import com.hadrmout.data.remote.model.MessageEvent
import com.hadrmout.utils.SharedData
import com.hadrmout.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_coupon.*
import org.greenrobot.eventbus.EventBus

@AndroidEntryPoint
class AddCouponActivity :AppCompatActivity() {

    private var data: SharedData? = null
    private val mViewModel: AddCopounViewModel by viewModels()
    private var token: String? = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_add_coupon)
        val v = window.decorView
        v.setBackgroundResource(android.R.color.transparent)
        data = SharedData(this)

        token = data?.getValue(SharedData.ReturnValue.STRING, "token")

        setupObserver()
        couponClick()

    }

    private fun setupObserver() {
        mViewModel.accountResponse.observe(this, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    Progress.visibility=View.GONE
                    EventBus.getDefault().postSticky(MessageEvent(it.data!!.data,"coupon"))
                    finish()

                }
                Status.LOADING -> {
                    Progress.visibility=View.VISIBLE
                }

                Status.ERROR -> {
                    Progress.visibility=View.GONE
                    Toast.makeText(this, ""+resources.getString(R.string.valid_coupon), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
     fun couponClick() {
         Confirm.setOnClickListener(){
             mViewModel.addCoupon(E_Coupon.text.toString(),token!! )
         }
    }




}