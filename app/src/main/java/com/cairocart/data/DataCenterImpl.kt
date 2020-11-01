package com.cairocart.data

import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import com.cairocart.data.remote.model.AccountResponse
import com.cairocart.data.remote.model.LoginRequest
import com.cairocart.data.remote.model.RegisterRequest
import com.cairocart.data.remote.repository.ApiRepository
import retrofit2.Response
import javax.inject.Inject

class DataCenterImpl @Inject constructor(private val apiRepository: ApiRepository,private val dataStore: DataStore<Preferences>) :
    DataCenterManager {
    override suspend fun newAccount(registerRequest: RegisterRequest): Response<AccountResponse> =
        apiRepository.newAccount(registerRequest)

    override suspend fun loginAccount(loginRequest: LoginRequest): Response<AccountResponse> =
        apiRepository.loginAccount(loginRequest)


    override suspend fun loginFacebook(map: Map<String, String>): Response<AccountResponse> =
        apiRepository.loginFacebook(map)

    override fun dataSourcePrefrences(): DataStore<Preferences> =dataStore


}