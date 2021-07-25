package com.cutzegypt.ui.productsbyId

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.cutzegypt.base.BaseViewModel
import com.cutzegypt.data.DataCenterManager
import com.cutzegypt.data.remote.model.FilterModel
import com.cutzegypt.data.remote.model.ProductsResponse
import com.cutzegypt.ui.bottomnavigate.BottomNavigateActivity
import javax.inject.Singleton

@Singleton
class ProductsByIdViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<ProductByIdNavigator>(dataCenterManager) {

    var filter = MutableLiveData<FilterModel>()




    var checkChanges = MutableLiveData<Boolean>()
    var Lang = BottomNavigateActivity.Lang!!
    var userId = MutableLiveData<String>()
    var Id = MutableLiveData<String>()
    var type = MutableLiveData<String>()
    var lat = MutableLiveData<String>()
    var lng = MutableLiveData<String>()




    var listData = Pager(PagingConfig(pageSize = 10)) {
        ProductsPagination(
            dataCenterManager,
            type.value.toString(),
            Id.value.toString(),
            userId.value.toString(),
            Lang,
            lat.value.toString(),
            lng.value.toString()
        )
    }.flow

}

class ProductsPagination constructor(
    dataCenterManager: DataCenterManager,type:String, categoryId: String, userId: String, Lang: String, lat: String, lng: String
) :
    PagingSource<Int, ProductsResponse.Data>() {
    var cat_id = categoryId
    var dataCenterManager: DataCenterManager
    var lang: String
    var userId: String
    var type: String
    var lat: String
    var lng: String

    init {
        this.cat_id = categoryId
        this.lang = Lang
        this.dataCenterManager = dataCenterManager
        this.userId = userId
        this.type=type
        this.lat=lat
        this.lng=lng
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductsResponse.Data> {

        try {
            val currentLoadingPageKey = params.key ?: 1
            var hashMap = HashMap<String, String>()
            hashMap.put("page", currentLoadingPageKey.toString())
            if(type.equals("sectionID")){
                hashMap.put("section_id", cat_id)
            }else if(type.equals("sub")){
                hashMap.put("subCategory_id", cat_id)
            }else {
                hashMap.put("piece_id", cat_id)
            }
            val response = dataCenterManager.getProductsById(
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


//val listData = Pager(PagingConfig(pageSize = 10)) {
//    ProductsPagination(dataCenterManager)
//}.flow.cachedIn(viewModelScope)


//private val _productsResponse = MutableLiveData<Resource<List<ProductsByIdResponse.Data?>?>>()
//val productResponse: LiveData<Resource<List<ProductsByIdResponse.Data?>?>>
//    get() = _productsResponse
//
////    init {
////        if (productResponse.value == null)
////            getProducts()
////    }
//
//fun getProductsById(lang: String, catId: String,Page:String) {
//    var hashMap = HashMap<String, String>()
//    hashMap.put("searchCriteria[filterGroups][0][filters][0][value]", catId)
//    hashMap.put("searchCriteria[filterGroups][0][filters][0][field]", "category_id")
//    hashMap.put("searchCriteria[currentPage]", Page)
//    hashMap.put("searchCriteria[pageSize]", "10")
//
//    viewModelScope.launch {
//        _productsResponse.postValue(Resource.loading(null))
//        dataCenterManager.getProductsById(lang, hashMap).let {
//            if (it.isSuccessful && it.code() == 200) {
//                val data = it.body()?.data
//                _productsResponse.postValue(Resource.success(data))
//            } else _productsResponse.postValue(Resource.error(it.message(), null))
//        }
//    }
//}