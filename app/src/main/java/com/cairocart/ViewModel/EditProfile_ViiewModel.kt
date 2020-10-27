package com.cairocart.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cairocart.Model.Edit_ProfileResponse
import com.cairocart.Retrofit.ApiClient
import com.cairocart.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class EditProfile_ViiewModel : ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<Edit_ProfileResponse>? = null
    private lateinit var context: Context
    private var Wron_Email:Boolean=false

    fun getStatus():Boolean{
        return Wron_Email
    }

     fun getData(
        auth:String,
        Email: String,
        Phone:String,
        Name:String,
        context: Context
    ): LiveData<Edit_ProfileResponse> {
        listProductsMutableLiveData = MutableLiveData<Edit_ProfileResponse>()
        this.context = context
        getDataValues(auth,Email,Phone,Name)
        return listProductsMutableLiveData as MutableLiveData<Edit_ProfileResponse>
    }

    fun getEditData(
        auth:String,
        Email:String,Phone:String,
        full_name: String,
        city_id:String,
        state_id:String,
        customer_street:String,
        customer_home_number:String,
        customer_floor_number:String,
        context: Context
    ): LiveData<Edit_ProfileResponse> {
        listProductsMutableLiveData = MutableLiveData<Edit_ProfileResponse>()
        this.context = context
        getDataEditValues(auth,Email,Phone,full_name,city_id,state_id,customer_street,customer_home_number,customer_floor_number)
        return listProductsMutableLiveData as MutableLiveData<Edit_ProfileResponse>
    }
    public fun getConfirmPassowrd(
        auth:String,
        password: String,
        confirmpassword:String,
        context: Context
    ): LiveData<Edit_ProfileResponse> {
        listProductsMutableLiveData = MutableLiveData<Edit_ProfileResponse>()
        this.context = context
        getDataPassword(auth,password,confirmpassword)
        return listProductsMutableLiveData as MutableLiveData<Edit_ProfileResponse>
    }
    private fun getDataValues(auth:String,Email: String,Phone:String,Name:String) {
        var map = HashMap<String, String>()
        if (Email != null) {
            map.put("email", Email)
        }
        if (Phone != null) {
            map.put("phone", Phone)
        }
        if (Name != null) {
            map.put("full_name", Name)
        }
        map.put("gender", "male")

        map.put("requestType","edit")
        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.EditProf(map, "Bearer " + auth)
        call?.enqueue(object : Callback, retrofit2.Callback<Edit_ProfileResponse> {
            override fun onResponse(
                call: Call<Edit_ProfileResponse>,
                response: Response<Edit_ProfileResponse>
            ) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Edit_ProfileResponse>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }

    fun getDataPassword(auth:String,Password: String,ConFirmPass:String) {

        var map = HashMap<String, String>()
        map.put("old_password", Password)
        map.put("password", ConFirmPass)
        map.put("password_confirmation", ConFirmPass)


        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.ChangePassword(map, "Bearer " + auth)
        call?.enqueue(object : Callback, retrofit2.Callback<Edit_ProfileResponse> {
            override fun onResponse(
                call: Call<Edit_ProfileResponse>,
                response: Response<Edit_ProfileResponse>
            ) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Edit_ProfileResponse>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)
            }
        })
    }


    private fun getDataEditValues(auth:String,
                                  email:String,
                                  phone:String,
                                  full_name: String,
                                  city_id:String,
                                  state_id:String,
                                  customer_street:String,
                                  customer_home_number:String,
                                  customer_floor_number:String) {
        var map = HashMap<String, String>()
        if (email != null) {
            map.put("email", email)
        }
        if (phone != null) {
            map.put("phone", phone)
        }
        if (full_name != null) {
            map.put("full_name", full_name)
        }
        if (city_id != null) {
            map.put("city_id", city_id)
        }
        if (state_id != null) {
            map.put("state_id", state_id)
        }
        if (customer_street != null) {
            map.put("customer_street", customer_street)
        }
        if (customer_home_number != null) {
            map.put("customer_home_number", customer_home_number)
        }
        if (customer_floor_number != null) {
            map.put("customer_floor_number", customer_floor_number)
        }

        map.put("gender", "male")

        map.put("requestType","edit")
        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.EditProf(map, "Bearer " + auth)
        call?.enqueue(object : Callback, retrofit2.Callback<Edit_ProfileResponse> {
            override fun onResponse(
                call: Call<Edit_ProfileResponse>,
                response: Response<Edit_ProfileResponse>
            ) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Edit_ProfileResponse>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }

}