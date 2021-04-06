package com.cutz.ui.phone

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cutz.base.BaseViewModel
import com.cutz.data.DataCenterManager
import com.cutz.data.remote.model.AccountResponse
import com.cutz.data.remote.model.LoginRequest
import com.cutz.data.remote.model.VerifyResponse
import com.cutz.ui.login.LoginNavigator
import com.cutz.utils.Resource
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class UpdatePhoneViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<LoginNavigator>(dataCenterManager) {


    private val _accountResponse = MutableLiveData<Resource<AccountResponse>>()
    val accountResponse: LiveData<Resource<AccountResponse>>
        get() = _accountResponse

    private val _checkPhoneResponse = MutableLiveData<Resource<VerifyResponse>>()
    val checkphoneResponse: LiveData<Resource<VerifyResponse>>
        get() = _checkPhoneResponse


    fun verify(phone: String) {
        _accountResponse.postValue(Resource.loading(null))
        var map = HashMap<String, String>()
        map.put("phone", phone)
        dataCenterManager.userVerify(map).enqueue(object :
            Callback, retrofit2.Callback<AccountResponse> {
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
                    _accountResponse.postValue(Resource.error(response.body()?.error.toString(), null))
            }


            override fun onFailure(call: Call<AccountResponse>, t: Throwable) {
                _accountResponse.postValue(Resource.error(t.message.toString(), null))

            }
        })
}

    fun checkPhone(phone: String) {

        _checkPhoneResponse.postValue(Resource.loading(null))
        var map = HashMap<String, String>()
        map.put("phone", phone)
        dataCenterManager.checkPhone(map).enqueue(object :
            Callback, retrofit2.Callback<VerifyResponse> {
            override fun onResponse(
                call: Call<VerifyResponse>,
                response: Response<VerifyResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {
                        _checkPhoneResponse.postValue(Resource.success(response.body()))

                    } else {
                        _checkPhoneResponse.postValue(Resource.error(response.body()?.error.toString(), null))
                    }
                } else
                    _checkPhoneResponse.postValue(Resource.error(response.message().toString(), null))
            }
            override fun onFailure(call: Call<VerifyResponse>, t: Throwable) {
                _checkPhoneResponse.postValue(Resource.error(t.message.toString(), null))

            }
        })
    }

    fun resendPassword(phone: String,password: String) {
        _accountResponse.postValue(Resource.loading(null))
        var map = HashMap<String, String>()
        map.put("phone", phone)
        map.put("password", password)
        map.put("password_confirmation", password)

        dataCenterManager.resendPassword(map).enqueue(object :
            Callback, retrofit2.Callback<AccountResponse> {
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