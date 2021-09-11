package com.hadrmout.data.remote.api

import com.hadrmout.data.remote.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("register")
     fun userRegister(@Body registerRequest: RegisterRequest): Call<AccountResponse>

    @POST("login")
     fun userLogin(@Body loginRequest: LoginRequest): Call<AccountResponse>

    @POST("social_login")
    fun userLoginSocial(@QueryMap map: Map<String, String>): Call<AccountResponse>

    @POST("verifyAccount")
    fun userVerify(@QueryMap map: Map<String, String>): Call<AccountResponse>

    @POST("social_login")
    suspend fun loginFacebook(@QueryMap map: Map<String, String>): Call<AccountResponse>

    @GET("categories")
     fun fetchCategories(
        @Header("lang") language: String,
        @Header("Authorization") token: String,
        @QueryMap map: Map<String, String>
    ): Call<SectonsResponse>

    @GET("home")
    fun fetchCollections(
        @Header("lang") language: String,
        @Header("Authorization") token: String,
        @QueryMap map: Map<String, String>
    ): Call<ProductsResponse>


    @GET( "categories")
    fun getCategories(
        @Header("lang") language: String,
    ): Call<CategoriesResponse>

    @GET( "listBranches")
    fun getBranches(
        @Header("lang") language: String,
    ): Call<BranchesResponse>

    @GET("products")
    fun fetchProductsById(
        @Header("lang") language: String,
        @Header("Authorization") token: String,
        @QueryMap map: Map<String, String>
    ): Call<ProductsResponse>

    @GET("home")
     fun fetchBestProducts(
        @Header("lang") language: String,
        @Header("Authorization") token: String,
        @QueryMap map: Map<String, String>
    ): Call<BestSeller_Response>

    @GET("search")
    suspend fun fetchSearchProducts(
        @Header("lang") language: String,
        @Header("Authorization") token: String,
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

    @GET("add-favourite/" + "{product_id}")
     fun addFavourit(
        @Path(
            value = "product_id",
            encoded = true
        ) language: String,
        @Header("Authorization") token: String
    ): Call<AddToFavouritResponse>

    @DELETE("remove-favourite/" + "{product_id}")
       fun removeFavourit(
        @Path(
            value = "product_id",
            encoded = true
        ) language: String,
        @Header("Authorization") token: String
    ): Call<AddToFavouritResponse>


    @GET("favourites")
    fun getWishList(
        @Header("lang") language: String,
        @Header("Authorization") token: String
    ): Call<ProductsResponse>

    @GET("delivery-fees")
    fun getDeliveryFees(
        @Header("Authorization") token: String
    ): Call<ProductsResponse>

    @POST("forget-password")
    fun forgetPassword(
        @QueryMap map: Map<String, String>
    ): Call<ForgetPasswordResponse>

    @GET("addresses")
    fun getAddress(
        @Header("lang") language: String,
        @Header("Authorization") token: String
    ): Call<AddressResponse>

    @GET("offers")
    fun getOffers(
        @Header("lang") language: String,
        @Header("Authorization") token: String,
        @QueryMap map: Map<String, String>

    ): Call<ProductsResponse>


    @GET( "nearstBranch")
     fun getNearestLocation(
        @Header("lang") language: String,
        @QueryMap map: Map<String, String>
    ): Call<NearestBranchResponse>


    @POST("add-cart/" + "{id}")
    fun addToCart(
        @Path(
            value = "id",
            encoded = true
        ) language: String,
        @Header("Authorization") token: String,
        @Body registerRequest: RequestAddToCartResponse
    ): Call<AddToCartResponse>

    @GET("cart")
     fun getlistCart(
        @Header("lang") language: String,
        @Header("Authorization") token: String,
    ): Call<ListCartResponse>

    @POST("update-cart/" + "{id}")
    fun editCart(
        @Path(
            value = "id",
            encoded = true
        ) language: String,
        @Header("Authorization") token: String,
        @QueryMap map: Map<String, String>
    ): Call<AddToCartResponse>


    @DELETE("remove-cart/" + "{id}")
    fun deleteCart(
        @Path(
            value = "id",
            encoded = true
        ) language: String,
        @QueryMap map: Map<String, String>,
        @Header("Authorization") token: String?
    ): Call<AddToCartResponse>



    @POST( "rest/V1/restapi/quote/cart")
    fun createCart(): Call<CreateCartResponse>


    @POST( "rest/V1/restapi/quote/cart/items/add")
    fun addGustCart(
        @Body registerRequest: AddGustCartResponse
    ): Call<AddToCartResponse>


    @GET("profile")
    fun getProfile(@Header("Authorization")token:String): Call<ProfileResponse>

    @GET("{language}" + "/rest/V1/restapi/brand")
    fun getBrand(
        @Path(
            value = "language",
            encoded = true
        ) language: String,
        @Header("Authorization")token:String
    ): Call<Brands_Response>

    @GET("cities")
    fun getCountries(): Call<CountriesResponse>

    @POST("/rest/V1/restapi/carts/shipping-methods")
    fun getShippingMethod(
        @Body request: RequestShippingMethodResponse,
        @Header("Authorization") token: String?
    ): Call<ListShippingMethod>


    @GET("blogs")
    suspend fun fetchBlogs(
        @Header("lang") language: String,
        @QueryMap map: Map<String, String>
    ): Response<BlogsResponse>

    @GET("about-us")
    fun fetchAboutus(
        @Header("lang") language: String,
    ): Call<AboutUsResponse>

    @GET("orders")
    fun getMyOrders(
        @Header("lang") language: String,
        @Header("Authorization") token: String
    ): Call<MyOrdersResponse>

    @Multipart
    @POST("update-profile")
    fun editAccount(
        @Part img: MultipartBody.Part?,
        @PartMap map: HashMap<String, @JvmSuppressWildcards RequestBody>,
        @Header("Authorization") token: String
    ): Call<DefultResponse>

    @POST("contact")
    fun contactUs(@Body registerRequest: RequestContactUs): Call<DefultResponse>

    @GET("relatedProduct")
    fun getRelated(
        @Header("lang") language: String,
        @Header("Authorization") token: String,
        @QueryMap map: Map<String, String>
    ): Call<ProductsResponse>

    @POST("add-order")
    fun createOrder(
        @Body request: RequestCreateOrder, @Header("Authorization") token: String?,
    ): Call<CreateOrderResponse>

    @POST("addresses")
    fun createAddress(
        @Body request: HashMap<String, String>, @Header("Authorization") token: String?,
    ): Call<CreateAddressResponse>

    @POST("promo-code")
    fun addCoupon(
        @QueryMap map: Map<String, String>, @Header("Authorization") token: String?,
    ): Call<AddCouponResponse>


    @POST("checkPhone")
    fun checkPhone(@QueryMap map: Map<String, String>): Call<VerifyResponse>

    @POST("RestPasswordByPhone")
    fun resendPassword(@QueryMap map: Map<String, String>): Call<AccountResponse>

}