package com.hadrmout.ui.editprofile

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadrmout.base.BaseViewModel
import com.hadrmout.data.DataCenterManager
import com.hadrmout.data.remote.model.*
import com.hadrmout.ui.bottomnavigate.BottomNavigateActivity
import com.hadrmout.utils.Resource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import javax.security.auth.callback.Callback

class EditProfileViewModel  @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<EditProfileNavigator>(dataCenterManager) {

    private var requestImage1: MultipartBody.Part? = null

    var customer: RequestEditProdile = RequestEditProdile()
    private val _accountResponse = MutableLiveData<Resource<DefultResponse>>()
    val defultResponse: LiveData<Resource<DefultResponse>>
        get() = _accountResponse

    private val _addressResponse = MutableLiveData<Resource<AddressResponse>>()
    val addresssResponse: LiveData<Resource<AddressResponse>>
        get() = _addressResponse

    private val _profileResponse = MutableLiveData<Resource<ProfileResponse>>()
    val getProfileResponse: LiveData<Resource<ProfileResponse>>
        get() = _profileResponse

    var token = MutableLiveData<String>()

    init {
        Log.d("CategoryFragment", "ESLAM: ")

        getProfile(BottomNavigateActivity.token!!)
        getAddress(BottomNavigateActivity.token!!)


    }


    fun getAddress(token: String) {
        _addressResponse.postValue(Resource.loading(null))
        dataCenterManager.getAddress(BottomNavigateActivity.Lang!!,"Bearer "+token).enqueue(
            object : Callback, retrofit2.Callback<AddressResponse> {
                override fun onResponse(
                    call: Call<AddressResponse>,
                    response: Response<AddressResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == true) {
                            _addressResponse.postValue(Resource.success(response.body()))
                        } else {
                            _addressResponse.postValue(
                                Resource.error(
                                    response.body()?.error.toString(),
                                    null
                                )
                            )
                        }
                    } else _addressResponse.postValue(
                        Resource.error(
                            response.message().toString(), null
                        )
                    )

                }

                override fun onFailure(call: Call<AddressResponse>, t: Throwable) {
                    _addressResponse.postValue(Resource.error(t.message.toString(), null))
                }
            }
        )
    }


    fun editProfile(token: String, file: File?) {
        if (!customer.empty()) {
            val full_name =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), customer.full_name)
            val Phone =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), customer.phone)
            val Email =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), customer.email)
            val gender = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "male")
            val deviceType =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "android")
            val requestFile1 = file?.let {
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(),
                    it
                )
            }

            requestImage1 =
                requestFile1?.let {
                    MultipartBody.Part.createFormData("avatar", file.name,
                        it
                    )
                }

            var map= HashMap<String, RequestBody>()
            map.put("name",full_name)
            map.put("email",Email)
            map.put("phone",Phone)

            _accountResponse.postValue(Resource.loading(null))
            dataCenterManager.editAccount(requestImage1,map,"Bearer $token")
                .enqueue(object : Callback, retrofit2.Callback<DefultResponse> {
                    override fun onResponse(
                        call: Call<DefultResponse>,
                        response: Response<DefultResponse>
                    ) {
                        if(response.isSuccessful){
                            if (response.body()?.status == true) {
                                _accountResponse.postValue(Resource.success(response.body()))

                            } else {
                                _accountResponse.postValue(Resource.error(response.body()?.error.toString(), null))
                            }
                        }else
                            _accountResponse.postValue(Resource.error(response.message(), null))
                    }
                    override fun onFailure(call: Call<DefultResponse>, t: Throwable) {
                        _accountResponse.postValue(Resource.error(t.message.toString(), null))
                    }
                })
        }
    }
    fun getProfile(token:String) {
        _profileResponse.postValue(Resource.loading(null))
//            viewModelScope.launch {
            dataCenterManager.getProfile("Bearer $token")
                .enqueue(object : Callback, retrofit2.Callback<ProfileResponse> {
                    override fun onResponse(
                        call: Call<ProfileResponse>,
                        response: Response<ProfileResponse>
                    ) {
                        if(response.isSuccessful){
                            if (response.code() == 200) {
                                _profileResponse.postValue(Resource.success(response.body()))

                            } else {
                                _profileResponse.postValue(Resource.error(response?.errorBody().toString(), null))
                            }
                        }else
                            _profileResponse.postValue(Resource.error(response.message(), null))
                    }

                    override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                        _profileResponse.postValue(Resource.error(t.message.toString(), null))
                    }
                })
        }

}