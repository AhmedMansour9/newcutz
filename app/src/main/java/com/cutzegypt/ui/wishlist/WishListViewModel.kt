package com.cutzegypt.ui.wishlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cutzegypt.base.BaseViewModel
import com.cutzegypt.data.DataCenterManager
import com.cutzegypt.data.remote.model.ProductsResponse
import com.cutzegypt.ui.bottomnavigate.BottomNavigateActivity
import com.cutzegypt.ui.myaccount.MyAccountNavigator
import com.cutzegypt.utils.Resource
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class WishListViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<MyAccountNavigator>(dataCenterManager) {


    private val _productsResponse = MutableLiveData<Resource<ProductsResponse>>()
    val wishlistResponse: LiveData<Resource<ProductsResponse>>
        get() = _productsResponse



    fun getCart(token: String?) {

        _productsResponse.postValue(Resource.loading(null))
        dataCenterManager.getWishList(BottomNavigateActivity.Lang!! ,"Bearer $token")
            .enqueue(object :
                Callback, retrofit2.Callback<ProductsResponse> {
                override fun onResponse(
                    call: Call<ProductsResponse>,
                    response: Response<ProductsResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == true) {
                            _productsResponse.postValue(Resource.success(response.body()))
                        } else {
                            _productsResponse.postValue(
                                Resource.error(
                                    response.body()?.error.toString(),
                                    null
                                )
                            )
                        }
                    } else
                        _productsResponse.postValue(Resource.error(response.message(), null))
                }
                override fun onFailure(call: Call<ProductsResponse>, t: Throwable) {
                    _productsResponse.postValue(Resource.error(t.message.toString(), null))
                }
            })
    }


}