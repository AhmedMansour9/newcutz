package com.cutz.ui.nointernet

import android.os.Bundle
import android.view.Window
import com.cutz.R
import com.cutz.base.BaseActivity
import com.cutz.data.remote.model.MessageEvent
import com.cutz.databinding.ActivityNointernetBinding
import com.cutz.utils.isConnected
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