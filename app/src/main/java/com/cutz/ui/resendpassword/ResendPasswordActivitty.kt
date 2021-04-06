package com.cutz.ui.resendpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.cutz.R
import com.cutz.base.BaseActivity
import com.cutz.databinding.ActivityForgetPasswordBinding
import com.cutz.databinding.ActivityResendPasswordActivittyBinding
import com.cutz.databinding.ActivityResendPasswordActivittyBindingImpl
import com.cutz.ui.bottomnavigate.BottomNavigateActivity
import com.cutz.ui.phone.UpdatePhoneViewModel
import com.cutz.ui.phone.VerifyPhoneActivity
import com.cutz.utils.SharedData
import com.cutz.utils.Status
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ResendPasswordActivitty : BaseActivity<ActivityResendPasswordActivittyBinding>(){


    lateinit var phone:String
    override var idLayoutRes: Int = R.layout.activity_resend_password_activitty
    lateinit var auth: FirebaseAuth
    var storedVerificationId:String?= String()
    private val mViewModel: UpdatePhoneViewModel by viewModels()
    private var data: SharedData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= FirebaseAuth.getInstance()
        data = SharedData(this)
        phone= intent.getStringExtra("phone")!!
        setupObserver()
    }


    private fun setupObserver() {
        mViewModel.accountResponse.observe(this, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    data?.putValue("token", it?.data?.data?.accessToken)
                    val intent= Intent(this, BottomNavigateActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
                Status.LOADING -> {
                    showLoading()
                }
                Status.ERROR -> {
                    dismissLoading()
                    error(resources.getString(R.string.error),it.message.toString())
                }
            }
        })
    }




    private fun validatePassword() :Boolean{
        if(mViewDataBinding.EPasswordForget.text.isNullOrEmpty() ){
            return false
        }
        return true
    }

    private fun validateConfirm() :Boolean{
        if(mViewDataBinding.EConfirmPasswordForget.text.isNullOrEmpty() ){
            return false
        }
        return true
    }

    private fun validateMatch() :Boolean{
        if(mViewDataBinding.EPasswordForget.text.toString().equals(mViewDataBinding.EConfirmPasswordForget.text.toString()) ){

            return true
        }
        Toast.makeText(this, resources.getString(R.string.p_notmatch), Toast.LENGTH_SHORT).show()
        return false
    }
    fun Send(view: View) {

        if(validatePassword() && validateConfirm() ){
            if(validateMatch()){
                mViewModel.resendPassword(phone,mViewDataBinding.EPasswordForget.text.toString())
            }

        }

    }


}