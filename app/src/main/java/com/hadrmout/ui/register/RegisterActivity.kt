package com.hadrmout.ui.register

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.hadrmout.R
import com.hadrmout.base.BaseActivity
import com.hadrmout.databinding.RegisterFragmentBinding
import com.hadrmout.ui.bottomnavigate.BottomNavigateActivity
import com.hadrmout.ui.login.LoginActivity
import com.hadrmout.ui.nointernet.NoInternertActivity
import com.hadrmout.utils.SharedData
import com.hadrmout.utils.Status
import com.hadrmout.utils.isConnected
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
        onClickPrivacy()
    }

    private fun onClickPrivacy() {
        mViewDataBinding.privacy.setOnClickListener() {
            val url = "https://hmantar.com"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
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