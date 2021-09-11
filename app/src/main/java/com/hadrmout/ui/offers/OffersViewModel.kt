package com.hadrmout.ui.offers

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.hadrmout.base.BaseViewModel
import com.hadrmout.data.DataCenterManager
import com.hadrmout.data.remote.model.ProductsResponse
import com.hadrmout.ui.bottomnavigate.BottomNavigateActivity
import com.hadrmout.ui.myaccount.MyAccountNavigator
import com.hadrmout.utils.Resource
import retrofit2.Call
import retrofit2.Response
import java.util.*
import javax.security.auth.callback.Callback
import kotlin.collections.HashMap

class OffersViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<MyAccountNavigator>(dataCenterManager) {
    var Lang = MutableLiveData<String>()
    var token = MutableLiveData<String>()
    var status = MutableLiveData<String>()
    var lat = MutableLiveData<String>()
    var lng = MutableLiveData<String>()




    private val _productsResponse = MutableLiveData<Resource<ProductsResponse>>()
    val productsResponse: LiveData<Resource<ProductsResponse>>
        get() = _productsResponse


    fun getProductsOffers(token: String,type:String) {
        var hashMap = HashMap<String, String>()
//            if(type.equals("sectionID")){
                hashMap.put("type", type)
//            }else if(type.equals("sub")){
//                hashMap.put("subCategory_id", cat_id)
//            }else {
//                hashMap.put("piece_id", cat_id)
//            }
        _productsResponse.postValue(Resource.loading(null))
        dataCenterManager.getOffers(BottomNavigateActivity.Lang!!, "Bearer $token",hashMap)
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