package com.cairocart.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cairocart.Model.AllNotifications_Response
import com.cairocart.Model.NotificationCounter_Response
import com.cairocart.Retrofit.ApiClient
import com.cairocart.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AllNotifications_ViewModel :ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<AllNotifications_Response>? = null
    public var listCountMutableLiveData: MutableLiveData<NotificationCounter_Response>? = null


    fun getNotifications(Lang:String,token:String ): LiveData<AllNotifications_Response> {
        listProductsMutableLiveData = MutableLiveData<AllNotifications_Response>()
        getDataValues(Lang,token)
        return listProductsMutableLiveData as MutableLiveData<AllNotifications_Response>
    }


    fun getCountNotifications(token:String ): LiveData<NotificationCounter_Response> {
        listCountMutableLiveData = MutableLiveData<NotificationCounter_Response>()
        getDataCount(token)
        return listCountMutableLiveData as MutableLiveData<NotificationCounter_Response>
    }

    private fun getDataValues(Lang:String,token:String) {

        var service = ApiClient.getClient()?.create(Service::class.java)
//        var map= HashMap<String,String>()
//        map.put("lang",lang)

        val call = service?.Notigications(Lang,"Bearer "+token)
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


    private fun getDataCount(token:String) {

        var service = ApiClient.getClient()?.create(Service::class.java)
//        var map= HashMap<String,String>()
//        map.put("lang",lang)

        val call = service?.NotigicationCount("Bearer "+token)
        call?.enqueue(object : Callback, retrofit2.Callback<NotificationCounter_Response> {
            override fun onResponse(call: Call<NotificationCounter_Response>, response: Response<NotificationCounter_Response>) {

                if (response.code() == 200) {
                    listCountMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listCountMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<NotificationCounter_Response>, t: Throwable) {
                listCountMutableLiveData?.setValue(null)
            }
        })
    }

}