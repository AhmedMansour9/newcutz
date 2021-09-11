package com.hadrmout.ui.forgetpassword

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadrmout.base.BaseViewModel
import com.hadrmout.data.DataCenterManager
import com.hadrmout.data.remote.model.ForgetPasswordResponse
import com.hadrmout.ui.login.LoginNavigator
import com.hadrmout.utils.Resource
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ForgetPasswordViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<LoginNavigator>(dataCenterManager) {


    private val _accountResponse = MutableLiveData<Resource<ForgetPasswordResponse>>()
    val accountResponse: LiveData<Resource<ForgetPasswordResponse>>
        get() = _accountResponse

    fun forgetPassword(email: String) {
//            viewModelScope.launch {
        _accountResponse.postValue(Resource.loading(null))
        var map = HashMap<String, String>()
        map.put("email", email)
        dataCenterManager.forgetPassword(map).enqueue(object :
            Callback, retrofit2.Callback<ForgetPasswordResponse> {
            override fun onResponse(
                call: Call<ForgetPasswordResponse>,
                response: Response<ForgetPasswordResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {
                        _accountResponse.postValue(Resource.success(response.body()))

                    } else {
                        _accountResponse.postValue(
                            Resource.error(
                                response.body()?.message.toString(),
                                null
                            )
                        )
                    }
                } else
                    _accountResponse.postValue(Resource.error(response.message(), null))


            }


            override fun onFailure(call: Call<ForgetPasswordResponse>, t: Throwable) {
                _accountResponse.postValue(Resource.error(t.message.toString(), null))

            }
        })

//                }

    }
}