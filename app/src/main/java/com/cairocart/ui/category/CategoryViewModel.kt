package com.cairocart.ui.category

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cairocart.base.BaseViewModel
import com.cairocart.data.DataCenterManager
import com.cairocart.data.remote.model.CategoryResponse
import com.cairocart.utils.Resource
import kotlinx.coroutines.launch

class CategoryViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any?>(dataCenterManager) {

    private val _categoryResponse = MutableLiveData<Resource<CategoryResponse>>()
    val categoryResponse: LiveData<Resource<CategoryResponse>>
        get() = _categoryResponse

    init {
        getCategory()
    }

    fun getCategory() {
        viewModelScope.launch {
            dataCenterManager.getCategories("en").let {
                if (it.isSuccessful && it.code() == 200) {
                    _categoryResponse.postValue(Resource.success(it.body()))
                } else _categoryResponse.postValue(Resource.error(it.message(), null))
            }
        }
    }


}