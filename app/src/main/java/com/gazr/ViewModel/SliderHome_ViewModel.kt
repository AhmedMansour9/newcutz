package com.gazr.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gazr.Model.SliderHome_Model
import com.gazr.Retrofit.ApiClient
import com.gazr.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class SliderHome_ViewModel : ViewModel()
{
     var listProductsMutableLiveData: MutableLiveData<SliderHome_Model>? = null
    private lateinit var context: Context


     fun getData(Lang:String, context: Context): LiveData<SliderHome_Model> {
        listProductsMutableLiveData = MutableLiveData<SliderHome_Model>()
        this.context = context
        getDataValues(Lang)
        return listProductsMutableLiveData as MutableLiveData<SliderHome_Model>
    }

    fun getDataBanners( context: Context): LiveData<SliderHome_Model> {
        listProductsMutableLiveData = MutableLiveData<SliderHome_Model>()
        this.context = context
        getbanners()
        return listProductsMutableLiveData as MutableLiveData<SliderHome_Model>
    }


    private fun getDataValues(Lang: String) {

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.SLider()
        call?.enqueue(object : Callback, retrofit2.Callback<SliderHome_Model> {
            override fun onResponse(call: Call<SliderHome_Model>, response: Response<SliderHome_Model>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<SliderHome_Model>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)
            }
        })
    }

    private fun getbanners() {


        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.Banners()
        call?.enqueue(object : Callback, retrofit2.Callback<SliderHome_Model> {
            override fun onResponse(call: Call<SliderHome_Model>, response: Response<SliderHome_Model>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<SliderHome_Model>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }


}