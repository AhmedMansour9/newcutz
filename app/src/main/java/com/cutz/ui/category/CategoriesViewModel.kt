package com.cutz.ui.category

import androidx.hilt.lifecycle.ViewModelInject
import com.cutz.base.BaseViewModel
import com.cutz.data.DataCenterManager
import com.cutz.ui.register.RegisterNavigator

class CategoriesViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<CategoriesNavigator>(dataCenterManager) {

}