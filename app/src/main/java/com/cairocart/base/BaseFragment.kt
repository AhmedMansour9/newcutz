package com.cairocart.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.cairocart.R
import com.cairocart.utils.Loading
import com.github.ybq.android.spinkit.style.FoldingCube

abstract class BaseFragment<T : ViewDataBinding>() :
    Fragment() {


    abstract var idLayoutRes: Int
    lateinit var mViewDataBinding: T
    private var dailog: Dialog? = null
    private var foldingCube: FoldingCube? = null

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
        Loading.Show(requireContext())

    }

    fun dismissLoading() {
        Loading.Disable()
    }

}