package com.cairocart.data.remote.api

import com.cairocart.data.remote.model.AccountResponse
import com.cairocart.data.remote.model.LoginRequest
import com.cairocart.data.remote.model.RegisterRequest
import retrofit2.Response

interface ApiHelper {

    suspend fun userRegister(registerRequest: RegisterRequest): Response<AccountResponse>

    suspend fun userLogin(loginRequest: LoginRequest): Response<AccountResponse>

    suspend fun loginFacebook(map: Map<String, String>): Response<AccountResponse>
}