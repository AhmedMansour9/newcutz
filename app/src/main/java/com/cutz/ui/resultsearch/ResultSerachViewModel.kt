package com.cutz.ui.resultsearch

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.cutz.base.BaseViewModel
import com.cutz.data.DataCenterManager
import com.cutz.data.remote.model.FilterModel
import com.cutz.data.remote.model.ProductsResponse
import com.cutz.ui.bottomnavigate.BottomNavigateActivity
import com.cutz.ui.productsbyId.ProductByIdNavigator
import javax.inject.Singleton

@Singleton
class ResultSerachViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<ProductByIdNavigator>(dataCenterManager) {

    var filter = MutableLiveData<FilterModel>()




    var checkChanges = MutableLiveData<Boolean>()
    lateinit var Lang : String
    var userId = MutableLiveData<String>()
    var searchValue = MutableLiveData<String>()

    val changeId: LiveData<String>
        get() = searchValue

    init {
        if (!changeId.equals(searchValue)) {
            checkChanges.value = true
        }

    }


    var listData = Pager(PagingConfig(pageSize = 10)) {
        ProductsPagination(
            dataCenterManager,
            searchValue.value.toString(),
            userId.value.toString(),
            Lang=BottomNavigateActivity.Lang!!
        )
    }.flow

}

class ProductsPagination constructor(
    dataCenterManager: DataCenterManager, searchValue: String, userId: String, Lang: String
) :
    PagingSource<Int, ProductsResponse.Data>() {
    var searchValue = searchValue
    var dataCenterManager: DataCenterManager
    var lang: String
    var userId: String

    init {
        this.searchValue = searchValue
        this.lang = Lang
        this.dataCenterManager = dataCenterManager
        this.userId = userId
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductsResponse.Data> {

        try {
            val currentLoadingPageKey = params.key ?: 1
            var hashMap = HashMap<String, String>()
            hashMap.put("key", searchValue)
            hashMap.put("page", currentLoadingPageKey.toString())
            val response = dataCenterManager.fetchSearchProducts(
                lang, "Bearer $userId", hashMap  )

            val responseData = mutableListOf<ProductsResponse.Data>()
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
