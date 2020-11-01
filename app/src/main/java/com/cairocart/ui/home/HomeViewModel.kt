package com.cairocart.ui.home

import android.util.Log
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.preferencesKey
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cairocart.base.BaseViewModel
import com.cairocart.data.DataCenterManager
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<HomeNavigator>(dataCenterManager) {


}