package com.cutz.ui.coupon

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cutz.base.BaseViewModel
import com.cutz.data.DataCenterManager
import com.cutz.data.remote.model.AccountResponse
import com.cutz.data.remote.model.AddCouponResponse
import com.cutz.data.remote.model.LoginRequest
import com.cutz.ui.login.LoginNavigator
import com.cutz.utils.Resource
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AddCopounViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<LoginNavigator>(dataCenterManager) {


    private val _accountResponse = MutableLiveData<Resource<AddCouponResponse>>()
    val accountResponse: LiveData<Resource<AddCouponResponse>>
        get() = _accountResponse

    fun addCoupon(code: String, token: String) {
//            viewModelScope.launch {
        _accountResponse.postValue(Resource.loading(null))
        var map = HashMap<String, String>()
        map.put("code", code)
        dataCenterManager.addCoupon(map, "Bearer $token").enqueue(object :
            Callback, retrofit2.Callback<AddCouponResponse> {
            override fun onResponse(
                call: Call<AddCouponResponse>,
                response: Response<AddCouponResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {
                        _accountResponse.postValue(Resource.success(response.body()))

                    } else {
                        _accountResponse.postValue(
                            Resource.error(
                                response.body()?.error.toString(),
                                null
                            )
                        )
                    }
                } else
                    _accountResponse.postValue(Resource.error(response.message(), null))


            }


            override fun onFailure(call: Call<AddCouponResponse>, t: Throwable) {
                _accountResponse.postValue(Resource.error(t.message.toString(), null))

            }
        })

//                }

    }
}