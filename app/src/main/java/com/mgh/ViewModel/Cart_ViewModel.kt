package com.mgh.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mgh.Model.AllProducts_Response
import com.mgh.Model.Cart_Response
import com.mgh.Model.PlusCart_Response
import com.mgh.Retrofit.ApiClient
import com.mgh.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Cart_ViewModel : ViewModel()
{
     var listProductsMutableLiveData: MutableLiveData<AllProducts_Response>? = null
    var PlusProductsMutableLiveData: MutableLiveData<PlusCart_Response>? = null
    var MinusProductsMutableLiveData: MutableLiveData<PlusCart_Response>? = null
    private lateinit var context: Context
     fun getData(
        lang:String,
        token:String?,
        context: Context
    ): LiveData<AllProducts_Response> {
        listProductsMutableLiveData = MutableLiveData<AllProducts_Response>()
        this.context = context
        getDataValues(lang,token)
        return listProductsMutableLiveData as MutableLiveData<AllProducts_Response>
    }
     fun DeleteData(
        auth:String,
        lang:String,
        cart_id:String,
        context: Context
    ): LiveData<PlusCart_Response> {
        PlusProductsMutableLiveData = MutableLiveData<PlusCart_Response>()
        this.context = context
        getDeleteCart(    auth,lang,cart_id)
        return PlusProductsMutableLiveData as MutableLiveData<PlusCart_Response>
    }
     fun AddPlusData(
        auth:String,
        lang:String,
        cart_id:String,
        context: Context
    ): LiveData<PlusCart_Response> {
        PlusProductsMutableLiveData = MutableLiveData<PlusCart_Response>()
        this.context = context
        getPlusCart(    auth,lang,cart_id)
        return PlusProductsMutableLiveData as MutableLiveData<PlusCart_Response>
    }

    private fun getDataValues(lang:String,token:String ?) {
        var map = HashMap<String, String>()
        map.put("lang",lang)
        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.getCart( lang,"Bearer "+token)
        call?.enqueue(object : Callback, retrofit2.Callback<AllProducts_Response> {
            override fun onResponse(
                call: Call<AllProducts_Response>,
                response: Response<AllProducts_Response>
            ) {

                if (response.code() == 200) {
                     
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<AllProducts_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }

    private fun getPlusCart(auth:String,quantity:String,cart_id:String ) {
        var map = HashMap<String, String>()
        map.put("quantity",quantity)
        map.put("productId",cart_id)
        map.put("userId",auth)

        var service = ApiClient.getClient()?.create(Service::class.java)

        val  call = service?.PlusCart( map)!!

        call .enqueue(object : Callback, retrofit2.Callback<PlusCart_Response> {
            override fun onResponse(
                call: Call<PlusCart_Response>,
                response: Response<PlusCart_Response>
            ) {

                if (response.code() == 200) {
                    PlusProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    PlusProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<PlusCart_Response>, t: Throwable) {
                PlusProductsMutableLiveData?.setValue(null)

            }
        })
    }

    private fun getDeleteCart(auth:String,lang:String,cart_id:String) {
        var map = HashMap<String, String>()
        map.put("lang",lang)
        map.put("product_id",cart_id)
        var service = ApiClient.getClient()?.create(Service::class.java)
       val  call = service?.DeleteCart( map,"Bearer " + auth)!!

        call.enqueue(object : Callback, retrofit2.Callback<PlusCart_Response> {
            override fun onResponse(
                call: Call<PlusCart_Response>,
                response: Response<PlusCart_Response>
            ) {

                if (response.code() == 200) {
                    PlusProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    PlusProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<PlusCart_Response>, t: Throwable) {
                PlusProductsMutableLiveData?.setValue(null)

            }
        })
    }



}