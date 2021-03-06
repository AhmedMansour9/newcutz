package com.hadrmout.data

import com.hadrmout.data.remote.model.*
import com.hadrmout.data.remote.repository.ApiRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.QueryMap
import javax.inject.Inject

class DataCenterImpl @Inject constructor(
    private val apiRepository: ApiRepository
) :
    DataCenterManager {
    override fun newAccount(registerRequest: RegisterRequest): Call<AccountResponse> =
        apiRepository.userRegister(registerRequest)

    override fun editAccount(@Part img: MultipartBody.Part?,
                             @PartMap map:HashMap<String,@JvmSuppressWildcards RequestBody>, token: String): Call<DefultResponse> =
        apiRepository.editAccount(img,map,token)

    override  fun loginAccount(loginRequest: LoginRequest): Call<AccountResponse> =
        apiRepository.userLogin(loginRequest)


    override suspend fun loginFacebook(map: Map<String, String>): Call<AccountResponse> =
        apiRepository.loginFacebook(map)

    override fun getCategories(language: String, token: String,map: Map<String, String>): Call<SectonsResponse> =
        apiRepository.fetchCategories(language, token,map)

    override fun getCategories(language: String): Call<CategoriesResponse> = apiRepository.getCategories(language)
    override fun getBranches(language: String): Call<BranchesResponse> = apiRepository.getBranches(language)
    override fun getAddress(language: String, token: String): Call<AddressResponse> = apiRepository.getAddress(language, token)


    override fun addReviewProduct(
        token: String,
        review: RequestAddReviewResponse
    ): Call<AddReviewResponse> = apiRepository.addReview(token, review)

    override fun forgetPassword(map: Map<String, String>): Call<ForgetPasswordResponse> =
        apiRepository.forgetPassword(map)

    override fun getDeliveryFees(token: String): Call<ProductsResponse> =
        apiRepository.getDeliveryFees(token)

    override fun getReviewProduct(sku: String, token: String): Call<ListReviwesResponse> =
        apiRepository.getReviews(sku, token)

     override fun addFavourit(id: String, token: String): Call<AddToFavouritResponse> =
        apiRepository.addFavourit(id, token)

     override fun removeFavourit(id: String, token: String): Call<AddToFavouritResponse> =
        apiRepository.removeFavourit(id, token)


    override  fun getProductsById(
        lang: String,
        token: String,
        map: Map<String, String>
    ): Call<ProductsResponse> =
        apiRepository.fetchProductsById(lang, token, map)

    override  fun getWishList(lang: String, token: String): Call<ProductsResponse> =
        apiRepository.getWishList(lang, token)

    override  fun getOffers(lang: String, token: String,  map: Map<String, String>): Call<ProductsResponse>
    = apiRepository.getOffers(lang,token,map)

    override fun addToCart(
        id: String,
        token: String,
        registerRequest: RequestAddToCartResponse
    ): Call<AddToCartResponse> =
        apiRepository.addToCart(id,token, registerRequest)

    override  fun getListCart(lang: String, token: String): Call<ListCartResponse> =
        apiRepository.getlistCart(lang, token)

    override fun fetchBestProducts(
        language: String,
        token: String,
        map: Map<String, String>
    ): Call<BestSeller_Response> = apiRepository.fetchBestProducts(language,token, map)


    override fun deleteCart(cart_Id:String, @QueryMap map: Map<String, String>,token:String?): Call<AddToCartResponse> =
        apiRepository.deleteCart(cart_Id,map,token)

    override fun createCart(): Call<CreateCartResponse> =
        apiRepository.createCart()

    override fun addGustCart(registerRequest: AddGustCartResponse): Call<AddToCartResponse> =
        apiRepository.addGustCart(registerRequest)

    override fun getProfile(token: String): Call<ProfileResponse> =
        apiRepository.getProfile(token)

    override fun getBrand(language: String, token: String): Call<Brands_Response> =
        apiRepository.getBrand(language, token)

    override fun getCountries(): Call<CountriesResponse> = apiRepository.getCountries()

    override fun getShippingMethod(request: RequestShippingMethodResponse,token: String?): Call<ListShippingMethod> =
        apiRepository.getShippingMethod(request,token)

    override fun fetchCollections(language: String, token: String, map: Map<String, String>): Call<ProductsResponse> =
        apiRepository.fetchCollections(language,token,map)

    suspend override fun fetchBlogs(language: String, map: Map<String, String>): Response<BlogsResponse> = apiRepository.fetchBlogs(language,map)

    override fun getOrders(language: String,token: String): Call<MyOrdersResponse> =  apiRepository.getMyOrders(language,token)

    override  fun fetchAboutus(language: String): Call<AboutUsResponse> = apiRepository.fetchAboutus(language)

    override fun contactUs(registerRequest: RequestContactUs): Call<DefultResponse>  = apiRepository.contactUs(registerRequest)
    override suspend fun fetchSearchProducts(
        language: String,
        token: String,
        map: Map<String, String>
    ): Response<ProductsResponse> = apiRepository.fetchSearchProducts(language,token,map)

    override fun getRelated(
        language: String,
        token: String,
        map: Map<String, String>
    ): Call<ProductsResponse> = apiRepository.getRelated(language,token,map)

    override fun editCart(cart_Id:String,token: String, map: Map<String, String>): Call<AddToCartResponse>
    = apiRepository.editCart(cart_Id,token,map)


    override fun createOrder(
        request: RequestCreateOrder,
        token: String?
    ): Call<CreateOrderResponse> = apiRepository.createOrder(request,token)

    override fun createAddress(
        request:  HashMap<String,String>,
        token: String?
    ): Call<CreateAddressResponse> = apiRepository.createAddress(request,token)

    override fun addCoupon(map: Map<String, String>, token: String?): Call<AddCouponResponse> = apiRepository.addCoupon(map,token)

    override fun userVerify(map: Map<String, String>): Call<AccountResponse> = apiRepository.userVerify(map)
    override fun userLoginSocial(map: Map<String, String>): Call<AccountResponse> = apiRepository.userLoginSocial(map)
    override fun checkPhone(map: Map<String, String>): Call<VerifyResponse> = apiRepository.checkPhone(map)
    override fun resendPassword(map: Map<String, String>): Call<AccountResponse> = apiRepository.resendPassword(map)
    override fun getNearestLocation(
        language: String,
        map: Map<String, String>
    ): Call<NearestBranchResponse> = apiRepository.getNearestLocation(language,map)

}