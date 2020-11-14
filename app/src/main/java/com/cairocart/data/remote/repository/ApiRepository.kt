package com.cairocart.data.remote.repository

import com.cairocart.data.remote.api.ApiService
import com.cairocart.data.remote.model.*
import retrofit2.Response
import javax.inject.Inject


class ApiRepository @Inject constructor(private val apiService: ApiService) : ApiService {

    override suspend fun userRegister(registerRequest: RegisterRequest): Response<AccountResponse> =
        apiService.userRegister(registerRequest)


    override suspend fun userLogin(loginRequest: LoginRequest): Response<AccountResponse> =
        apiService.userLogin(loginRequest)

    override suspend fun loginFacebook(map: Map<String, String>): Response<AccountResponse> =
        apiService.loginFacebook(map)


    override suspend fun fetchCategories(language: String): Response<CategoriesResponse> =
        apiService.fetchCategories(language)


    override suspend fun fetchProductsById(language: String, map: Map<String, String>): Response<ProductsResponse> =
        apiService.fetchProductsById(language,map)

}