package com.cairocart.ui.category

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cairocart.base.BaseViewModel
import com.cairocart.data.DataCenterManager
import com.cairocart.data.remote.model.CategoriesResponse
import com.cairocart.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Singleton
class CategoryViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager,) :
    BaseViewModel<Any?>(dataCenterManager) {

    private val _categoryResponse = MutableLiveData<Resource<CategoriesResponse>>()
    val categoryResponse: LiveData<Resource<CategoriesResponse>>
        get() = _categoryResponse

    init {
        Log.d("CategoryFragment", "ESLAM: ")
        if (categoryResponse.value == null)
            getCategory()
    }

    fun getCategory() {
        viewModelScope.launch {
            _categoryResponse.postValue(Resource.loading(null))
            dataCenterManager.getCategories("en").let {
                if (it.isSuccessful && it.code() == 200) {
                    _categoryResponse.postValue(Resource.success(it.body()))
                } else _categoryResponse.postValue(Resource.error(it.message(), null))
            }
        }
    }


}