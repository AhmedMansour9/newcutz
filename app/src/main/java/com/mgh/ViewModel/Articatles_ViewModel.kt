package com.mgh.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mgh.Model.News_Response
import com.mgh.Retrofit.ApiClient
import com.mgh.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Articatles_ViewModel : ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<News_Response>? = null
    private lateinit var context: Context


     fun getCategories(Lang:String, context: Context): LiveData<News_Response> {
        listProductsMutableLiveData = MutableLiveData<News_Response>()
        this.context = context
        getDataValues(Lang)
        return listProductsMutableLiveData as MutableLiveData<News_Response>
    }


    private fun getDataValues(Lang: String) {

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.Blogs(Lang)
        call?.enqueue(object : Callback, retrofit2.Callback<News_Response> {
            override fun onResponse(call: Call<News_Response>, response: Response<News_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<News_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }

}