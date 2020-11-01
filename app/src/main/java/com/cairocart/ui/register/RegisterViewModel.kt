package com.cairocart.ui.register

import android.util.Log
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cairocart.base.BaseViewModel
import com.cairocart.data.DataCenterManager
import com.cairocart.data.remote.model.AccountResponse
import com.cairocart.data.remote.model.RegisterRequest
import com.cairocart.utils.Resource
import kotlinx.coroutines.launch

class RegisterViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<RegisterNavigator>(dataCenterManager) {


    var customer: RegisterRequest.Customer = RegisterRequest.Customer()
    var registerRequest: RegisterRequest = RegisterRequest()
    private val _accountResponse = MutableLiveData<Resource<AccountResponse>>()
    val accountResponse: LiveData<Resource<AccountResponse>>
        get() = _accountResponse

    fun register() {
        registerRequest.customer = customer
        if (!registerRequest.empty()) {
            viewModelScope.launch {
                _accountResponse.postValue(Resource.loading(null))
                dataCenterManager.newAccount(registerRequest)
                    .let {
                        Log.d("RegisterFragment", "register: "+it.code())

                        if (it.isSuccessful && it.code() == 200) {
                            dataCenterManager.dataSourcePrefrences()
                                .edit { preferences ->
                                    preferences[preferencesKey<Boolean>("sign_in")] = true
                                }

                            dataCenterManager.dataSourcePrefrences().edit { preferences ->
                                preferences[preferencesKey<String>("access_token")] =
                                    it.body()?.data?.accountToken!!
                            }

                            _accountResponse.postValue(Resource.success(it.body()))
                        } else _accountResponse.postValue(Resource.error(it.message(), null))
                    }
            }
        }
    }

}

