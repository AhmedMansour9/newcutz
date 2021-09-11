package com.hadrmout.ui.changelocation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadrmout.base.BaseViewModel
import com.hadrmout.data.DataCenterManager
import com.hadrmout.data.remote.model.BranchesResponse
import com.hadrmout.data.remote.model.NearestBranchResponse
import com.hadrmout.ui.bottomnavigate.BottomNavigateActivity
import com.hadrmout.utils.Resource
import retrofit2.Call
import retrofit2.Response
import java.util.*
import javax.security.auth.callback.Callback
import kotlin.collections.HashMap

class ChangeLocationViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<Any>(dataCenterManager) {

    var itemPositionBranches = MutableLiveData<Int>()

    private val _brachesResponse = MutableLiveData<Resource<BranchesResponse>>()
    val countriesResponse: LiveData<Resource<BranchesResponse>>
        get() = _brachesResponse

    private val _nearlocationResponse = MutableLiveData<Resource<NearestBranchResponse>>()
    val nearestResponse: LiveData<Resource<NearestBranchResponse>>
        get() = _nearlocationResponse

    init {
        if (countriesResponse.value == null)

            getBranches()
    }

    fun getBranches() {
        var language = Locale.getDefault().language
        if(language.isNullOrEmpty()){
            language=Locale.getDefault().displayLanguage
        }

        _brachesResponse.postValue(Resource.loading(null))
        dataCenterManager.getBranches(BottomNavigateActivity.Lang!!).enqueue(
            object : Callback, retrofit2.Callback<BranchesResponse> {
                override fun onResponse(
                    call: Call<BranchesResponse>,
                    response: Response<BranchesResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == true) {
                            _brachesResponse.postValue(Resource.success(response.body()))
                        } else {
                            _brachesResponse.postValue(
                                Resource.error(
                                    response.body()?.error.toString(),
                                    null
                                )
                            )
                        }
                    } else _brachesResponse.postValue(
                        Resource.error(
                            response.message().toString(), null
                        )
                    )

                }

                override fun onFailure(call: Call<BranchesResponse>, t: Throwable) {
                    _brachesResponse.postValue(Resource.error(t.message.toString(), null))
                }
            }
        )
    }


    fun getNearestBranches(lat:String,lng:String) {
        var language = Locale.getDefault().language
        if(language.isNullOrEmpty()){
            language=Locale.getDefault().displayLanguage
        }
        var map =HashMap<String,String>()

        _nearlocationResponse.postValue(Resource.loading(null))
        dataCenterManager.getNearestLocation(BottomNavigateActivity.Lang!!,map).enqueue(
            object : Callback, retrofit2.Callback<NearestBranchResponse> {
                override fun onResponse(
                    call: Call<NearestBranchResponse>,
                    response: Response<NearestBranchResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == true) {
                            _nearlocationResponse.postValue(Resource.success(response.body()))
                        } else {
                            _nearlocationResponse.postValue(
                                Resource.error(
                                    response.body()?.error.toString(),
                                    null
                                )
                            )
                        }
                    } else _nearlocationResponse.postValue(
                        Resource.error(
                            response.message().toString(), null
                        )
                    )

                }

                override fun onFailure(call: Call<NearestBranchResponse>, t: Throwable) {
                    _nearlocationResponse.postValue(Resource.error(t.message.toString(), null))
                }
            }
        )
    }



}