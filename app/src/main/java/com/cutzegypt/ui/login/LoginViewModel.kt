package com.cutzegypt.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cutzegypt.base.BaseViewModel
import com.cutzegypt.data.DataCenterManager
import com.cutzegypt.data.remote.model.AccountResponse
import com.cutzegypt.data.remote.model.LoginRequest
import com.cutzegypt.utils.Resource
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class LoginViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<LoginNavigator>(dataCenterManager) {

    var loginRequest: LoginRequest = LoginRequest()

    private val _accountResponse = MutableLiveData<Resource<AccountResponse>>()
    val accountResponse: LiveData<Resource<AccountResponse>>
        get() = _accountResponse

    fun login() {
        if (!loginRequest.empty()) {
//            viewModelScope.launch {
            _accountResponse.postValue(Resource.loading(null))
            dataCenterManager.loginAccount(loginRequest)
                .enqueue(object : Callback, retrofit2.Callback<AccountResponse> {
                    override fun onResponse(
                        call: Call<AccountResponse>,
                        response: Response<AccountResponse>
                    ) {
                        if (response.isSuccessful) {
                            if (response.body()?.status == true) {
                                _accountResponse.postValue(Resource.success(response.body()))

                            } else {
                                _accountResponse.postValue(Resource.error(response.body()?.error.toString(), null))
                            }
                        } else
                            _accountResponse.postValue(
                                Resource.error(
                                    response.code().toString(),
                                    null
                                )
                            )
                    }


                    override fun onFailure(call: Call<AccountResponse>, t: Throwable) {
                        _accountResponse.postValue(Resource.error(t.message.toString(), null))

                    }
                })

        }
    }


    fun loginSocial(social_id: String?, email: String?, full_name: String?) {
//            viewModelScope.launch {
        _accountResponse.postValue(Resource.loading(null))
        var map = HashMap<String, String>()
        if (social_id != null) {
            map.put("social_id", social_id)
        }
        if (email != null) {
            map.put("email", email)
        }
        if (full_name != null) {
            map.put("full_name", full_name)
        }

        map.put("deviceType", "android")

        dataCenterManager.userLoginSocial(map)
            .enqueue(object : Callback, retrofit2.Callback<AccountResponse> {
                override fun onResponse(
                    call: Call<AccountResponse>,
                    response: Response<AccountResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == true) {
                            _accountResponse.postValue(Resource.success(response.body()))

                        } else {
                            _accountResponse.postValue(Resource.error(response.body()?.error.toString(), null))
                        }
                    } else
                        _accountResponse.postValue(Resource.error(response.code().toString(), null))
                }


                override fun onFailure(call: Call<AccountResponse>, t: Throwable) {
                    _accountResponse.postValue(Resource.error(t.message.toString(), null))

                }
            })
    }

}