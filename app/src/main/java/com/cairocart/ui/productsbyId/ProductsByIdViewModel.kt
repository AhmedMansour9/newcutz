package com.cairocart.ui.productsbyId

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cairocart.base.BaseViewModel
import com.cairocart.data.DataCenterManager
import com.cairocart.data.remote.model.ProductsByIdResponse
import com.cairocart.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Singleton
class ProductsByIdViewModel  @ViewModelInject constructor(dataCenterManager: DataCenterManager,) :
    BaseViewModel<ProductByIdNavigator>(dataCenterManager) {

    private val _productsResponse = MutableLiveData<Resource<ProductsByIdResponse>>()
    val productResponse: LiveData<Resource<ProductsByIdResponse>>
        get() = _productsResponse

//    init {
//        if (productResponse.value == null)
//            getProducts()
//    }

    fun getProductsById(lang:String,catId:String) {
       var hashMap=HashMap<String,String>()
        hashMap.put("searchCriteria[filterGroups][0][filters][0][value]",catId)
        hashMap.put("searchCriteria[filterGroups][0][filters][0][field]","category_id")
        hashMap.put("searchCriteria[currentPage]","1")
        hashMap.put("searchCriteria[pageSize]","10")

//        viewModelScope.launch {
            _productsResponse.postValue(Resource.loading(null))
            dataCenterManager.getProductsById(lang,hashMap).let {
                if (it.isSuccessful && it.code() == 200) {
                    _productsResponse.postValue(Resource.success(it.body()))
                } else _productsResponse.postValue(Resource.error(it.message(), null))
            }
//        }
    }
}