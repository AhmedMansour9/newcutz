package com.cairocart.data

import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import com.cairocart.data.remote.model.*
import com.cairocart.data.remote.repository.ApiRepository
import retrofit2.Response
import javax.inject.Inject

class DataCenterImpl @Inject constructor(
    private val apiRepository: ApiRepository,
    private val dataStore: DataStore<Preferences>
) :
    DataCenterManager {
    override suspend fun newAccount(registerRequest: RegisterRequest): Response<AccountResponse> =
        apiRepository.userRegister(registerRequest)

    override suspend fun loginAccount(loginRequest: LoginRequest): Response<AccountResponse> =
        apiRepository.userLogin(loginRequest)


    override suspend fun loginFacebook(map: Map<String, String>): Response<AccountResponse> =
        apiRepository.loginFacebook(map)

    override suspend fun getCategories(language: String): Response<CategoriesResponse> =
        apiRepository.fetchCategories(language)

    override  fun getProductsById(language: String,map: Map<String, String>): Response<ProductsByIdResponse> =
        apiRepository.fetchProductsById(language,map)

    override fun dataSourcePreference(): DataStore<Preferences> = dataStore


}