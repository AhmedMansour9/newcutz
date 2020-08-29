package com.gazr.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gazr.Model.Cities_Response
import com.gazr.Model.RecivesPoints_Response
import com.gazr.Retrofit.ApiClient
import com.gazr.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Cities_ViewModel : ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<Cities_Response>? = null
    private lateinit var context: Context

     var listTimesMutableLiveData: MutableLiveData<RecivesPoints_Response>? = null



    fun getDataCountries( DeviceLang:String,context: Context): LiveData<Cities_Response> {
        if(listProductsMutableLiveData==null)
            listProductsMutableLiveData = MutableLiveData<Cities_Response>()
        this.context = context
        getcountries(DeviceLang)
        return listProductsMutableLiveData as MutableLiveData<Cities_Response>
    }

    fun getTypesTime( DeviceLang:String,State_Id:String,context: Context): LiveData<RecivesPoints_Response> {
        listTimesMutableLiveData = MutableLiveData<RecivesPoints_Response>()
        this.context = context
        getTimes(DeviceLang,State_Id)
        return listTimesMutableLiveData as MutableLiveData<RecivesPoints_Response>
    }


    fun getDataStates( DeviceLang:String,city_id:String,context: Context): LiveData<Cities_Response> {
        if(listProductsMutableLiveData==null)
            listProductsMutableLiveData = MutableLiveData<Cities_Response>()
        this.context = context
        GetStates(DeviceLang,city_id)
        return listProductsMutableLiveData as MutableLiveData<Cities_Response>
    }

    private fun getcountries(DeviceLang:String) {
        var map= HashMap<String,String>()
        map.put("lang",DeviceLang)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.getCountries(map,DeviceLang)
        call?.enqueue(object : Callback, retrofit2.Callback<Cities_Response> {
            override fun onResponse(
                call: Call<Cities_Response>,
                response: Response<Cities_Response>
            ) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Cities_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }
    private fun getTimes(DeviceLang:String,State_Id:String) {
        var map= HashMap<String,String>()
        map.put("lang",DeviceLang)
        map.put("city_id",State_Id)
        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.getRecivesPoints(map,DeviceLang)
        call?.enqueue(object : Callback, retrofit2.Callback<RecivesPoints_Response> {
            override fun onResponse(
                call: Call<RecivesPoints_Response>,
                response: Response<RecivesPoints_Response>
            ) {

                if (response.code() == 200) {
                    listTimesMutableLiveData?.setValue(response.body()!!)
                } else {
                    listTimesMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<RecivesPoints_Response>, t: Throwable) {
                listTimesMutableLiveData?.setValue(null)

            }
        })
    }


    private fun GetStates(DeviceLang:String,city_id:String) {
        var map= HashMap<String,String>()
        map.put("city_id",city_id)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.getStates(map,DeviceLang)
        call?.enqueue(object : Callback, retrofit2.Callback<Cities_Response> {
            override fun onResponse(
                call: Call<Cities_Response>,
                response: Response<Cities_Response>
            ) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Cities_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }

}

