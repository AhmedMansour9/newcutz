package com.cutz.base

import android.app.Application
import androidx.lifecycle.ViewModel
import com.cutz.App
import com.cutz.data.DataCenterManager


abstract class BaseViewModel<BaseNavigator> constructor(protected val dataCenterManager: DataCenterManager) :
    ViewModel() {

    var navigator: BaseNavigator? = null

}


interface BaseNavigator