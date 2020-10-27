package com.cairocart.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cairocart.Model.ContactUs_Response
import com.cairocart.Model.ForgetPassword_Response
import com.cairocart.Retrofit.ApiClient
import com.cairocart.Retrofit.Service
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class ContactUs_ViewModel :ViewModel() {
    lateinit var context: Context
    private var regist: MutableLiveData<ContactUs_Response>? = null
    internal var Error: String? = null
    private var forget: MutableLiveData<ForgetPassword_Response>? = null
    companion object {
        var Message:String?= String()
    }
    fun ForgetPass(
        email: String,
        context: Context
    ): LiveData<ForgetPassword_Response> {
        forget = MutableLiveData<ForgetPassword_Response>()
        this.context = context
        Reply(email)

        return forget as MutableLiveData<ForgetPassword_Response>
    }

    fun Reply(email: String) {
        val queryMap = HashMap<String, String>()

        queryMap["email"] = email
        val apiInterface = ApiClient.getClient()?.create(Service::class.java)
        val call = apiInterface?.ForgetPassword(queryMap)
        call?.enqueue(object : Callback<ForgetPassword_Response> {
            override fun onResponse(
                call: Call<ForgetPassword_Response>,
                response: Response<ForgetPassword_Response>
            ) {
                if (response.code() == 200) {
                    forget!!.setValue(response.body())
                } else {
                    val jObjError = JSONObject(response.errorBody()!!.string())

                    Message = jObjError.getString("message")
                    forget!!.setValue(null)
                }


            }

            override fun onFailure(call: Call<ForgetPassword_Response>, t: Throwable) {
                forget!!.setValue(null)

            }
        })
    }

    fun getContactus(
        Email: String,
        Name: String,
        phone: String,
        message: String,
        context: Context
    ): LiveData<ContactUs_Response> {
        regist = MutableLiveData<ContactUs_Response>()
        this.context = context
        Contactus(Email, Name, phone, message)

        return regist as MutableLiveData<ContactUs_Response>
    }

    fun setRate(
        comment: String,
        product_id: String,
        review: String,
        token:String,
        context: Context
    ): LiveData<ContactUs_Response> {
        regist = MutableLiveData<ContactUs_Response>()
        this.context = context
        Review(comment, product_id, review,token)

        return regist as MutableLiveData<ContactUs_Response>
    }

    fun ReplayNotification(
        comment: String,
        product_id: String,
        token:String,
        context: Context
    ): LiveData<ContactUs_Response> {
        regist = MutableLiveData<ContactUs_Response>()
        this.context = context
        Reply(comment, product_id,token)

        return regist as MutableLiveData<ContactUs_Response>
    }

    fun Contactus(Email: String, Name: String, phone: String, message: String) {
        val queryMap = HashMap<String, String>()

        queryMap["email"] = Email
        queryMap["name"] = Name
        queryMap["phone"] = phone
        queryMap["message"] = message
        val apiInterface = ApiClient.getClient()?.create(Service::class.java)
        val call = apiInterface?.ContactUs(queryMap)
        call?.enqueue(object : Callback<ContactUs_Response> {
            override fun onResponse(
                call: Call<ContactUs_Response>,
                response: Response<ContactUs_Response>
            ) {
                if (response.code() == 200) {
                    regist!!.setValue(response.body())
                } else if (response.code() == 404) {
                    regist!!.setValue(null)
                }


            }

            override fun onFailure(call: Call<ContactUs_Response>, t: Throwable) {
                regist!!.setValue(null)

            }
        })
    }

    fun Review( comment: String,
                product_id: String,
                review: String,token:String) {
        val queryMap = HashMap<String, String>()

        queryMap["comment"] = comment
        queryMap["product_id"] = product_id
        queryMap["review"] = review
        val apiInterface = ApiClient.getClient()?.create(Service::class.java)
        val call = apiInterface?.Review(queryMap,"Bearer "+token)
        call?.enqueue(object : Callback<ContactUs_Response> {
            override fun onResponse(
                call: Call<ContactUs_Response>,
                response: Response<ContactUs_Response>
            ) {
                if (response.code() == 200) {
                    regist!!.setValue(response.body())
                } else if (response.code() == 404) {
                    regist!!.setValue(null)
                }


            }

            override fun onFailure(call: Call<ContactUs_Response>, t: Throwable) {
                regist!!.setValue(null)

            }
        })
    }
    fun Reply( comment: String,
                product_id: String,token:String) {
        val queryMap = HashMap<String, String>()

        queryMap["message"] = comment
        queryMap["message_id"] = product_id
        val apiInterface = ApiClient.getClient()?.create(Service::class.java)
        val call = apiInterface?.ReplyNotification(queryMap,"Bearer "+token)
        call?.enqueue(object : Callback<ContactUs_Response> {
            override fun onResponse(
                call: Call<ContactUs_Response>,
                response: Response<ContactUs_Response>
            ) {
                if (response.code() == 200) {
                    regist!!.setValue(response.body())
                } else if (response.code() == 404) {
                    regist!!.setValue(null)
                }


            }

            override fun onFailure(call: Call<ContactUs_Response>, t: Throwable) {
                regist!!.setValue(null)

            }
        })
    }

}