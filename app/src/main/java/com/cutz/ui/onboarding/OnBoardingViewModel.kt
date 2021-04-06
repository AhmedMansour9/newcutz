package com.cutz.ui.onboarding

import androidx.hilt.lifecycle.ViewModelInject
import com.cutz.base.BaseViewModel
import com.cutz.data.DataCenterManager

class OnBoardingViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any>(dataCenterManager) {


}