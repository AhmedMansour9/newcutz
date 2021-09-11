package com.hadrmout.ui.createorder

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadrmout.base.BaseViewModel
import com.hadrmout.data.DataCenterManager
import com.hadrmout.data.remote.model.CountriesResponse
import com.hadrmout.data.remote.model.CreateOrderResponse
import com.hadrmout.data.remote.model.ListShippingMethod
import com.hadrmout.data.remote.model.RequestCreateOrder
import com.hadrmout.ui.detailsproduct.DetailsProductNavigtor
import com.hadrmout.utils.Resource
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class CreateOrderViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<DetailsProductNavigtor>(dataCenterManager) {

    var itemPositionCountry = MutableLiveData<Int>()
    var itemPositionCity = MutableLiveData<Int>()
    var itemPositionStates = MutableLiveData<Int>()

    private val _countriesResponse = MutableLiveData<Resource<CountriesResponse>>()
    val countriesResponse: LiveData<Resource<CountriesResponse>>
        get() = _countriesResponse

    private val _shippingResponse = MutableLiveData<Resource<ListShippingMethod>>()
    val shippingResponse: LiveData<Resource<ListShippingMethod>>
        get() = _shippingResponse


    private val _createOrderResponse = MutableLiveData<Resource<CreateOrderResponse>>()
    val createOrderResponse: LiveData<Resource<CreateOrderResponse>>
        get() = _createOrderResponse



    init {
        if (countriesResponse.value == null)

            getCountries()
    }

    fun getCountries() {
        _countriesResponse.postValue(Resource.loading(null))
        dataCenterManager.getCountries().enqueue(
            object : Callback, retrofit2.Callback<CountriesResponse> {
                override fun onResponse(
                    call: Call<CountriesResponse>,
                    response: Response<CountriesResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == true) {
                            _countriesResponse.postValue(Resource.success(response.body()))
                        } else {
                            _countriesResponse.postValue(
                                Resource.error(
                                    response.body()?.error.toString(),
                                    null
                                )
                            )
                        }
                    } else _countriesResponse.postValue(
                        Resource.error(
                            response.message().toString(), null
                        )
                    )

                }

                override fun onFailure(call: Call<CountriesResponse>, t: Throwable) {
                    _countriesResponse.postValue(Resource.error(t.message.toString(), null))
                }
            }
        )
    }


    fun createOrder(request: RequestCreateOrder, token:String?) {
        _createOrderResponse.postValue(Resource.loading(null))

        dataCenterManager.createOrder(request,"Bearer $token").enqueue(
            object : Callback, retrofit2.Callback<CreateOrderResponse> {
                override fun onResponse(
                    call: Call<CreateOrderResponse>,
                    response: Response<CreateOrderResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == true) {
                            _createOrderResponse.postValue(Resource.success(response.body()))
                        } else {
                            _createOrderResponse.postValue(
                                Resource.error(
                                    response.body()?.error.toString(),
                                    null))
                        }
                    } else _createOrderResponse.postValue(
                        Resource.error(
                            response.errorBody().toString(), null
                        )
                    )
                }
                override fun onFailure(call: Call<CreateOrderResponse>, t: Throwable) {
                    _createOrderResponse.postValue(Resource.error(t.message.toString(), null))
                }
            }
        )
    }

}