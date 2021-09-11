package com.hadrmout.ui.home

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadrmout.base.BaseViewModel
import com.hadrmout.data.DataCenterManager
import com.hadrmout.data.remote.model.BestSeller_Response
import com.hadrmout.data.remote.model.ProductsResponse
import com.hadrmout.data.remote.model.SectonsResponse
import com.hadrmout.ui.bottomnavigate.BottomNavigateActivity
import com.hadrmout.utils.Resource
import retrofit2.Call
import retrofit2.Response
import java.util.*
import javax.inject.Singleton
import javax.security.auth.callback.Callback
import kotlin.collections.HashMap

@Singleton
class HomeViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any?>(dataCenterManager) {

    private val _categoryResponse = MutableLiveData<Resource<SectonsResponse>>()
    val categoryResponse: LiveData<Resource<SectonsResponse>>
        get() = _categoryResponse

    private val _collectionResponse = MutableLiveData<Resource<ProductsResponse>>()
    val collectionResponse: LiveData<Resource<ProductsResponse>>
        get() = _collectionResponse

    private val _bestProductResponse = MutableLiveData<Resource<BestSeller_Response>>()
    val bestProductResponse: LiveData<Resource<BestSeller_Response>>
        get() = _bestProductResponse


    var Lang = MutableLiveData<String>()
    var token = MutableLiveData<String>()

    init {
        Log.d("CategoryFragment", "ESLAM: ")


    }



    fun getLang(): String {
        val currentLang: String = Locale.getDefault().getLanguage()
        return currentLang
    }

    fun getCategory(Lang: String, token: String, Status: String) {
        _categoryResponse.postValue(Resource.loading(null))
        var language = BottomNavigateActivity.Lang!!
        var hashMap = HashMap<String, String>()
        hashMap.put("type", Status)
        dataCenterManager.getCategories(BottomNavigateActivity.Lang!!, token, hashMap)
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


    fun getCollections(token:String,type:String) {
        _collectionResponse.postValue(Resource.loading(null))
        var map = HashMap<String, String>()
        map.put("type", type)
        dataCenterManager.fetchCollections(BottomNavigateActivity.Lang!!, "Bearer $token", map)
            .enqueue(
                object : Callback, retrofit2.Callback<ProductsResponse> {
                    override fun onResponse(
                        call: Call<ProductsResponse>,
                        response: Response<ProductsResponse>
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

                    override fun onFailure(call: Call<ProductsResponse>, t: Throwable) {
                        _collectionResponse.postValue(Resource.error(t.message.toString(), null))
                    }
                }
            )
    }

    var userId = MutableLiveData<String>()





}