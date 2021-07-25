package com.cutzegypt.ui.category

import androidx.hilt.lifecycle.ViewModelInject
import com.cutzegypt.base.BaseViewModel
import com.cutzegypt.data.DataCenterManager

class CategoriesViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<CategoriesNavigator>(dataCenterManager) {

}