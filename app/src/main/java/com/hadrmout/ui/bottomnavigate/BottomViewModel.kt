package com.hadrmout.ui.bottomnavigate

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadrmout.base.BaseViewModel
import com.hadrmout.data.DataCenterManager
import com.hadrmout.data.remote.model.CategoriesResponse
import com.hadrmout.utils.Resource
import retrofit2.Call
import retrofit2.Response
import java.util.*
import javax.inject.Singleton
import javax.security.auth.callback.Callback


@Singleton
class BottomViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any?>(dataCenterManager) {

    private val _categoryResponse = MutableLiveData<Resource<CategoriesResponse>>()
    val categoryResponse: LiveData<Resource<CategoriesResponse>>
        get() = _categoryResponse


    var Lang = MutableLiveData<String>()
    var token = MutableLiveData<String>()

    init {
        Log.d("CategoryFragment", "ESLAM: ")



//        if (collectionResponse.value == null){
//            getCollections(BottomNavigateActivity.token!!)
//        }
    }

    fun getLang(): String {
        val currentLang: String = Locale.getDefault().getLanguage()
        return currentLang
    }

    fun getCategory(lang:String) {
        _categoryResponse.postValue(Resource.loading(null))
        var language= BottomNavigateActivity.Lang!!
        dataCenterManager.getCategories(lang)
            .enqueue(
                object : Callback, retrofit2.Callback<CategoriesResponse> {
                    override fun onResponse(
                        call: Call<CategoriesResponse>,
                        response: Response<CategoriesResponse>
                    ) {
                        if (response.isSuccessful) {
                            if (response.body()?.status == true) {
                                _categoryResponse.postValue(Resource.success(response.body()))
                            } else {
                                _categoryResponse.postValue(
                                    Resource.error(
                                        response.body()?.error.toString(),
                                        null
                                    )
                                )
                            }
                        } else _categoryResponse.postValue(
                            Resource.error(
                                response.errorBody().toString(), null
                            )
                        )

                    }

                    override fun onFailure(call: Call<CategoriesResponse>, t: Throwable) {
                        _categoryResponse.postValue(Resource.error(t.message.toString(), null))
                    }
                }
            )
    }




    }