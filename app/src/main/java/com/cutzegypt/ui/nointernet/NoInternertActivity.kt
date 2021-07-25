package com.cutzegypt.ui.nointernet

import android.os.Bundle
import android.view.Window
import com.cutzegypt.R
import com.cutzegypt.base.BaseActivity
import com.cutzegypt.data.remote.model.MessageEvent
import com.cutzegypt.databinding.ActivityNointernetBinding
import com.cutzegypt.utils.isConnected
import org.greenrobot.eventbus.EventBus

class NoInternertActivity : BaseActivity<ActivityNointernetBinding>() {

    override var idLayoutRes: Int = R.layout.activity_nointernet

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)

        mViewDataBinding.BtnTryAgain.setOnClickListener(){
            if(this.isConnected) {
                EventBus.getDefault().postSticky(MessageEvent("network"))
                finish()
            }
        }

    }
}