package com.hadrmout.ui.aboutus

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.hadrmout.base.BaseViewModel
import com.hadrmout.data.DataCenterManager
import com.hadrmout.data.remote.model.AboutUsResponse
import com.hadrmout.data.remote.model.DefultResponse
import com.hadrmout.data.remote.model.FilterModel
import com.hadrmout.data.remote.model.RequestContactUs
import com.hadrmout.ui.bottomnavigate.BottomNavigateActivity
import com.hadrmout.ui.productsbyId.ProductByIdNavigator
import com.hadrmout.utils.Resource
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AboutUsViewModel @ViewModelInject constructor(dataCenterManager: DataCenterManager) :
    BaseViewModel<ProductByIdNavigator>(dataCenterManager) {

    var filter = MutableLiveData<FilterModel>()



    var Lang = MutableLiveData<String>()
    var userId = MutableLiveData<String>()



    private val _aboutResponse = MutableLiveData<Resource<AboutUsResponse>>()
    val aboutResponse: LiveData<Resource<AboutUsResponse>>
        get() = _aboutResponse

    fun aboutUs() {
            _aboutResponse.postValue(Resource.loading(null))
//            viewModelScope.launch {
            dataCenterManager.fetchAboutus(Lang.value.toString())
                .enqueue(object : Callback, retrofit2.Callback<AboutUsResponse> {
                    override fun onResponse(
                        call: Call<AboutUsResponse>,
                        response: Response<AboutUsResponse>
                    ) {
                        if(response.isSuccessful){
                            if (response.body()?.status == true) {
                                _aboutResponse.postValue(Resource.success(response.body()))

                            } else {
                                _aboutResponse.postValue(Resource.error(response.body()?.error.toString(), null))
                            }
                        }else
                            _aboutResponse.postValue(Resource.error(response.message(), null))



                    }

                    override fun onFailure(call: Call<AboutUsResponse>, t: Throwable) {
                        _aboutResponse.postValue(Resource.error(t.message.toString(), null))
                    }
                })
        }


}