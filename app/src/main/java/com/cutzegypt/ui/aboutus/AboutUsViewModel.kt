package com.cutzegypt.ui.aboutus

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.cutzegypt.base.BaseViewModel
import com.cutzegypt.data.DataCenterManager
import com.cutzegypt.data.remote.model.AboutUsResponse
import com.cutzegypt.data.remote.model.FilterModel
import com.cutzegypt.ui.bottomnavigate.BottomNavigateActivity
import com.cutzegypt.ui.productsbyId.ProductByIdNavigator

class AboutUsViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<ProductByIdNavigator>(dataCenterManager) {

    var filter = MutableLiveData<FilterModel>()



    var Lang = MutableLiveData<String>()
    var userId = MutableLiveData<String>()



    var listData = Pager(PagingConfig(pageSize = 10)) {
        ProductsPagination(
            dataCenterManager,
            Lang=BottomNavigateActivity.Lang!!
        )
    }.flow.cachedIn(viewModelScope)

}

class ProductsPagination constructor(
    dataCenterManager: DataCenterManager, Lang: String
) :
    PagingSource<Int, AboutUsResponse.Data>() {
    var dataCenterManager: DataCenterManager
    var lang: String

    init {
        this.lang = Lang
        this.dataCenterManager = dataCenterManager
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AboutUsResponse.Data> {

        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = dataCenterManager.fetchAboutus(
                lang  )
            val responseData = mutableListOf<AboutUsResponse.Data>()
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