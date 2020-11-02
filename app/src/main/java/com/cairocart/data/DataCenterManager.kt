package com.cairocart.data

import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import com.cairocart.data.remote.model.AccountResponse
import com.cairocart.data.remote.model.CategoryResponse
import com.cairocart.data.remote.model.LoginRequest
import com.cairocart.data.remote.model.RegisterRequest
import retrofit2.Response

interface DataCenterManager {

    suspend fun newAccount(registerRequest: RegisterRequest): Response<AccountResponse>

    suspend fun loginAccount(loginRequest: LoginRequest): Response<AccountResponse>

    suspend fun loginFacebook(map: Map<String, String>): Response<AccountResponse>

    suspend fun getCategories(language: String): Response<CategoryResponse>

    fun dataSourcePreference(): DataStore<Preferences>


}