package com.cutz.ui.coupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.cutz.R
import com.cutz.base.BaseActivity
import com.cutz.data.remote.model.MessageEvent
import com.cutz.ui.bottomnavigate.BottomNavigateActivity
import com.cutz.ui.login.LoginViewModel
import com.cutz.ui.nointernet.NoInternertActivity
import com.cutz.ui.register.RegisterActivity
import com.cutz.utils.SharedData
import com.cutz.utils.Status
import com.cutz.utils.isConnected
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_coupon.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

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