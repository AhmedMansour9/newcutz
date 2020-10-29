package com.cairocart.Retrofit

import com.cairocart.Model.*
import retrofit2.Call
import retrofit2.http.*

interface Service {

    @POST("en"+"/rest/V1/restapi/register")
     fun userRegister( @Body Requests: SendRegisterRequest_Model
        ): Call<Register_Model>


    @POST("en/rest/V1/restapi/login")
    fun userLogin(
        @Body Requests: SendRequestLogin_Model): Call<Register_Model>

    @POST("social_login")
    fun userLoginFacebook(
        @QueryMap map:Map<String,String>): Call<Register_Model>


    @GET("{language}"+"/rest/V1/mstore/categories")
    fun Categories(
        @Path(value = "language", encoded = true)language:String): Call<Categories_Response>



}