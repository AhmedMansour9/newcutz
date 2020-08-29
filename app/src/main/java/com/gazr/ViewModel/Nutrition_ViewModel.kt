package com.gazr.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gazr.Model.Reviews_Response
import com.gazr.Model.Sizes_Response
import com.gazr.Retrofit.ApiClient
import com.gazr.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Nutrition_ViewModel : ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<Reviews_Response>? = null
    private lateinit var context: Context

    public var listAllsizesMutableLiveData: MutableLiveData<Sizes_Response>? = null

     fun getData(product_id:String,lang:String ,context: Context): LiveData<Reviews_Response> {
        listProductsMutableLiveData = MutableLiveData<Reviews_Response>()
        this.context = context
        getDataValues(product_id,lang)
        return listProductsMutableLiveData as MutableLiveData<Reviews_Response>
    }


    private fun getDataValues(product_id: String,lang:String) {
        var map= HashMap<String,String>()

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.AddetionalProducts(lang)
        call?.enqueue(object : Callback, retrofit2.Callback<Reviews_Response> {
            override fun onResponse(call: Call<Reviews_Response>, response: Response<Reviews_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Reviews_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }



}