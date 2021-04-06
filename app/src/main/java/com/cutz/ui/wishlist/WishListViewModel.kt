package com.cutz.ui.wishlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.cutz.ChangeLanguage
import com.cutz.base.BaseViewModel
import com.cutz.data.DataCenterManager
import com.cutz.data.remote.model.ListFavouritResponse
import com.cutz.data.remote.model.ProductsResponse
import com.cutz.ui.bottomnavigate.BottomNavigateActivity
import com.cutz.ui.myaccount.MyAccountNavigator
import com.cutz.utils.Resource
import retrofit2.Call
import retrofit2.Response
import java.util.*
import javax.security.auth.callback.Callback
import kotlin.collections.HashMap

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