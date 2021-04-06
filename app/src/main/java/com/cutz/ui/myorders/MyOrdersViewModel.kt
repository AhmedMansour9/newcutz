package com.cutz.ui.myorders

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cutz.base.BaseViewModel
import com.cutz.data.DataCenterManager
import com.cutz.data.remote.model.MyOrdersResponse
import com.cutz.ui.bottomnavigate.BottomNavigateActivity
import com.cutz.ui.myaccount.MyAccountNavigator
import com.cutz.utils.Resource
import retrofit2.Call
import retrofit2.Response
import java.util.*
import javax.security.auth.callback.Callback
import kotlin.collections.HashMap

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