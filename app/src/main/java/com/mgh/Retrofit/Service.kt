package com.mgh.Retrofit

import com.mgh.Model.*
import retrofit2.Call
import retrofit2.http.*

interface Service {
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("customer/changePassword")
    fun ChangePassword(
        @QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<Edit_ProfileResponse>

    @POST("signupCustomer")
     fun userRegister(
        @QueryMap map:Map<String,String>): Call<Register_Model>
////
    @POST("login")
    fun userLogin(
        @QueryMap map:Map<String,String>): Call<Register_Model>

    @POST("customer/carts")
    fun getCart( @Header("X-localization")lang:String,
                 @Header("Authorization")token:String): Call<AllProducts_Response>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("customer/getPromocodeDiscount")
    fun check_promo_code(
        @QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<PromoCode_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("{language}")
    fun Order(
        @Path(value = "language", encoded = true)language:String ,@QueryMap map:Map<String,String>
     ): Call<Order_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("{language}")
    fun Myorders(
        @Path(value = "language", encoded = true)language:String,@Header("Authorization")auth:String): Call<MyOrders_Response>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("{language}")
    fun MyordersDetails(
        @Path(value = "language", encoded = true)language:String): Call<OrderDetails_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("updateCart")
    fun PlusCart(@QueryMap map:Map<String,String>): Call<PlusCart_Response>



    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("customer/removeFromCart")
    fun DeleteCart(@QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<PlusCart_Response>



    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("min_quentity_Cart")
    fun MinusCart(@QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<PlusCart_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("customer/addToCart")
    fun AddToCart(
            @QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<AddToCart_Response>

    @POST("customer/social_login")
    fun userLoginFacebook(
        @QueryMap map:Map<String,String>): Call<Register_Model>

    @POST("products_size")
    fun Sizes(
            @QueryMap map:Map<String,String>): Call<Sizes_Response>

    @POST("{language}")
    fun AddetionalProducts(
             @Path(value = "language", encoded = true)language:String): Call<Reviews_Response>

    @POST("list_data_cart_additions")
    fun AddetionalCart(
            @QueryMap map:Map<String,String>): Call<ExtraAdditonal_Response>

    @POST("{language}")
    fun BestSallingProducts(
        @Header("X-localization")lang:String,
        @Path(value = "language", encoded = true)language:String,
        @Header("Authorization")token:String,
        @QueryMap map:Map<String,String>
       ): Call<AllProducts_Response>

    @POST("{language}")
    fun FilterProducts(
        @Header("X-localization")lang:String,
        @Path(value = "language", encoded = true)language:String,
        @Header("Authorization")token:String,
        @QueryMap map:Map<String,String>): Call<AllProducts_Response>


    @POST("categories")
    fun Categories(
        @QueryMap map:Map<String,String>): Call<Categories_Response>


    @POST("{language}")
    fun Blogs(
        @Path(value = "language", encoded = true)language:String ): Call<News_Response>


    @POST("sliders")
    fun SLider(): Call<SliderHome_Model>

    @POST("banners")
    fun Banners(): Call<SliderHome_Model>

    @POST("products_images")
    fun Products_SLider(
            @QueryMap map:Map<String,String>): Call<SliderHome_Model>


    @POST("staticPages")
    fun GetAboutus( @QueryMap queryMab: Map<String, String>): Call<About_Response>

    @POST("social")
    fun GetSocialMedia(@QueryMap queryMab: Map<String, String>): Call<SocialMedia_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("contact_message")
    fun ContactUs(@QueryMap queryMab: Map<String, String>): Call<ContactUs_Response>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("shippingCategories")
    fun getCities(@Header("X-localization")auth:String): Call<Cities_Response>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("{language}")
    fun getShipping(@Path(value = "language", encoded = true)language:String): Call<Cities_Response>

    @POST("{language}")
    fun AllProducts(
        @Path(value = "language", encoded = true)language:String): Call<ProductDeal_Response>


    @POST("list_favorite_product")
    fun FavouritProducts(
            @QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<Favourit_Response>

    @POST("{language}")
    fun AddFavouritProducts(
        @Path(value = "language", encoded = true)language:String,
        @QueryMap map:Map<String,String>,
        @Header("Authorization")token:String): Call<AddFav_Response>


    @POST("related_products")
    fun SimiliarProducts(
            @QueryMap map:Map<String,String>): Call<AllProducts_Response>

    @POST("{language}")
    fun ProductsByCatId(
        @Path(value = "language", encoded = true)language:String, @QueryMap map:Map<String,String>): Call<AllProducts_Response>






    @POST("customer/editCustomerProfile")
    fun EditProf(
        @QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<Edit_ProfileResponse>

//    @Headers("Content-Type: application/json;charset=UTF-8")
//    @POST("change_password")
//    fun ChangePassword(
//            @QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<Edit_ProfileResponse>


    @POST("customer/profile")
    fun Profile(
        @Header("Authorization")token:String): Call<Profile_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("customer/states")
    fun getStates(@QueryMap map:Map<String,String>,@Header("X-localization")auth:String): Call<Cities_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("customer/makeOrder")
    fun Order(
        @QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<Order_Response>

}