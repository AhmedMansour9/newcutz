package com.cairocart.ui.login

import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cairocart.base.BaseViewModel
import com.cairocart.data.DataCenterManager
import com.cairocart.data.remote.model.AccountResponse
import com.cairocart.data.remote.model.LoginRequest
import com.cairocart.utils.Resource
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<LoginNavigator>(dataCenterManager) {

    var loginRequest: LoginRequest = LoginRequest()


    private val _accountResponse = MutableLiveData<Resource<AccountResponse>>()
    val accountResponse: LiveData<Resource<AccountResponse>>
        get() = _accountResponse

    fun login() {
        if (!loginRequest.empty()) {
            viewModelScope.launch {
                _accountResponse.postValue(Resource.loading(null))
                dataCenterManager.loginAccount(loginRequest)
                    .let {
                        if (it.isSuccessful) {
                            dataCenterManager.dataSourcePrefrences()
                                .edit { preferences ->
                                    preferences[preferencesKey<Boolean>("sign_in")] = true
                                }
                            dataCenterManager.dataSourcePrefrences().edit { preferences ->
                                preferences[preferencesKey<AccountResponse>("accountResponse")] =
                                    it.body()!! // Save Account Response
                            }


                            _accountResponse.postValue(Resource.success(it.body()))
                        } else _accountResponse.postValue(Resource.error(it.message(), null))
                    }
            }

        }
    }

}