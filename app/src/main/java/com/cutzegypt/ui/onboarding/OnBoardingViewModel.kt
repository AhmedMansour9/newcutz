package com.cutzegypt.ui.onboarding

import androidx.hilt.lifecycle.ViewModelInject
import com.cutzegypt.base.BaseViewModel
import com.cutzegypt.data.DataCenterManager

class OnBoardingViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any>(dataCenterManager) {


}