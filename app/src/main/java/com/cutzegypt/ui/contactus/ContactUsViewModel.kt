package com.cutzegypt.ui.contactus

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cutzegypt.base.BaseViewModel
import com.cutzegypt.data.DataCenterManager
import com.cutzegypt.data.remote.model.DefultResponse
import com.cutzegypt.data.remote.model.RequestContactUs
import com.cutzegypt.ui.register.RegisterNavigator
import com.cutzegypt.utils.Resource
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ContactUsViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<RegisterNavigator>(dataCenterManager) {


    var customer: RequestContactUs = RequestContactUs()
    var registerRequest: RequestContactUs = RequestContactUs()
    private val _accountResponse = MutableLiveData<Resource<DefultResponse>>()
    val accountResponse: LiveData<Resource<DefultResponse>>
        get() = _accountResponse

    fun contactus() {
        registerRequest = customer
        if (!registerRequest.empty()) {
            _accountResponse.postValue(Resource.loading(null))
//            viewModelScope.launch {
            dataCenterManager.contactUs(registerRequest)
                .enqueue(object : Callback, retrofit2.Callback<DefultResponse> {
                    override fun onResponse(
                        call: Call<DefultResponse>,
                        response: Response<DefultResponse>
                    ) {
                        if(response.isSuccessful){
                            if (response.body()?.status == true) {
                                _accountResponse.postValue(Resource.success(response.body()))

                            } else {
                                _accountResponse.postValue(Resource.error(response.body()?.error.toString(), null))
                            }
                        }else
                            _accountResponse.postValue(Resource.error(response.message(), null))



                    }

                    override fun onFailure(call: Call<DefultResponse>, t: Throwable) {
                        _accountResponse.postValue(Resource.error(t.message.toString(), null))
                    }
                })
        }
    }

}