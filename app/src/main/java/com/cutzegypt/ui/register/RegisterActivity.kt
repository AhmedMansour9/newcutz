package com.cutzegypt.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.cutzegypt.R
import com.cutzegypt.base.BaseActivity
import com.cutzegypt.databinding.RegisterFragmentBinding
import com.cutzegypt.ui.bottomnavigate.BottomNavigateActivity
import com.cutzegypt.ui.login.LoginActivity
import com.cutzegypt.ui.nointernet.NoInternertActivity
import com.cutzegypt.utils.SharedData
import com.cutzegypt.utils.Status
import com.cutzegypt.utils.isConnected
import dagger.hilt.android.AndroidEntryPoint


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
                    data?.putValue("token", it?.data?.data?.accessToken)
                    val intent = Intent(this, BottomNavigateActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()

                }
                Status.LOADING -> {
                    showLoading()
                }

                Status.ERROR -> {
                    dismissLoading()
                    error(resources.getString(R.string.error), it.message.toString())

                }
            }
        })
    }

}