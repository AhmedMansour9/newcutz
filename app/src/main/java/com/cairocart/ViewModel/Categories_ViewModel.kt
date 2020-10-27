package com.cairocart.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cairocart.Model.Categories_Response
import com.cairocart.Retrofit.ApiClient
import com.cairocart.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Categories_ViewModel : ViewModel() {
    public var listProductsMutableLiveData: MutableLiveData<Categories_Response>? = null
    private lateinit var context: Context


    public fun getCategories(lang:String, context: Context): LiveData<Categories_Response> {
        listProductsMutableLiveData = MutableLiveData<Categories_Response>()
        this.context = context
        getDataValues(lang)
        return listProductsMutableLiveData as MutableLiveData<Categories_Response>
    }
    fun getCategoriesById(page:String,id:String, context: Context): LiveData<Categories_Response> {
        listProductsMutableLiveData = MutableLiveData<Categories_Response>()
        this.context = context
        getcategoriesbyid(page,id)
        return listProductsMutableLiveData as MutableLiveData<Categories_Response>
    }


    private fun getDataValues(lang:String) {


        var service = ApiClient.getClient()?.create(Service::class.java)
        var map= HashMap<String,String>()
//        map.put("page",page)

        val call = service?.Categories(lang)
        call?.enqueue(object : Callback, retrofit2.Callback<Categories_Response> {
            override fun onResponse(call: Call<Categories_Response>, response: Response<Categories_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Categories_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }

    private fun getcategoriesbyid(Lang: String,id:String) {
        var map= HashMap<String,String>()
        map.put("lang",Lang)
        map.put("section_id",id)


//        var service = ApiClient.getClient()?.create(Service::class.java)
////        val call = service?.CategoriesByid(map)
//        call?.enqueue(object : Callback, retrofit2.Callback<Categories_Response> {
//            override fun onResponse(call: Call<Categories_Response>, response: Response<Categories_Response>) {
//
//                if (response.code() == 200) {
//                    listProductsMutableLiveData?.setValue(response.body()!!)
//
//                } else  {
//                    listProductsMutableLiveData?.setValue(null)
//                }
//            }
//
//            override fun onFailure(call: Call<Categories_Response>, t: Throwable) {
//                listProductsMutableLiveData?.setValue(null)
//
//            }
//        })
    }
}