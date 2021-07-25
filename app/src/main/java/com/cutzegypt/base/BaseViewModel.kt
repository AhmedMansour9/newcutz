package com.cutzegypt.base

import androidx.lifecycle.ViewModel
import com.cutzegypt.data.DataCenterManager


abstract class BaseViewModel<BaseNavigator> constructor(protected val dataCenterManager: DataCenterManager) :
    ViewModel() {

    var navigator: BaseNavigator? = null

}


interface BaseNavigator