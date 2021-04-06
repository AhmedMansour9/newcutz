package com.cutz.data.remote.repository

import com.cutz.data.remote.api.ApiService
import com.cutz.data.remote.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.QueryMap
import javax.inject.Inject


class ApiRepository @Inject constructor(private val apiService: ApiService) : ApiService {

    override fun userRegister(registerRequest: RegisterRequest): Call<AccountResponse> =
        apiService.userRegister(registerRequest)

    override fun editAccount(@Part img: MultipartBody.Part?,
                             @PartMap map:HashMap<String,@JvmSuppressWildcards RequestBody>, token:String): Call<DefultResponse> =
        apiService.editAccount(img,map,token)

    override fun contactUs(registerRequest: RequestContactUs): Call<DefultResponse> = apiService.contactUs(registerRequest)
    override fun getRelated(
        language: String,
        token: String,
        map: Map<String, String>
    ): Call<ProductsResponse> = apiService.getRelated(language,token,map)

    override fun createOrder(
        request: RequestCreateOrder,
        token: String?
    ): Call<CreateOrderResponse> = apiService.createOrder(request,token)

    override fun addCoupon(map: Map<String, String>, token: String?): Call<AddCouponResponse> = apiService.addCoupon(map,token)

    override fun checkPhone(map: Map<String, String>): Call<VerifyResponse> = apiService.checkPhone(map)
    override fun resendPassword(map: Map<String, String>): Call<AccountResponse> = apiService.resendPassword(map)

    override fun getProfile(token: String): Call<ProfileResponse> =
        apiService.getProfile(token)

    override fun getBrand(language: String, token: String): Call<Brands_Response> =
        apiService.getBrand(language, token)



    override  fun userLogin(loginRequest: LoginRequest): Call<AccountResponse> =
        apiService.userLogin(loginRequest)

    override fun userLoginSocial(map: Map<String, String>): Call<AccountResponse> = apiService.userLoginSocial(map)

    override fun userVerify(map: Map<String, String>): Call<AccountResponse> = apiService.userVerify(map)

    override suspend fun loginFacebook(map: Map<String, String>): Call<AccountResponse> =
        apiService.loginFacebook(map)


    override fun fetchCategories(language: String, token: String): Call<SectonsResponse> =
        apiService.fetchCategories(language, token)

    override fun fetchCollections(language: String, token: String): Call<CollectionsResponse> = apiService.fetchCollections(language,token)
    override fun getCategories(language: String): Call<CategoriesResponse> = apiService.getCategories(language)

    override suspend fun fetchProductsById(
        language: String,
        token: String,
        map: Map<String, String>
    ): Response<ProductsResponse> =
        apiService.fetchProductsById(language, token, map)

    override suspend fun fetchSearchProducts(
        language: String,
        token: String,
        map: Map<String, String>
    ): Response<ProductsResponse> = apiService.fetchSearchProducts(language,token,map)

    override fun addReview(
        token: String,
        review: RequestAddReviewResponse
    ): Call<AddReviewResponse> = apiService.addReview(token, review)

    override fun getReviews(sku: String, token: String): Call<ListReviwesResponse> =
        apiService.getReviews(sku, token)

    override  fun addFavourit(id: String, token: String): Call<AddToFavouritResponse> =
        apiService.addFavourit(id, token)

    override  fun removeFavourit(id: String, token: String): Call<AddToFavouritResponse> =
        apiService.removeFavourit(id, token)

    override  fun getWishList(lang: String, token: String): Call<ProductsResponse> =
        apiService.getWishList(lang,token)

    override suspend fun getOffers(language: String, token: String): Response<ProductsResponse> = apiService.getOffers(language,token)

    override fun addToCart(
        token: String,
        registerRequest: RequestAddToCartResponse
    ): Call<AddToCartResponse> =
    apiService.addToCart(token,registerRequest)

    override  fun getlistCart(lang: String, token: String
    ): Call<ListCartResponse> =
        apiService.getlistCart(lang,token)

    override fun editCart(token: String, map: Map<String, String>): Call<AddToCartResponse> = apiService.editCart(token,map)


    override fun deleteCart(
        @QueryMap map: Map<String, String>,
        token: String?
    ): Call<AddToCartResponse> =
        apiService.deleteCart(map,token)

    override fun createCart(): Call<CreateCartResponse> =
        apiService.createCart()

    override fun addGustCart(registerRequest: AddGustCartResponse): Call<AddToCartResponse> =
        apiService.addGustCart(registerRequest)

    override fun getCountries(): Call<CountriesResponse> = apiService.getCountries()

    override fun getShippingMethod(request: RequestShippingMethodResponse,token: String?): Call<ListShippingMethod> =
        apiService.getShippingMethod(request,token)

    suspend override fun fetchBlogs(language: String, map: Map<String, String>): Response<BlogsResponse> = apiService.fetchBlogs(language,map)

    override suspend fun fetchAboutus(language: String): Response<AboutUsResponse> = apiService.fetchAboutus(language)

    override fun getMyOrders(language: String,@Header("Authorization")token:String): Call<MyOrdersResponse> =  apiService.getMyOrders(language,token)


}