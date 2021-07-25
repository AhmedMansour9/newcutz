package com.cutzegypt.ui.offers

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.cutzegypt.base.BaseViewModel
import com.cutzegypt.data.DataCenterManager
import com.cutzegypt.data.remote.model.ProductsResponse
import com.cutzegypt.ui.bottomnavigate.BottomNavigateActivity
import com.cutzegypt.ui.myaccount.MyAccountNavigator
import java.util.*
import kotlin.collections.HashMap

class OffersViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<MyAccountNavigator>(dataCenterManager) {
    var Lang = MutableLiveData<String>()
    var token  = MutableLiveData<String>()
    var lat = MutableLiveData<String>()
    var lng = MutableLiveData<String>()



    var listData = Pager(PagingConfig(pageSize = 10)) {
        ProductsPagination(dataCenterManager,token.value.toString(),
            lat.value.toString(),
            lng.value.toString())
    }.flow
}
class ProductsPagination constructor(
    dataCenterManager: DataCenterManager, token:String, lat: String, lng: String
) :
    PagingSource<Int, ProductsResponse.Data>() {
    var dataCenterManager: DataCenterManager
    var token :String
    var lat: String
    var lng: String

    init {
        this.dataCenterManager = dataCenterManager
        this.token=token
        this.lat=lat
        this.lng=lng
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductsResponse.Data> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            var hashMap = HashMap<String, String>()
            hashMap.put("lat",lat)
            hashMap.put("lng",lng)
         var lang=Locale.getDefault().getLanguage()
            val response = dataCenterManager.getOffers(BottomNavigateActivity.Lang!!,"Bearer $token",hashMap)

            val responseData = mutableListOf<ProductsResponse.Data>()
            val data = response.body()!!.data
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
            if (data.size>0){
                return LoadResult.Page(
                    data = responseData,
                    prevKey = prevKey,
                    nextKey = null)
            }else
                return LoadResult.Error(IllegalAccessException())

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}