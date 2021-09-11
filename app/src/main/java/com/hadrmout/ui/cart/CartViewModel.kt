package com.hadrmout.ui.cart

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadrmout.base.BaseViewModel
import com.hadrmout.data.DataCenterManager
import com.hadrmout.data.remote.model.*
import com.hadrmout.ui.bottomnavigate.BottomNavigateActivity
import com.hadrmout.ui.myaccount.MyAccountNavigator
import com.hadrmout.utils.Resource
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback
import kotlin.collections.HashMap

class CartViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<MyAccountNavigator>(dataCenterManager) {

    private val _cartResponse = MutableLiveData<Resource<ListCartResponse>>()
    val cartResponse: LiveData<Resource<ListCartResponse>>
        get() = _cartResponse


    private val _editCartResponse = MutableLiveData<Resource<AddToCartResponse>>()
    val editCartResponse: LiveData<Resource<AddToCartResponse>>
        get() = _editCartResponse

    private val _deleteCartResponse = MutableLiveData<Resource<AddToCartResponse>>()
    val deleteCartResponse: LiveData<Resource<AddToCartResponse>>
        get() = _deleteCartResponse


    fun getCart(token: String) {

        _cartResponse.postValue(Resource.loading(null))
        dataCenterManager.getListCart(BottomNavigateActivity.Lang!! , "Bearer $token")
            .enqueue(object :
                Callback, retrofit2.Callback<ListCartResponse> {
                override fun onResponse(
                    call: Call<ListCartResponse>,
                    response: Response<ListCartResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == true) {
                            _cartResponse.postValue(Resource.success(response.body()))
                        } else {
                            _cartResponse.postValue(
                                Resource.error(
                                    response.body()?.error.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _cartResponse.postValue(Resource.error(response.message(), null))
                }
                override fun onFailure(call: Call<ListCartResponse>, t: Throwable) {
                    _cartResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }


    fun editCart( token: String?,cart_id:String,quantity:String) {
        _editCartResponse.postValue(Resource.loading(null))
        var map=HashMap<String,String>()
        map.put("_method","put")
        map.put("qty",quantity)

        dataCenterManager.editCart( cart_id,"Bearer $token",map).enqueue(
            object : Callback, retrofit2.Callback<AddToCartResponse> {
                override fun onResponse(
                    call: Call<AddToCartResponse>,
                    response: Response<AddToCartResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == true) {
                            _editCartResponse.postValue(Resource.success(response.body()))
                        } else {
                            _editCartResponse.postValue(
                                Resource.error(
                                    response.body()?.error.toString(),
                                    null
                                )
                            )
                        }
                    } else _editCartResponse.postValue(
                        Resource.error(
                            response.errorBody().toString(), null
                        )
                    )

                }

                override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {
                    _editCartResponse.postValue(Resource.error(t.message.toString(), null))
                }
            }
        )

    }



    fun deleteCart(id:String,token: String?) {
        _deleteCartResponse.postValue(Resource.loading(null))
        val hashMap=HashMap<String,String>()
//            hashMap.put("cart_id",id)

        dataCenterManager.deleteCart(id,hashMap,"Bearer $token").enqueue(
            object : Callback, retrofit2.Callback<AddToCartResponse> {
                override fun onResponse(
                    call: Call<AddToCartResponse>,
                    response: Response<AddToCartResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == true) {
                            _editCartResponse.postValue(Resource.success(response.body()))
                        } else {
                            _deleteCartResponse.postValue(
                                Resource.error(
                                    response.body()?.error.toString(),
                                    null
                                )
                            )
                        }
                    } else _deleteCartResponse.postValue(
                        Resource.error(
                            response.errorBody().toString(), null
                        )
                    )

                }

                override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {
                    _deleteCartResponse.postValue(Resource.error(t.message.toString(), null))
                }
            }
        )

    }



}