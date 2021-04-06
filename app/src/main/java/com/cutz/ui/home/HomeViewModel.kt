package com.cutz.ui.home

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cutz.base.BaseViewModel
import com.cutz.data.DataCenterManager
import com.cutz.data.remote.model.SectonsResponse
import com.cutz.data.remote.model.CollectionsResponse
import com.cutz.ui.bottomnavigate.BottomNavigateActivity
import com.cutz.utils.Resource
import retrofit2.Call
import retrofit2.Response
import java.util.*
import javax.inject.Singleton
import javax.security.auth.callback.Callback

@Singleton
class HomeViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any?>(dataCenterManager) {

    private val _categoryResponse = MutableLiveData<Resource<SectonsResponse>>()
    val categoryResponse: LiveData<Resource<SectonsResponse>>
        get() = _categoryResponse

    private val _collectionResponse = MutableLiveData<Resource<CollectionsResponse>>()
    val collectionResponse: LiveData<Resource<CollectionsResponse>>
        get() = _collectionResponse

    var Lang = MutableLiveData<String>()
    var token = MutableLiveData<String>()

    init {
        Log.d("CategoryFragment", "ESLAM: ")

        if (categoryResponse.value == null){
            getCategory()
        }

//        if (collectionResponse.value == null){
//            getCollections(BottomNavigateActivity.token!!)
//        }
    }

    fun getLang(): String {
        val currentLang: String = Locale.getDefault().getLanguage()
        return currentLang
    }

    fun getCategory() {
        _categoryResponse.postValue(Resource.loading(null))
        var language= BottomNavigateActivity.Lang!!
        dataCenterManager.getCategories(BottomNavigateActivity.Lang!!, "Bearer 5u1forfnoiuok9qtdaftqxtyd399bcsl")
            .enqueue(
                object : Callback, retrofit2.Callback<SectonsResponse> {
                    override fun onResponse(
                        call: Call<SectonsResponse>,
                        response: Response<SectonsResponse>
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

                    override fun onFailure(call: Call<SectonsResponse>, t: Throwable) {
                        _categoryResponse.postValue(Resource.error(t.message.toString(), null))
                    }
                }
            )
    }


    fun getCollections(token:String) {
        _collectionResponse.postValue(Resource.loading(null))
        dataCenterManager.fetchCollections(BottomNavigateActivity.Lang!!, "Bearer $token")
            .enqueue(
                object : Callback, retrofit2.Callback<CollectionsResponse> {
                    override fun onResponse(
                        call: Call<CollectionsResponse>,
                        response: Response<CollectionsResponse>
                    ) {
                        if (response.isSuccessful) {
                            if (response.body()?.status == true) {
                                _collectionResponse.postValue(Resource.success(response.body()))
                            } else {
                                _collectionResponse.postValue(
                                    Resource.error(
                                        response.body()?.error.toString(),
                                        null
                                    )
                                )
                            }
                        } else _collectionResponse.postValue(
                            Resource.error(
                                response.errorBody().toString(), null
                            )
                        )

                    }

                    override fun onFailure(call: Call<CollectionsResponse>, t: Throwable) {
                        _collectionResponse.postValue(Resource.error(t.message.toString(), null))
                    }
                }
            )
    }

}