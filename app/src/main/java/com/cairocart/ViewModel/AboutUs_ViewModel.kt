package com.cairocart.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cairocart.Model.About_Response
import com.cairocart.Model.SocialResponse
import com.cairocart.Retrofit.ApiClient
import com.cairocart.Retrofit.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class AboutUs_ViewModel :ViewModel() {

    internal lateinit var context: Context
    private var tripList: MutableLiveData<About_Response.Data>? = null
    private var socialMedia: MutableLiveData<SocialResponse>? = null

    fun getAboutus(Lang: String, context: Context): LiveData<About_Response.Data> {
        tripList = MutableLiveData<About_Response.Data>()
        this.context = context
        getBanner(Lang)

        return tripList as MutableLiveData<About_Response.Data>
    }


    fun getPrivacy(Lang: String, context: Context): LiveData<About_Response.Data> {
        tripList = MutableLiveData<About_Response.Data>()
        this.context = context
        getDataPrivacy(Lang)

        return tripList as MutableLiveData<About_Response.Data>
    }
    fun SocialMedia(Lang: String): LiveData<SocialResponse> {
        socialMedia = MutableLiveData<SocialResponse>()
        getSocialMedia(Lang)

        return socialMedia as MutableLiveData<SocialResponse>
    }

    fun getBanner(lang: String) {
        val service = ApiClient.getClient()!!.create(Service::class.java)
        var hashMa=HashMap<String,String>()
        hashMa.put("pageName","about")
        val call = service.GetAboutus(hashMa)
        call.enqueue(object : Callback<About_Response> {
            override fun onResponse(
                call: Call<About_Response>,
                response: Response<About_Response>
            ) {

                if (response.code() == 200) {
                    tripList!!.setValue(response.body()!!.data)
                } else {
                    tripList!!.setValue(null)
                }
            }

            override fun onFailure(call: Call<About_Response>, t: Throwable) {
                tripList!!.setValue(null)

            }
        })
    }
    fun getDataPrivacy(lang: String) {
        val service = ApiClient.getClient()!!.create(Service::class.java)
        var hashMa=HashMap<String,String>()
        hashMa.put("pageName","policy")
        val call = service.GetAboutus(hashMa)
        call.enqueue(object : Callback<About_Response> {
            override fun onResponse(
                call: Call<About_Response>,
                response: Response<About_Response>
            ) {

                if (response.code() == 200) {
                    tripList!!.setValue(response.body()!!.data)
                } else {
                    tripList!!.setValue(null)
                }
            }

            override fun onFailure(call: Call<About_Response>, t: Throwable) {
                tripList!!.setValue(null)

            }
        })
    }

    fun getSocialMedia(lang: String) {
        val hashMap = HashMap<String, String>()
        hashMap["lang"] = lang
        val service = ApiClient.getClient()!!.create(Service::class.java)
        val call = service.GetSocialMedia(hashMap)
        call.enqueue(object : Callback<SocialResponse> {
            override fun onResponse(
                call: Call<SocialResponse>,
                response: Response<SocialResponse>
            ) {

                if (response.code() == 200) {
                    socialMedia!!.setValue(response.body())
                } else {
                    socialMedia!!.setValue(null)
                }
            }

            override fun onFailure(call: Call<SocialResponse>, t: Throwable) {
                socialMedia!!.setValue(null)

            }
        })
    }

}