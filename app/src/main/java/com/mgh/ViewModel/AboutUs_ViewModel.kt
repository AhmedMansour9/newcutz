package com.mgh.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mgh.Model.About_Response
import com.mgh.Model.SocialMedia_Response
import com.mgh.Retrofit.ApiClient
import com.mgh.Retrofit.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class AboutUs_ViewModel :ViewModel() {

    internal lateinit var context: Context
    private var tripList: MutableLiveData<About_Response.Data>? = null
    private var socialMedia: MutableLiveData<List<SocialMedia_Response.Data>>? = null

    fun getAboutus(Lang: String, context: Context): LiveData<About_Response.Data> {
        tripList = MutableLiveData<About_Response.Data>()
        this.context = context
        getBanner(Lang)

        return tripList as MutableLiveData<About_Response.Data>
    }

    fun SocialMedia(Lang: String, context: Context): LiveData<List<SocialMedia_Response.Data>> {
        socialMedia = MutableLiveData<List<SocialMedia_Response.Data>>()
        this.context = context
        getSocialMedia(Lang)

        return socialMedia as MutableLiveData<List<SocialMedia_Response.Data>>
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

    fun getSocialMedia(lang: String) {
        val hashMap = HashMap<String, String>()
        hashMap["lang"] = lang
        val service = ApiClient.getClient()!!.create(Service::class.java)
        val call = service.GetSocialMedia(hashMap)
        call.enqueue(object : Callback<SocialMedia_Response> {
            override fun onResponse(
                call: Call<SocialMedia_Response>,
                response: Response<SocialMedia_Response>
            ) {

                if (response.code() == 200) {
                    socialMedia!!.setValue(response.body()!!.data)
                } else {
                    socialMedia!!.setValue(null)
                }
            }

            override fun onFailure(call: Call<SocialMedia_Response>, t: Throwable) {
                socialMedia!!.setValue(null)

            }
        })
    }

}