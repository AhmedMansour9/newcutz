package com.cairocart.base

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.databinding.ViewDataBinding
import com.cairocart.R
import com.github.ybq.android.spinkit.style.FoldingCube

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    abstract var idLayoutRes: Int

    open var colorStatusBar: Int = 0

    lateinit var mViewDataBinding: T

    private var dailog: Dialog? = null
    private var foldingCube: FoldingCube? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (colorStatusBar != 0)
                window.statusBarColor = colorStatusBar
        }
        mViewDataBinding = setContentView(this, idLayoutRes)
        mViewDataBinding.executePendingBindings()
    }


    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    @SuppressLint("InflateParams")
    fun setupLoading() {
        this.dailog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.loading_dialog, null)

        dailog?.setContentView(view)
        val progressBar: ProgressBar = dailog!!.findViewById(R.id.progress)
        foldingCube = FoldingCube()
        foldingCube?.setDrawBounds(0, 0, 100, 100)
        foldingCube?.color = Color.WHITE
        dailog?.setCancelable(false)
        progressBar.indeterminateDrawable = foldingCube
        foldingCube?.start()

    }

    fun showLoading() {
        dailog?.show()
        foldingCube?.stop()

    }

    fun dismissLoading() {
        dailog?.dismiss()
        foldingCube?.stop()
    }

}