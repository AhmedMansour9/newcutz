package com.cairocart.data.remote.api

import com.cairocart.data.remote.model.LoginRequest
import com.cairocart.data.remote.model.RegisterRequest
import com.cairocart.data.remote.model.AccountResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface ApiService {

    @POST("en" + "/rest/V1/restapi/register")
    suspend fun userRegister(@Body registerRequest: RegisterRequest): Response<AccountResponse>

    @POST("en/rest/V1/restapi/login")
    suspend fun userLogin(@Body loginRequest: LoginRequest): Response<AccountResponse>

    @POST("social_login")
    suspend fun loginFacebook(@QueryMap map: Map<String, String>): Response<AccountResponse>


}