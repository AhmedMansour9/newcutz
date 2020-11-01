package com.cairocart.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import com.cairocart.base.BaseViewModel
import com.cairocart.data.DataCenterManager

class HomeViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<HomeNavigator>(dataCenterManager) {


}