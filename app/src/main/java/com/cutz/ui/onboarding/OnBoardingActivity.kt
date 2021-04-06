package com.cutz.ui.onboarding

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.viewpager2.widget.ViewPager2
import com.cutz.R
import com.cutz.adapter.IntroSliderAdapter
import com.cutz.base.BaseActivity
import com.cutz.data.remote.model.IntroSlide
import com.cutz.databinding.ActivityOnBoardingBinding
import com.cutz.ui.login.LoginActivity
import com.cutz.utils.SharedData
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OnBoardingActivity : BaseActivity<ActivityOnBoardingBinding>() {

    override var idLayoutRes: Int= R.layout.activity_on_boarding

    private var data: SharedData? = null

    private lateinit var introSliderAdapter :IntroSliderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(Color.BLACK)
        }
        super.onCreate(savedInstanceState)
        data = SharedData(this)
        initAdapter()
        skip()
        mViewDataBinding.viewPager.adapter = introSliderAdapter
        mViewDataBinding.indicator.setViewPager(mViewDataBinding.viewPager)
        mViewDataBinding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (position == introSliderAdapter.itemCount - 1) {
                        val animation = AnimationUtils.loadAnimation(
                            this@OnBoardingActivity,
                            R.anim.app_name_animation
                        )
                        mViewDataBinding.buttonNext.animation = animation
                        mViewDataBinding.buttonNext.text = resources.getString(R.string.finish)
                        mViewDataBinding.buttonNext.setOnClickListener {
                            data?.putValue("slider", "true")
                            startActivity(
                                Intent(
                                    this@OnBoardingActivity,
                                    LoginActivity::class.java
                                )
                            )
                            finish()
                        }
                    } else {
                        mViewDataBinding.buttonNext.text = resources.getString(R.string.next)
                        mViewDataBinding.buttonNext.setOnClickListener {
                            mViewDataBinding.viewPager.currentItem.let {
                                mViewDataBinding.viewPager.setCurrentItem(it + 1, false)
                            }
                        }
                    }
                }
            })


    }

    private fun initAdapter() {
        introSliderAdapter = IntroSliderAdapter(
            listOf(
                IntroSlide(
                    resources.getString(R.string.buy_anything),
                    resources.getString(R.string.f_txt),
                    R.drawable.img_buy
                ),
                IntroSlide(
                    resources.getString(R.string.exclusive),
                    resources.getString(R.string.s_txt),
                    R.drawable.img_exclusive
                ),
                IntroSlide(
                    resources.getString(R.string.amazing_disc),
                    resources.getString(R.string.third_txt),
                    R.drawable.img_amazing
                )
            )
        )
    }

    fun skip(){
        mViewDataBinding.TSkip.setOnClickListener(){
            data?.putValue("slider", "true")
            startActivity(
                Intent(
                    this@OnBoardingActivity,
                    LoginActivity::class.java
                )
            )
            finish()
        }
    }
}