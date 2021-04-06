package com.cutz.ui.forgetpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.cutz.R
import com.cutz.base.BaseActivity
import com.cutz.databinding.ActivityForgetPasswordBinding
import com.cutz.databinding.ActivityVerifyPhoneBinding
import com.cutz.ui.bottomnavigate.BottomNavigateActivity
import com.cutz.ui.phone.UpdatePhoneViewModel
import com.cutz.ui.phone.VerifyPhoneActivity
import com.cutz.utils.SharedData
import com.cutz.utils.Status
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_verify_phone.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ForgetPasswordActivity : BaseActivity<ActivityForgetPasswordBinding>(){


    lateinit var phone:String
    override var idLayoutRes: Int = R.layout.activity_forget_password
    lateinit var auth: FirebaseAuth
    var storedVerificationId:String?= String()
    private val mViewModel: UpdatePhoneViewModel by viewModels()
    private var data: SharedData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= FirebaseAuth.getInstance()
        data = SharedData(this)
        setupObserver()
    }


    private fun setupObserver() {
        mViewModel.checkphoneResponse.observe(this, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    val intent= Intent(this, VerifyPhoneActivity::class.java)
                    intent.putExtra("phone",phone)
                    intent.putExtra("type","password")
                    startActivity(intent)
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




    private fun validateInputs() :Boolean{
        if(mViewDataBinding.EPhoneForget.text.isNullOrEmpty() ){
            return false
        }
        phone=mViewDataBinding.EPhoneForget.text.toString()
        return true
    }

    fun Send(view: View) {

        if(validateInputs()){
            val code=mViewDataBinding.EPhoneForget.text.toString()
                mViewModel.checkPhone(code)

        }

    }


}