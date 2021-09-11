package com.hadrmout.ui.forgetpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.hadrmout.R
import com.hadrmout.data.remote.model.MessageEvent
import com.hadrmout.ui.coupon.AddCopounViewModel
import com.hadrmout.utils.SharedData
import com.hadrmout.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_coupon.E_Coupon
import kotlinx.android.synthetic.main.activity_forget_password2.*
import org.greenrobot.eventbus.EventBus

@AndroidEntryPoint
class ForgetPasswordActivity :AppCompatActivity() {


    private var data: SharedData? = null
    private val mViewModel: ForgetPasswordViewModel by viewModels()
    private var token: String? = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_forget_password2)
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
                    Progress.visibility = View.GONE
                    Toast.makeText(this, ""+resources.getString(R.string.updatedemail), Toast.LENGTH_SHORT).show()
                    finish()

                }
                Status.LOADING -> {
                    Progress.visibility = View.VISIBLE
                }

                Status.ERROR -> {
                    Progress.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "" + resources.getString(R.string.validateEmail),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
    fun couponClick() {
        Confirm.setOnClickListener(){
            mViewModel.forgetPassword(E_PasswordForget.text.toString())
        }
    }


}