package com.cairocart.ui.bottomnavigate

import androidx.hilt.lifecycle.ViewModelInject
import com.cairocart.base.BaseViewModel
import com.cairocart.data.DataCenterManager

class BottomViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<BottomNavigator>(dataCenterManager) {


}