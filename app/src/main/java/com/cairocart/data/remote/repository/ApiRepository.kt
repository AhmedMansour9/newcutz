package com.cairocart.data.remote.repository

import com.cairocart.data.remote.api.ApiHelper
import com.cairocart.data.remote.model.AccountResponse
import com.cairocart.data.remote.model.LoginRequest
import com.cairocart.data.remote.model.RegisterRequest
import retrofit2.Response
import javax.inject.Inject


class ApiRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun newAccount(registerRequest: RegisterRequest) =
        apiHelper.userRegister(registerRequest)

    suspend fun loginAccount(loginRequest: LoginRequest) =
        apiHelper.userLogin(loginRequest)

    suspend fun loginFacebook(map: Map<String, String>) = apiHelper.loginFacebook(map)


}