package com.cairocart.data.remote.api

import com.cairocart.data.remote.model.AccountResponse
import com.cairocart.data.remote.model.LoginRequest
import com.cairocart.data.remote.model.RegisterRequest
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun userRegister(registerRequest: RegisterRequest): Response<AccountResponse> =
        apiService.userRegister(registerRequest)

    override suspend fun userLogin(loginRequest: LoginRequest): Response<AccountResponse> =
        apiService.userLogin(loginRequest)

    override suspend fun loginFacebook(map: Map<String, String>): Response<AccountResponse> =
        apiService.loginFacebook(map)


}