package com.cutz.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import com.cutz.R
import com.cutz.base.BaseActivity
import com.cutz.databinding.ActivitySplash2Binding
import com.cutz.ui.bottomnavigate.BottomNavigateActivity
import com.cutz.ui.login.LoginActivity
import com.cutz.ui.onboarding.OnBoardingActivity
import com.cutz.utils.SharedData
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Splash : BaseActivity<ActivitySplash2Binding>() {
    private var data: SharedData? = null

    override var idLayoutRes: Int = R.layout.activity_splash2
    private var cdt: CountDownTimer? = null
    private var checkIntro:String?= String()
    private var token:String?= String()
    private var Lang:String?= String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = SharedData(this)
        init()
        if(!checkIntro.isNullOrEmpty()){
          if(token.isNullOrEmpty()){
              navigateToLogin()
          }  else {
              navigateToHome()

          }
        }else
        {
            navigateToSlider()
        }

    }

    private fun init() {
        checkIntro=data?.getValue(SharedData.ReturnValue.STRING, "slider")
        token=data?.getValue(SharedData.ReturnValue.STRING, "token")
        Lang=data?.getValue(SharedData.ReturnValue.STRING, "Lann")
        Lang?.let { Lingver.getInstance().setLocale(this, it, "") }

    }


    private fun navigateToHome() {
        setupCounterDown {
            startActivity(Intent(this, BottomNavigateActivity::class.java))
            finish()
        }

    }
    private fun navigateToLogin() {
        setupCounterDown {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }
    private fun navigateToSlider() {
        setupCounterDown {
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
        }

    }

    private fun setupCounterDown(action: () -> Unit) {
        cdt = object : CountDownTimer(1000, 3000) {
            override fun onFinish() {
                action()
            }

            override fun onTick(p0: Long) {

            }
        }
        cdt?.start()
    }


    override fun onStop() {
        cdt?.cancel()
        super.onStop()
    }
}