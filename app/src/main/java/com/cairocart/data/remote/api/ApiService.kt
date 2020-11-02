package com.cairocart.data.remote.api

import com.cairocart.data.remote.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("en" + "/rest/V1/restapi/register")
    suspend fun userRegister(@Body registerRequest: RegisterRequest): Response<AccountResponse>

    @POST("en/rest/V1/restapi/login")
    suspend fun userLogin(@Body loginRequest: LoginRequest): Response<AccountResponse>

    @POST("social_login")
    suspend fun loginFacebook(@QueryMap map: Map<String, String>): Response<AccountResponse>

    @GET("{language}"+"/rest/V1/mstore/categories")
    suspend fun fetchCategories(@Path(value = "language", encoded = true)language:String): Response<Categories_Response>


}