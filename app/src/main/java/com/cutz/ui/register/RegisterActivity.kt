package com.cutz.ui.register

import android.R.attr.phoneNumber
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.cutz.R
import com.cutz.base.BaseActivity
import com.cutz.data.remote.model.MessageEvent
import com.cutz.databinding.RegisterFragmentBinding
import com.cutz.ui.bottomnavigate.BottomNavigateActivity
import com.cutz.ui.login.LoginActivity
import com.cutz.ui.nointernet.NoInternertActivity
import com.cutz.ui.phone.VerifyPhoneActivity
import com.cutz.utils.SharedData
import com.cutz.utils.Status
import com.cutz.utils.isConnected
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class RegisterActivity : BaseActivity<RegisterFragmentBinding>(), RegisterNavigator {

    override var idLayoutRes: Int = R.layout.register_fragment

    private val mViewModel: RegisterViewModel by viewModels()

    private var data: SharedData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = SharedData(this)
        mViewDataBinding.registerViewModel = mViewModel
        mViewModel.navigator = this
        setupObserver()
        checkDirections()
    }

    private fun checkDirections() {
        var Lang: String? = data?.getValue(SharedData.ReturnValue.STRING, "Lann")
        if (!Lang.isNullOrEmpty()) {
            if (Lang.equals("ar"))
                mViewDataBinding.EPhone.gravity = Gravity.RIGHT
            mViewDataBinding.EName.gravity = Gravity.RIGHT
            mViewDataBinding.EEmail.gravity = Gravity.RIGHT
            mViewDataBinding.EPassword.gravity = Gravity.RIGHT

        }
    }

    override fun backLoginClick() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun sigupClick() {
        if (!this.isConnected) {
            startActivity(Intent(this, NoInternertActivity::class.java))
        }
        mViewModel.register()
    }

    private fun setupObserver() {
        mViewModel.accountResponse.observe(this, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
//                    data?.putValue("token", it?.data?.data?.accessToken)
//
//                    EventBus.getDefault().postSticky(MessageEvent("login"))
//                    val intent=Intent(this, BottomNavigateActivity::class.java)
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//                    startActivity(intent)
//                    finish()


                }
                Status.LOADING -> {
                    showLoading()
                }

                Status.ERROR -> {
                    dismissLoading()
                    if (it.message.equals("Verifiy Your Account ")) {
                        var intent = Intent(this, VerifyPhoneActivity::class.java)
                        intent.putExtra("phone", mViewDataBinding.EPhone.text.toString())
                        intent.putExtra("type", "register")
                        startActivity(intent)
                        finish()

                    } else {
                        error(resources.getString(R.string.error), it.message.toString())
                    }
                }
            }
        })
    }

}