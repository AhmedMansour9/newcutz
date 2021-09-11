package com.hadrmout.ui.nointernet

import android.os.Bundle
import android.view.Window
import com.hadrmout.R
import com.hadrmout.base.BaseActivity
import com.hadrmout.data.remote.model.MessageEvent
import com.hadrmout.databinding.ActivityNointernetBinding
import com.hadrmout.utils.isConnected
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