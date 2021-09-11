package com.hadrmout.ui.onboarding

import androidx.hilt.lifecycle.ViewModelInject
import com.hadrmout.base.BaseViewModel
import com.hadrmout.data.DataCenterManager

class OnBoardingViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any>(dataCenterManager) {


}