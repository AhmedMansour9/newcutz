package com.mgh.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mgh.Model.AddToCart_Response
import com.mgh.Model.PromoCode_Response
import com.mgh.Retrofit.ApiClient
import com.mgh.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AddToCart_ViewModel : ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<AddToCart_Response>? = null
    private lateinit var context: Context
    public var listProductsMutable: MutableLiveData<PromoCode_Response>? = null


     fun getData(
        auth:String,
        product_id: String,
        product_quantity:String,
        context: Context
    ): LiveData<AddToCart_Response> {
        listProductsMutableLiveData = MutableLiveData<AddToCart_Response>()
        this.context = context
        getDataValues(auth,product_id,product_quantity)
        return listProductsMutableLiveData as MutableLiveData<AddToCart_Response>
    }

    fun getPromoCodeData(
        auth:String,
        code: String,
        context: Context
    ): LiveData<PromoCode_Response> {
        listProductsMutable = MutableLiveData<PromoCode_Response>()
        this.context = context
        getDataValuesPromo(auth,code)
        return listProductsMutable as MutableLiveData<PromoCode_Response>
    }


    private fun getDataValuesPromo(auth:String,code:String) {
        var map = HashMap<String, String>()
        map.put("code", code)
        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.check_promo_code(map, "Bearer " + auth)
        call?.enqueue(object : Callback, retrofit2.Callback<PromoCode_Response> {
            override fun onResponse(
                call: Call<PromoCode_Response>,
                response: Response<PromoCode_Response>
            ) {

                if (response.code() == 200) {
                    listProductsMutable?.setValue(response.body()!!)

                } else {
                    listProductsMutable?.setValue(null)
                }
            }

            override fun onFailure(call: Call<PromoCode_Response>, t: Throwable) {
                listProductsMutable?.setValue(null)

            }
        })
    }

    private fun getDataValues(auth:String,product_id: String,product_quantity:String) {
        var map = HashMap<String, String>()
            map.put("product_id", product_id)
            map.put("qty", product_quantity)

         var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.AddToCart(map,"Bearer "+auth)
        call?.enqueue(object : Callback, retrofit2.Callback<AddToCart_Response> {
            override fun onResponse(
                call: Call<AddToCart_Response>,
                response: Response<AddToCart_Response>
            ) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<AddToCart_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }




}