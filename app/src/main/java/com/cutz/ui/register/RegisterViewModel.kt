package com.cutz.ui.register

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cutz.base.BaseViewModel
import com.cutz.data.DataCenterManager
import com.cutz.data.remote.model.AccountResponse
import com.cutz.data.remote.model.RegisterRequest
import com.cutz.utils.Resource
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class RegisterViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<RegisterNavigator>(dataCenterManager) {


    var customer: RegisterRequest = RegisterRequest()
    var registerRequest: RegisterRequest = RegisterRequest()
    private val _accountResponse = MutableLiveData<Resource<AccountResponse>>()
    val accountResponse: LiveData<Resource<AccountResponse>>
        get() = _accountResponse

    fun register() {
        registerRequest = customer
        if (!registerRequest.empty()) {
                _accountResponse.postValue(Resource.loading(null))
//            viewModelScope.launch {
                dataCenterManager.newAccount(registerRequest)
                    .enqueue(object : Callback, retrofit2.Callback<AccountResponse> {
                        override fun onResponse(
                            call: Call<AccountResponse>,
                            response: Response<AccountResponse>
                        ) {
                            if(response.isSuccessful){
                                if (response.body()?.status == true) {
                                    _accountResponse.postValue(Resource.success(response.body()))

                                } else {
                                    _accountResponse.postValue(Resource.error(response.body()?.error.toString(), null))

                                }
                            }else
                                _accountResponse.postValue(Resource.error(response.code().toString(), null))

                        }

                        override fun onFailure(call: Call<AccountResponse>, t: Throwable) {
                            _accountResponse.postValue(Resource.error(t.message.toString(), null))
                        }
                    })
        }
    }

}

