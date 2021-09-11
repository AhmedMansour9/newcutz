package com.hadrmout.ui.address_profile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadrmout.base.BaseViewModel
import com.hadrmout.data.DataCenterManager
import com.hadrmout.data.remote.model.*
import com.hadrmout.ui.detailsproduct.DetailsProductNavigtor
import com.hadrmout.utils.Resource
import com.hadrmout.utils.SingleLiveEvent
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AddreeViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<DetailsProductNavigtor>(dataCenterManager) {

    var itemPositionCountry = MutableLiveData<Int>()
    var itemPositionCity = MutableLiveData<Int>()
    var itemPositionStates = MutableLiveData<Int>()

    private val _countriesResponse = MutableLiveData<Resource<CountriesResponse>>()
    val countriesResponse: LiveData<Resource<CountriesResponse>>
        get() = _countriesResponse





    private val _createAddressResponse = SingleLiveEvent<Resource<CreateAddressResponse>>(null)
    val createOrderResponse: SingleLiveEvent<Resource<CreateAddressResponse>>
        get() = _createAddressResponse


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



    fun createOrder( token: String?,hashMap:HashMap<String,String>) {
        _createAddressResponse.postValue(Resource.loading(null))

        dataCenterManager.createAddress(hashMap, "Bearer $token").enqueue(
            object : Callback, retrofit2.Callback<CreateAddressResponse> {
                override fun onResponse(
                    call: Call<CreateAddressResponse>,
                    response: Response<CreateAddressResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == true) {
                            _createAddressResponse.postValue(Resource.success(response.body()))
                        } else {
                            _createAddressResponse.postValue(
                                Resource.error(
                                    response.body()?.error.toString(),
                                    null
                                )
                            )
                        }
                    } else _createAddressResponse.postValue(
                        Resource.error(
                            response.errorBody().toString(), null
                        )
                    )
                }

                override fun onFailure(call: Call<CreateAddressResponse>, t: Throwable) {
                    _createAddressResponse.postValue(Resource.error(t.message.toString(), null))
                }
            }
        )
    }
}