package com.cairocart.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import com.github.ybq.android.spinkit.style.FoldingCube
import com.kaopiz.kprogresshud.KProgressHUD

abstract class BaseFragment<T : ViewDataBinding>() :
    Fragment() {


    abstract var idLayoutRes: Int
    lateinit var mViewDataBinding: T
    private var dailog: Dialog? = null
    private var foldingCube: FoldingCube? = null
    private  lateinit var hud: KProgressHUD


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, idLayoutRes, container, false)
        return mViewDataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupDataBinding();

    }


    private fun setupDataBinding() {
        mViewDataBinding.executePendingBindings()
    }


    fun showLoading() {
        hud = KProgressHUD.create(requireActivity())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(false)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
            .show()
    }

    fun dismissLoading() {
        if( this::hud.isInitialized){
            if(hud.isShowing){
                hud.dismiss()
            }
        }

    }


}