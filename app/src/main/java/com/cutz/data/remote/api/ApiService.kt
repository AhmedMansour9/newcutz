package com.cutz.data.remote.api

import com.cutz.data.remote.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST(  "signupCustomer")
     fun userRegister(@Body registerRequest: RegisterRequest): Call<AccountResponse>

    @POST("login")
     fun userLogin(@Body loginRequest: LoginRequest): Call<AccountResponse>

    @POST("social_login")
    fun userLoginSocial(@QueryMap map: Map<String, String>): Call<AccountResponse>

    @POST("verifyAccount")
    fun userVerify(@QueryMap map: Map<String, String>): Call<AccountResponse>

    @POST("social_login")
    suspend fun loginFacebook(@QueryMap map: Map<String, String>): Call<AccountResponse>

    @GET( "sections")
     fun fetchCategories(
        @Header("X-localization")language: String,
        @Header("Authorization")token:String
    ): Call<SectonsResponse>

    @GET( "collections")
    fun fetchCollections(
        @Header("X-localization")language: String,
        @Header("Authorization")token:String
    ): Call<CollectionsResponse>


    @GET( "categories")
    fun getCategories(
        @Header("X-localization")language: String,
    ): Call<CategoriesResponse>


    @GET("products")
    suspend fun fetchProductsById(
        @Header("X-localization")language: String,
        @Header("Authorization")token:String,
        @QueryMap map: Map<String, String>
    ): Response<ProductsResponse>

    @GET("search")
    suspend fun fetchSearchProducts(
        @Header("X-localization")language: String,
        @Header("Authorization")token:String,
        @QueryMap map: Map<String, String>
    ): Response<ProductsResponse>

    @POST( "/rest/V1/reviews")
     fun addReview(
        @Header("Authorization")token:String,
        @Body review:RequestAddReviewResponse
    ): Call<AddReviewResponse>

    @GET( "/rest/V1/products/"+"{sku}"+"/reviews")
    fun getReviews(
        @Path(value = "sku", encoded = true) sku: String,
        @Header("Authorization")token:String
    ): Call<ListReviwesResponse>

    @FormUrlEncoded
    @POST( "customer/addTofavourite")
     fun addFavourit(
        @Field("product_id")product_id:String,
        @Header("Authorization")token:String
    ): Call<AddToFavouritResponse>

    @FormUrlEncoded
    @POST( "customer/deleteFavourite")
       fun removeFavourit(
        @Field("product_id")product_id:String,
        @Header("Authorization")token:String
    ): Call<AddToFavouritResponse>


    @POST( "customer/listDatafavourite")
     fun getWishList(
        @Header("X-localization")language: String,
        @Header("Authorization")token:String
    ): Call<ProductsResponse>

    @GET( "offers")
    suspend fun getOffers(
        @Header("X-localization")language: String,
        @Header("Authorization")token:String
    ): Response<ProductsResponse>


    @POST( "customer/addToCart")
    fun addToCart(
        @Header("Authorization")token:String,
        @Body registerRequest: RequestAddToCartResponse
    ): Call<AddToCartResponse>

    @POST( "customer/listDataCart")
     fun getlistCart(
        @Header("X-localization")language: String,
        @Header("Authorization")token:String,
    ): Call<ListCartResponse>

    @POST( "customer/editCart")
    fun editCart(
        @Header("Authorization")token:String,
        @QueryMap map: Map<String, String>
    ): Call<AddToCartResponse>


    @POST( "customer/deleteCart")
    fun deleteCart(
        @QueryMap map: Map<String, String>,
        @Header("Authorization")token:String?
    ): Call<AddToCartResponse>



    @POST( "rest/V1/restapi/quote/cart")
    fun createCart(): Call<CreateCartResponse>


    @POST( "rest/V1/restapi/quote/cart/items/add")
    fun addGustCart(
        @Body registerRequest: AddGustCartResponse
    ): Call<AddToCartResponse>


    @POST("customer/profile")
    fun getProfile(@Header("Authorization")token:String): Call<ProfileResponse>

    @GET("{language}" + "/rest/V1/restapi/brand")
    fun getBrand(
        @Path(
            value = "language",
            encoded = true
        ) language: String,
        @Header("Authorization")token:String
    ): Call<Brands_Response>

    @GET( "countries")
    fun getCountries(): Call<CountriesResponse>

    @POST( "/rest/V1/restapi/carts/shipping-methods")
    fun getShippingMethod(@Body request: RequestShippingMethodResponse, @Header("Authorization")token:String?): Call<ListShippingMethod>


    @GET( "blogs")
    suspend fun fetchBlogs(
        @Header("X-localization")language: String,
        @QueryMap map: Map<String, String>
    ): Response<BlogsResponse>

    @GET( "about")
    suspend fun fetchAboutus(
        @Header("X-localization")language: String,
    ): Response<AboutUsResponse>

    @POST( "customer/listOrder")
    fun getMyOrders( @Header("X-localization")language: String,@Header("Authorization")token:String): Call<MyOrdersResponse>

    @Multipart
    @POST("customer/editCustomerProfile")
    fun editAccount(@Part img: MultipartBody.Part?,
                     @PartMap map:HashMap<String,@JvmSuppressWildcards RequestBody>, @Header("Authorization")token:String): Call<DefultResponse>

    @POST(  "contact")
    fun contactUs(@Body registerRequest: RequestContactUs): Call<DefultResponse>

    @GET( "relatedProduct")
    fun getRelated(
        @Header("X-localization")language: String,
        @Header("Authorization")token:String,
        @QueryMap map: Map<String, String>
    ): Call<ProductsResponse>

    @POST( "customer/createOrder")
    fun createOrder(@Body request: RequestCreateOrder, @Header("Authorization")token:String?,
                    ): Call<CreateOrderResponse>

    @POST( "customer/addPromoCode")
    fun addCoupon(@QueryMap map: Map<String, String>, @Header("Authorization")token:String?,
    ): Call<AddCouponResponse>


    @POST("checkPhone")
     fun checkPhone(@QueryMap map: Map<String, String>): Call<VerifyResponse>

    @POST("RestPasswordByPhone")
    fun resendPassword(@QueryMap map: Map<String, String>): Call<AccountResponse>

}