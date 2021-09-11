package com.hadrmout.ui.myorders

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadrmout.base.BaseViewModel
import com.hadrmout.data.DataCenterManager
import com.hadrmout.data.remote.model.MyOrdersResponse
import com.hadrmout.ui.bottomnavigate.BottomNavigateActivity
import com.hadrmout.ui.myaccount.MyAccountNavigator
import com.hadrmout.utils.Resource
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MyOrdersViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<MyAccountNavigator>(dataCenterManager) {

    private val _ordersResponse = MutableLiveData<Resource<MyOrdersResponse>>()
    val ordersResponse: LiveData<Resource<MyOrdersResponse>>
        get() = _ordersResponse


    fun getOrders(token: String?) {

        _ordersResponse.postValue(Resource.loading(null))
        dataCenterManager.getOrders(BottomNavigateActivity.Lang!! , "Bearer $token")
            .enqueue(object :
                Callback, retrofit2.Callback<MyOrdersResponse> {
                override fun onResponse(
                    call: Call<MyOrdersResponse>,
                    response: Response<MyOrdersResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == true) {
                            _ordersResponse.postValue(Resource.success(response.body()))
                        } else {
                            _ordersResponse.postValue(
                                Resource.error(
                                    response.body()?.error.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _ordersResponse.postValue(Resource.error(response.message(), null))
                }
                override fun onFailure(call: Call<MyOrdersResponse>, t: Throwable) {
                    _ordersResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }


}