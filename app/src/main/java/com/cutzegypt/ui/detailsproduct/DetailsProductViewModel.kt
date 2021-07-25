package com.cutzegypt.ui.detailsproduct

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cutzegypt.base.BaseViewModel
import com.cutzegypt.data.DataCenterManager
import com.cutzegypt.data.remote.model.*
import com.cutzegypt.ui.bottomnavigate.BottomNavigateActivity
import com.cutzegypt.utils.Resource
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback
import kotlin.collections.HashMap

class DetailsProductViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<DetailsProductNavigtor>(dataCenterManager) {


    private val _addFavouritResponse = MutableLiveData<Resource<AddToFavouritResponse>>()
    val addFavouritResponse: LiveData<Resource<AddToFavouritResponse>>
        get() = _addFavouritResponse

    private val _removeFavouritResponse = MutableLiveData<Resource<AddToFavouritResponse>>()
    val removeFavouritResponse: LiveData<Resource<AddToFavouritResponse>>
        get() = _removeFavouritResponse


    private val _addToCartResponse = MutableLiveData<Resource<AddToCartResponse>>()
    val addCartResponse: LiveData<Resource<AddToCartResponse>>
        get() = _addToCartResponse

    private val _relatedResponse = MutableLiveData<Resource<ProductsResponse>>()
    val relatedResponse: LiveData<Resource<ProductsResponse>>
        get() = _relatedResponse



    fun getRelated(id:String,token: String?) {

        _relatedResponse.postValue(Resource.loading(null))
        var map=HashMap<String,String>()
        map.put("product_id",id)
        dataCenterManager.getRelated(BottomNavigateActivity.Lang!! ,"Bearer $token",map)
            .enqueue(object :
                Callback, retrofit2.Callback<ProductsResponse> {
                override fun onResponse(
                    call: Call<ProductsResponse>,
                    response: Response<ProductsResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == true) {
                            _relatedResponse.postValue(Resource.success(response.body()))
                        } else {
                            _relatedResponse.postValue(
                                Resource.error(
                                    response.body()?.error.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _relatedResponse.postValue(Resource.error(response.message(), null))
                }
                override fun onFailure(call: Call<ProductsResponse>, t: Throwable) {
                    _relatedResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }


     fun addFavourit(id:String,token:String) {
        _addFavouritResponse.postValue(Resource.loading(null))
        dataCenterManager.addFavourit(id, "Bearer $token").enqueue(
            object : Callback, retrofit2.Callback<AddToFavouritResponse> {
                override fun onResponse(
                    call: Call<AddToFavouritResponse>,
                    response: Response<AddToFavouritResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == true) {
                            _addFavouritResponse.postValue(Resource.success(response.body()))
                        } else {
                            _addFavouritResponse.postValue(
                                Resource.error(
                                    response.body()?.error.toString(),
                                    null
                                )
                            )
                        }
                    } else _addFavouritResponse.postValue(
                        Resource.error(
                            response.errorBody().toString(), null
                        )
                    )

                }

                override fun onFailure(call: Call<AddToFavouritResponse>, t: Throwable) {
                    _addFavouritResponse.postValue(Resource.error(t.message.toString(), null))
                }
            }
        )

    }


     fun removeFavourit(id:String,token:String) {
        _removeFavouritResponse.postValue(Resource.loading(null))
        dataCenterManager.removeFavourit(id, "Bearer $token").enqueue(
            object : Callback, retrofit2.Callback<AddToFavouritResponse> {
                override fun onResponse(
                    call: Call<AddToFavouritResponse>,
                    response: Response<AddToFavouritResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == true) {
                            _removeFavouritResponse.postValue(Resource.success(response.body()))
                        } else {
                            _removeFavouritResponse.postValue(
                                Resource.error(
                                    response.body()?.error.toString(),
                                    null
                                )
                            )
                        }
                    } else _removeFavouritResponse.postValue(
                        Resource.error(
                            response.errorBody().toString(), null
                        )
                    )

                }

                override fun onFailure(call: Call<AddToFavouritResponse>, t: Throwable) {
                    _removeFavouritResponse.postValue(Resource.error(t.message.toString(), null))
                }
            }
        )

    }





    fun addToCart(token:String,request:RequestAddToCartResponse) {
        _addToCartResponse.postValue(Resource.loading(null))
        dataCenterManager.addToCart("Bearer $token",request).enqueue(
            object : Callback, retrofit2.Callback<AddToCartResponse> {
                override fun onResponse(
                    call: Call<AddToCartResponse>,
                    response: Response<AddToCartResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == true) {
                            _addToCartResponse.postValue(Resource.success(response.body()))
                        } else {
                            _addToCartResponse.postValue(
                                Resource.error(
                                    response.body()?.error.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _addToCartResponse.postValue(
                        Resource.error(
                            response.errorBody().toString(), null
                        )
                    )

                }

                override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {
                    _addToCartResponse.postValue(Resource.error(t.message.toString(), null))
                }
            }
        )
    }


}