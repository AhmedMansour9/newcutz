package com.cutz.ui.resultfilter

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.cutz.base.BaseViewModel
import com.cutz.data.DataCenterManager
import com.cutz.data.remote.model.ProductsResponse
import com.cutz.ui.productsbyId.ProductByIdNavigator

class ResultFitertionViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<ProductByIdNavigator>(dataCenterManager) {
    var checkChanges = MutableLiveData<Boolean>()
    var Lang = MutableLiveData<String>()
    var userId = MutableLiveData<String>()
    var category_Id = MutableLiveData<String>()
    var brand_Id = MutableLiveData<String>()
    var min_Price = MutableLiveData<String>()
    var max_Price = MutableLiveData<String>()



    var listData = Pager(PagingConfig(pageSize = 10)) {
        ProductsPagination(
            dataCenterManager,
            category_Id.value.toString(),
            userId.value.toString(),
            Lang.value.toString(),brand_Id.value.toString(),min_Price.value.toString(),max_Price.value.toString()
        )
    }.flow
    init {

    }

}

class ProductsPagination constructor(
    dataCenterManager: DataCenterManager, categoryId: String, userId: String, Lang: String,brand_Id:String?,min_Price:String?,max_Price:String?
) :
    PagingSource<Int, ProductsResponse.Data>() {
    var cat_id:String?= String()
    var brand_id :String?= String()
    var min_Price:String?= String()
    var max_Price :String?= String()

    var dataCenterManager: DataCenterManager
    var lang: String
    var userId: String

    init {
        this.brand_id = brand_Id
        this.min_Price = min_Price
        this.max_Price = max_Price
        this.cat_id = categoryId
        this.lang = Lang
        this.dataCenterManager = dataCenterManager
        this.userId = userId
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductsResponse.Data> {

        try {
            val currentLoadingPageKey = params.key ?: 1
            var hashMap = HashMap<String, String>()
            cat_id?.let { hashMap.put("searchCriteria[filterGroups][0][filters][0][value]", it) }
            hashMap.put("searchCriteria[filterGroups][0][filters][0][field]", "category_id")
            hashMap.put("searchCriteria[currentPage]", currentLoadingPageKey.toString())
            hashMap.put("searchCriteria[pageSize]", "10")
             Log.e("brand view model","  "+brand_id)
            if(!brand_id.isNullOrEmpty()){
                hashMap.put("searchCriteria[filterGroups][0][filters][1][field]", "manufacturer")
                brand_id?.let { hashMap.put("searchCriteria[filterGroups][0][filters][1][value]", it) }
            }


            if(!min_Price.isNullOrEmpty() ){
                hashMap.put("searchCriteria[filterGroups][0][filters][2][field]", "price")
                min_Price?.let { hashMap.put("searchCriteria[filterGroups][0][filters][2][value]", it) }
                hashMap.put("searchCriteria[filterGroups][0][filters][2][condition_type]", "moreq")
            }


            if(!max_Price.isNullOrEmpty()  ) {
                hashMap.put("searchCriteria[filterGroups][0][filters][3][field]", "price")
                max_Price?.let { hashMap.put("searchCriteria[filterGroups][0][filters][3][value]", it) }
                hashMap.put("searchCriteria[filterGroups][0][filters][3][condition_type]", "lteq")
            }


            val response = dataCenterManager.getProductsById(
                lang, "Bearer 5u1forfnoiuok9qtdaftqxtyd399bcsl", hashMap  )

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

