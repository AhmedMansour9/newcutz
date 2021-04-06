package com.cutz.ui.offers

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.cutz.base.BaseViewModel
import com.cutz.data.DataCenterManager
import com.cutz.data.remote.model.ProductsResponse
import com.cutz.ui.bottomnavigate.BottomNavigateActivity
import com.cutz.ui.myaccount.MyAccountNavigator
import java.util.*
import kotlin.collections.HashMap

class OffersViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<MyAccountNavigator>(dataCenterManager) {
    var Lang = MutableLiveData<String>()
    var token  = MutableLiveData<String>()

    var listData = Pager(PagingConfig(pageSize = 10)) {
        ProductsPagination(dataCenterManager,token.value.toString())
    }.flow
}
class ProductsPagination constructor(
    dataCenterManager: DataCenterManager, token:String
) :
    PagingSource<Int, ProductsResponse.Data>() {
    var dataCenterManager: DataCenterManager
    var token :String

    init {
        this.dataCenterManager = dataCenterManager
        this.token=token
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductsResponse.Data> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            var hashMap = HashMap<String, String>()

         var lang=Locale.getDefault().getLanguage()
            val response = dataCenterManager.getOffers(BottomNavigateActivity.Lang!!,"Bearer $token")

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