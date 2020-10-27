package com.cairocart.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cairocart.Model.AddToCart_Response
import com.cairocart.Model.Gifts_Response
import com.cairocart.Model.MyOrders_Response
import com.cairocart.Model.OrderDetails_Response
import com.cairocart.Retrofit.ApiClient
import com.cairocart.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MyOrders_ViewModel : ViewModel() {
    private var No_Product:Boolean=false

     var listProductsMutableLiveData: MutableLiveData<MyOrders_Response>? = null
    private lateinit var context: Context
    private var Wron_Email:Boolean=false
    var listOrderDetailssMutableLiveData: MutableLiveData<OrderDetails_Response>? = null
    var AddGiftsMutableLiveData: MutableLiveData<AddToCart_Response>? = null
    var listGiftsMutableLiveData: MutableLiveData<Gifts_Response>? = null

    fun getStatus():Boolean{
        return Wron_Email
    }

    fun getGifts(
        link:String,
        auth:String?,
        context: Context
    ): LiveData<Gifts_Response> {
        listGiftsMutableLiveData = MutableLiveData<Gifts_Response>()
        this.context = context
        getDataGifts(link,auth)
        return listGiftsMutableLiveData as MutableLiveData<Gifts_Response>
    }


    fun addGift(
        link:String,
        auth:String?,
        gift_id:String,
        context: Context
    ): LiveData<AddToCart_Response> {
        AddGiftsMutableLiveData = MutableLiveData<AddToCart_Response>()
        this.context = context
        AddGifts(link,auth,gift_id)
        return AddGiftsMutableLiveData as MutableLiveData<AddToCart_Response>
    }


    public fun getData(
        lang:String,
        link:String,
        auth:String?,
        context: Context
    ): LiveData<MyOrders_Response> {
        listProductsMutableLiveData = MutableLiveData<MyOrders_Response>()
        this.context = context
        getDataValues(lang,link,auth)
        return listProductsMutableLiveData as MutableLiveData<MyOrders_Response>
    }
    public fun getorderDetails(
        orderid:String,
        context: Context
    ): LiveData<OrderDetails_Response> {
        listOrderDetailssMutableLiveData = MutableLiveData<OrderDetails_Response>()
        this.context = context
        getOrderdetails(orderid)
        return listOrderDetailssMutableLiveData as MutableLiveData<OrderDetails_Response>
    }


    private fun getDataValues(lang:String,link:String,auth:String?) {
        var map= HashMap<String,String>()
        map.put("lang", "en")

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.Myorders(lang,link, "Bearer "+auth)
        call?.enqueue(object : Callback, retrofit2.Callback<MyOrders_Response> {
            override fun onResponse(call: Call<MyOrders_Response>, response: Response<MyOrders_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)
                } else  {
                    No_Product=true
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<MyOrders_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }
    private fun getOrderdetails(orderid:String) {
        var map= HashMap<String,String>()
        map.put("order_id", orderid)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.MyordersDetails(orderid)
        call?.enqueue(object : Callback, retrofit2.Callback<OrderDetails_Response> {
            override fun onResponse(call: Call<OrderDetails_Response>, response: Response<OrderDetails_Response>) {

                if (response.code() == 200) {
                    listOrderDetailssMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listOrderDetailssMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<OrderDetails_Response>, t: Throwable) {
                listOrderDetailssMutableLiveData?.setValue(null)

            }
        })
    }

    private fun getDataGifts(link:String,auth:String?) {
        var map= HashMap<String,String>()
        map.put("lang", "en")

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.MyGifts(link, "Bearer "+auth)
        call?.enqueue(object : Callback, retrofit2.Callback<Gifts_Response> {
            override fun onResponse(call: Call<Gifts_Response>, response: Response<Gifts_Response>) {

                if (response.code() == 200) {
                    listGiftsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    No_Product=true
                    listGiftsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Gifts_Response>, t: Throwable) {
                listGiftsMutableLiveData?.setValue(null)

            }
        })
    }

    private fun AddGifts(link:String,auth:String?,gift_id:String) {
        var map= HashMap<String,String>()
        map.put("gift_id", gift_id)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.AddGifts(link, map,"Bearer "+auth)
        call?.enqueue(object : Callback, retrofit2.Callback<AddToCart_Response> {
            override fun onResponse(call: Call<AddToCart_Response>, response: Response<AddToCart_Response>) {

                if (response.code() == 200) {
                    AddGiftsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    AddGiftsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<AddToCart_Response>, t: Throwable) {
                AddGiftsMutableLiveData?.setValue(null)

            }
        })
    }
}