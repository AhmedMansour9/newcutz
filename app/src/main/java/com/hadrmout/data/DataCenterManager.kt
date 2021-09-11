package com.hadrmout.data

import com.hadrmout.data.remote.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.QueryMap

interface DataCenterManager {

    fun newAccount(registerRequest: RegisterRequest): Call<AccountResponse>

    fun editAccount(
        @Part img: MultipartBody.Part?,
        @PartMap map: HashMap<String, @JvmSuppressWildcards RequestBody>, token: String
    ): Call<DefultResponse>

    fun loginAccount(loginRequest: LoginRequest): Call<AccountResponse>

    suspend fun loginFacebook(map: Map<String, String>): Call<AccountResponse>

    fun getCategories(
        language: String
        , token: String
        ,map: Map<String, String>
    ): Call<SectonsResponse>

    fun getBranches(language: String): Call<BranchesResponse>

    fun getAddress(language: String, token: String): Call<AddressResponse>

     fun getProductsById(
        lang: String,
        token: String,
        map: Map<String, String>
    ): Call<ProductsResponse>

    fun addReviewProduct(
        token: String,
        review: RequestAddReviewResponse
    ): Call<AddReviewResponse>

    fun forgetPassword(map: Map<String, String>) :Call<ForgetPasswordResponse>

    fun getDeliveryFees(token: String): Call<ProductsResponse>

    fun getReviewProduct(
        sku: String,
        token: String
    ): Call<ListReviwesResponse>

    fun addFavourit(id: String, token: String): Call<AddToFavouritResponse>

    fun removeFavourit(id: String, token: String): Call<AddToFavouritResponse>


    fun getWishList(
        lang: String,
        token: String
    ): Call<ProductsResponse>

     fun getOffers(
        lang: String,
        token: String,
        map: Map<String, String>
    ): Call<ProductsResponse>


    fun addToCart(
        id: String,
        token: String,
        registerRequest: RequestAddToCartResponse
    ): Call<AddToCartResponse>

    fun getListCart(
        lang: String,
        token: String,
    ): Call<ListCartResponse>


     fun fetchBestProducts(
        language: String,
        token: String,
        map: Map<String, String>
    ): Call<BestSeller_Response>

    fun deleteCart(
        cart_Id:String,
        @QueryMap map: Map<String, String>
        , token: String?
    ): Call<AddToCartResponse>


    fun createCart(): Call<CreateCartResponse>

    fun addGustCart(
        registerRequest: AddGustCartResponse
    ): Call<AddToCartResponse>

    fun getProfile(token: String): Call<ProfileResponse>

    fun getBrand(language: String, token: String): Call<Brands_Response>

    fun getCountries(): Call<CountriesResponse>

    fun getShippingMethod(
        request: RequestShippingMethodResponse,
        token: String?
    ): Call<ListShippingMethod>

    fun fetchCollections(language: String, token: String
    , map: Map<String, String>): Call<ProductsResponse>

    suspend fun fetchBlogs(language: String, map: Map<String, String>): Response<BlogsResponse>

    fun getOrders(
        language: String,
        token: String
    ): Call<MyOrdersResponse>

     fun fetchAboutus(language: String): Call<AboutUsResponse>

    fun contactUs(registerRequest: RequestContactUs): Call<DefultResponse>
    suspend fun fetchSearchProducts(
        language: String,
        token: String,
        map: Map<String, String>
    ): Response<ProductsResponse>

    fun getRelated(
        language: String,
        token: String,
        map: Map<String, String>
    ): Call<ProductsResponse>

    fun editCart(cart_Id:String,token: String, map: Map<String, String>): Call<AddToCartResponse>

    fun createOrder(
        request: RequestCreateOrder,
        token: String?
    ): Call<CreateOrderResponse>

    fun createAddress(
        request:  HashMap<String,String>,
        token: String?
    ): Call<CreateAddressResponse>

    fun addCoupon(map: Map<String, String>, token: String?): Call<AddCouponResponse>

    fun userVerify(map: Map<String, String>): Call<AccountResponse>

    fun userLoginSocial(map: Map<String, String>): Call<AccountResponse>

    fun checkPhone(map: Map<String, String>): Call<VerifyResponse>

    fun resendPassword(map: Map<String, String>): Call<AccountResponse>

    fun getCategories(language: String): Call<CategoriesResponse>

    fun getNearestLocation(
        language: String,
        map: Map<String, String>
    ): Call<NearestBranchResponse>
}
