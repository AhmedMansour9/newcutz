package com.cairocart.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cairocart.Model.Register_Model
import com.cairocart.Model.SendRegisterRequest_Model
import com.cairocart.Model.SendRequestLogin_Model
import com.cairocart.Retrofit.ApiClient
import com.cairocart.Retrofit.Service
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback


class Register_ViewModel : ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<Register_Model>? = null
    private lateinit var context: Context
    private var Wron_Email:Boolean=false
    companion object {

        var LastPhone: String? = String()
        var LastEmail: String? = String()
    }

    fun getStatus():Boolean{
        return Wron_Email
    }

    public fun getData(
        Email: String,
        Password:String,
        FirstName:String,
        LastName:String,
        context: Context
    ): LiveData<Register_Model> {
        listProductsMutableLiveData = MutableLiveData<Register_Model>()
        this.context = context
        getDataValues(Email,Password,FirstName,LastName)
        return listProductsMutableLiveData as MutableLiveData<Register_Model>
    }
    public fun getLogin(
        Email: String,
        Password:String,
        context: Context
    ): LiveData<Register_Model> {
        listProductsMutableLiveData = MutableLiveData<Register_Model>()
        this.context = context
        getLoginValues(Email,Password)
        return listProductsMutableLiveData as MutableLiveData<Register_Model>

    }

    public fun getLoginFacebook(
        id: String?,
        email:String?,
        name:String?,
        context: Context
    ): LiveData<Register_Model> {
        listProductsMutableLiveData = MutableLiveData<Register_Model>()
        this.context = context
        getface(id,email,name)
        return listProductsMutableLiveData as MutableLiveData<Register_Model>

    }


    private fun getDataValues(Email: String,Password:String, FirstName:String,
                              LastName:String) {
      var map= HashMap<String,String>()
//        map.put("email",Email)
//        map.put("password",Password)
//        map.put("password_confirmation",Password)
////        map.put("phone",phone)
//        map.put("full_name",Name)
//        map.put("requestType","create")
//        map.put("gender","male")
        var model=SendRegisterRequest_Model(
            SendRegisterRequest_Model.Customer(
                Email,FirstName,LastName
        ),Password)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.userRegister(model)
        call?.enqueue(object : Callback, retrofit2.Callback<Register_Model> {
            override fun onResponse(call: Call<Register_Model>, response: Response<Register_Model>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else if (response.code() == 422) {
//                        Wron_Email=true
//                        var jObjError = JSONObject(response.errorBody()!!.string())
//                        var jOsonData = jObjError.getJSONObject("data")
//                        if(let { jOsonData.has("phone")}){
//                            LastPhone=jOsonData.getJSONArray("phone").get(0).toString()
//                        }
//                        if(let { jOsonData.has("email")}){
//                            LastEmail=jOsonData.getJSONArray("email").get(0).toString()
//                        }
                        listProductsMutableLiveData?.setValue(null)

                    }
                    listProductsMutableLiveData?.setValue(null)
                }


            override fun onFailure(call: Call<Register_Model>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }
    private fun getLoginValues(Email: String,Password:String) {
        var map= HashMap<String,String>()
        map.put("email",Email)
        map.put("password",Password)

        var model=SendRequestLogin_Model(
            Email,Password)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.userLogin(model)


        call?.enqueue(object : Callback, retrofit2.Callback<Register_Model> {
            override fun onResponse(call: Call<Register_Model>, response: Response<Register_Model>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    Wron_Email=true
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Register_Model>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }

    private fun getface(social_id: String?,email:String?,full_name:String?) {
        var map= HashMap<String,String>()
        map.put("social_id",social_id!!)
        if(email!=null) {
            map.put("email", email)
        }else {
            map.put("email", "")

        }
        map.put("full_name",full_name!!)


        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.userLoginFacebook(map)


        call?.enqueue(object : Callback, retrofit2.Callback<Register_Model> {
            override fun onResponse(call: Call<Register_Model>, response: Response<Register_Model>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    Wron_Email=true
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Register_Model>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }


}