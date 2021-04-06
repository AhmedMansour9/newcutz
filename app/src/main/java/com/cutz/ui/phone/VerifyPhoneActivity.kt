package com.cutz.ui.phone

import android.content.Intent
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
import com.cutz.databinding.ActivityVerifyPhoneBinding
import com.cutz.ui.bottomnavigate.BottomNavigateActivity
import com.cutz.ui.login.LoginActivity
import com.cutz.ui.resendpassword.ResendPasswordActivitty
import com.cutz.utils.SharedData
import com.cutz.utils.Status
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_verify_phone.*
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class VerifyPhoneActivity : BaseActivity<ActivityVerifyPhoneBinding>(){


    lateinit var phone:String
    override var idLayoutRes: Int = R.layout.activity_verify_phone
    lateinit var auth:FirebaseAuth
    lateinit var callbacks:OnVerificationStateChangedCallbacks
    var storedVerificationId:String?= String()
    lateinit var resendToken:ForceResendingToken
    private val mViewModel: UpdatePhoneViewModel by viewModels()
    private var data: SharedData? = null
    var type :String?= String()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= FirebaseAuth.getInstance()
        data = SharedData(this)
        callback()
        getPhone()
        sendCode()
        setupOtpInputs()
        setupObserver()
    }

    private fun initTime() {
        val maxTimeInMilliseconds: Long = 120000 // in your case
        startTimer(maxTimeInMilliseconds, 1000)

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
                }
                Status.ERROR -> {
                    dismissLoading()

                }
            }
        })
    }

    private fun sendCode() {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+2$phone") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    private fun callback(){
        initTime()

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
//                Log.d(TAG, "onVerificationCompleted:$credential")
//                Toast.makeText(this@VerifyPhoneActivity, ""+credential.smsCode.toString(), Toast.LENGTH_SHORT).show()
//                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("errorr", e.message.toString())
                  dismissLoading()
//                Toast.makeText(this@VerifyPhoneActivity, ""+e.message.toString(), Toast.LENGTH_SHORT).show()

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

                // Show a message and update the UI
                // ...
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                dismissLoading()

//                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token

                // ...
            }
        }

    }

    private fun getPhone() {
       phone= intent.extras?.getString("phone")!!
        type= intent.extras?.getString("type")!!
    }
    fun Verify(view: View) {
        if(validateInputs()){
        var code=mViewDataBinding.EFirst.text.toString()+mViewDataBinding.ESecond.text.toString()+mViewDataBinding.EThird.text.toString()+ mViewDataBinding.EFourth.text.toString() +mViewDataBinding.EFifth.text.toString() +mViewDataBinding.ESix.text.toString()
         if(!storedVerificationId.isNullOrEmpty()){
             showLoading()
             val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, code)
               signInWithPhoneAuthCredential(credential)
         }
        }

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("success", "signInWithCredential:success")

                    val user = task.result?.user
                    if(type.equals("password")){
                        dismissLoading()
                        var intent=Intent(this, ResendPasswordActivitty::class.java)
                        intent.putExtra("phone",phone)
                        startActivity(intent)

                    }else {
                        mViewModel.verify(phone)
                    }
                    // ...
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("error", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        dismissLoading()
                        Toast.makeText(this,resources.getString(R.string.wrong_verify), Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun setupOtpInputs(){
        mViewDataBinding.EFirst.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()){
                    mViewDataBinding.ESecond.requestFocus()
                }
            }
        })
        mViewDataBinding.ESecond.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()){
                    mViewDataBinding.EThird.requestFocus()
                }
            }
        })
        mViewDataBinding.EThird.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()){
                    mViewDataBinding.EFourth.requestFocus()
                }
            }
        })
        mViewDataBinding.EFourth.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()){
                    mViewDataBinding.EFifth.requestFocus()
                }
            }
        })
        mViewDataBinding.EFifth.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()){
                    mViewDataBinding.ESix.requestFocus()
                }
            }
        })
        mViewDataBinding.ESix.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()){
                   mViewDataBinding.btnVerify.isEnabled=true
                }
            }
        })
    }

    private fun validateInputs() :Boolean{
        if(mViewDataBinding.EFirst.text.isNullOrEmpty() && mViewDataBinding.ESecond.text.isNullOrEmpty() && mViewDataBinding.EThird.text.isNullOrEmpty() && mViewDataBinding.EFourth.text.isNullOrEmpty()
            && mViewDataBinding.EFifth.text.isNullOrEmpty()  && E_Six.text.isNullOrEmpty()){
            return false
        }
        return true
    }

    fun resend(view: View) {
        callback()
        sendCode()

    }


    fun startTimer(finish: Long, tick: Long) {

        mViewDataBinding.resend.isEnabled=false

        val t: CountDownTimer
        t = object : CountDownTimer(finish, tick) {
            override fun onTick(millisUntilFinished: Long) {
                val remainedSecs = millisUntilFinished / 1000
                mViewDataBinding.seconds.setText("" + remainedSecs / 60 + ":" + remainedSecs % 60) // manage it accordign to you
            }

            override fun onFinish() {
                mViewDataBinding.seconds.setText("0")
                mViewDataBinding.resend.isEnabled=true
                cancel()
            }
        }.start()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(!type.equals("password")){
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }

}