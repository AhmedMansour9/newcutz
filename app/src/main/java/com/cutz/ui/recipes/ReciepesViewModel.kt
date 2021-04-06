package com.cutz.ui.recipes

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.cutz.base.BaseViewModel
import com.cutz.data.DataCenterManager
import com.cutz.data.remote.model.BlogsResponse
import com.cutz.data.remote.model.FilterModel
import com.cutz.data.remote.model.ProductsResponse
import com.cutz.ui.bottomnavigate.BottomNavigateActivity
import com.cutz.ui.productsbyId.ProductByIdNavigator

class ReciepesViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<ProductByIdNavigator>(dataCenterManager) {

    var filter = MutableLiveData<FilterModel>()



    var Lang = BottomNavigateActivity.Lang!!
    var userId = MutableLiveData<String>()



    var listData = Pager(PagingConfig(pageSize = 10)) {
        ProductsPagination(
            dataCenterManager,
            Lang
        )
    }.flow.cachedIn(viewModelScope)

}

class ProductsPagination constructor(
    dataCenterManager: DataCenterManager, Lang: String
) :
    PagingSource<Int, BlogsResponse.Data>() {
    var dataCenterManager: DataCenterManager
    var lang: String

    init {
        this.lang = Lang
        this.dataCenterManager = dataCenterManager
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BlogsResponse.Data> {

        try {
            val currentLoadingPageKey = params.key ?: 1
            var hashMap = HashMap<String, String>()
            hashMap.put("page", currentLoadingPageKey.toString())
            val response = dataCenterManager.fetchBlogs(
                lang, hashMap  )
            val responseData = mutableListOf<BlogsResponse.Data>()
            val data = response.body()!!.data
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
            if (data.size>0){
                return LoadResult.Page(
                    data = responseData,
                    prevKey = prevKey,
                    nextKey = currentLoadingPageKey.plus(1))
            }else
                return LoadResult.Error(IllegalAccessException())

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}