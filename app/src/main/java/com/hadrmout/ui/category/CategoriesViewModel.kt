package com.hadrmout.ui.category

import androidx.hilt.lifecycle.ViewModelInject
import com.hadrmout.base.BaseViewModel
import com.hadrmout.data.DataCenterManager

class CategoriesViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<CategoriesNavigator>(dataCenterManager) {

}