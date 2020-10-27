package com.cairocart.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cairocart.Model.Blogs_Response
import com.cairocart.Retrofit.ApiClient
import com.cairocart.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Articatles_ViewModel : ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<Blogs_Response>? = null
    private lateinit var context: Context


     fun getCategories(link:String,lang:String, context: Context): LiveData<Blogs_Response> {
        listProductsMutableLiveData = MutableLiveData<Blogs_Response>()
        this.context = context
        getDataValues(link,lang)
        return listProductsMutableLiveData as MutableLiveData<Blogs_Response>
    }


    private fun getDataValues(link: String,lang:String) {

        var service = ApiClient.getClient()?.create(Service::class.java)
        var map= HashMap<String,String>()

        map.put("lang",lang)

        val call = service?.Blogs(link,lang)
        call?.enqueue(object : Callback, retrofit2.Callback<Blogs_Response> {
            override fun onResponse(call: Call<Blogs_Response>, response: Response<Blogs_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Blogs_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)
            }
        })
    }

}