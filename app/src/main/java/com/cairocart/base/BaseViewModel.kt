package com.cairocart.base

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.cairocart.data.DataCenterManager


abstract class BaseViewModel<BaseNavigator> constructor(protected val dataCenterManager: DataCenterManager) :
    ViewModel() {

    var navigator: BaseNavigator? = null

}


interface BaseNavigator