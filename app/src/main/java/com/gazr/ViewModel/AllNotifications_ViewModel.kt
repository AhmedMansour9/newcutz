package com.gazr.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gazr.Model.AllNotifications_Response
import com.gazr.Model.Blogs_Response
import com.gazr.Retrofit.ApiClient
import com.gazr.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AllNotifications_ViewModel :ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<AllNotifications_Response>? = null


    fun getNotifications( ): LiveData<AllNotifications_Response> {
        listProductsMutableLiveData = MutableLiveData<AllNotifications_Response>()
        getDataValues()
        return listProductsMutableLiveData as MutableLiveData<AllNotifications_Response>
    }


    private fun getDataValues() {

        var service = ApiClient.getClient()?.create(Service::class.java)
//        var map= HashMap<String,String>()
//
//        map.put("lang",lang)

        val call = service?.Notigications()
        call?.enqueue(object : Callback, retrofit2.Callback<AllNotifications_Response> {
            override fun onResponse(call: Call<AllNotifications_Response>, response: Response<AllNotifications_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<AllNotifications_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)
            }
        })
    }


}