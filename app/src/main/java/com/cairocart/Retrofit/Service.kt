package com.cairocart.Retrofit

import com.cairocart.Model.*
import retrofit2.Call
import retrofit2.http.*

interface Service {
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("customer/changePassword")
    fun ChangePassword(
        @QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<Edit_ProfileResponse>

    @POST("en"+"/rest/V1/restapi/register")
     fun userRegister( @Body Requests: SendRegisterRequest_Model
        ): Call<Register_Model>


    @POST("en/rest/V1/restapi/login")
    fun userLogin(
        @Body Requests: SendRequestLogin_Model): Call<Register_Model>

    @POST("customer/carts")
    fun getCart( @Header("X-localization")lang:String,
                 @Header("Authorization")token:String): Call<AllProducts_Response>

    @POST("customer/getOrderTaxes")
    fun getTaxes( @Header("X-localization")lang:String,
                 @Header("Authorization")token:String): Call<Taxes_Response>


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
    fun Myorders(@Header("X-localization")lang:String,
        @Path(value = "language", encoded = true)language:String,@Header("Authorization")auth:String): Call<MyOrders_Response>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("{language}")
    fun MyordersDetails(
        @Path(value = "language", encoded = true)language:String): Call<OrderDetails_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("updateCart")
    fun PlusCart(@QueryMap map:Map<String,String>): Call<PlusCart_Response>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("password/create")
    fun ForgetPassword(@QueryMap queryMab: Map<String, String>): Call<ForgetPassword_Response>


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

    @POST("social_login")
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

    @POST("filterSearchProduct")
    fun SarchFilterProducts(
        @Header("X-localization")lang:String,
        @Header("Authorization")token:String,
        @QueryMap map:Map<String,String>): Call<AllProducts_Response>

    @GET("{language}"+"/rest/V1/mstore/categories")
    fun Categories(
        @Path(value = "language", encoded = true)language:String): Call<Categories_Response>


    @POST("{language}")
    fun Blogs(
        @Path(value = "language", encoded = true)language:String,
        @Header("X-localization")lang:String): Call<Blogs_Response>

    @POST("customer/notificaitons")
    fun Notigications(@Header("X-localization")lang:String,@Header("Authorization")auth:String): Call<AllNotifications_Response>

    @POST("customer/count_notificaitons")
    fun NotigicationCount(@Header("Authorization")auth:String): Call<NotificationCounter_Response>


    @POST("sliders")
    fun SLider(): Call<SliderHome_Model>

    @POST("banners")
    fun Banners(): Call<SliderHome_Model>

    @POST("products_images")
    fun Products_SLider(
            @QueryMap map:Map<String,String>): Call<SliderHome_Model>


    @POST("staticPages")
    fun GetAboutus( @QueryMap queryMab: Map<String, String>): Call<About_Response>

    @POST("website_info")
    fun GetSocialMedia(@QueryMap queryMab: Map<String, String>): Call<SocialResponse>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("contactUs")
    fun ContactUs(@QueryMap queryMab: Map<String, String>): Call<ContactUs_Response>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("customer/setReview")
    fun Review(@QueryMap queryMab: Map<String, String>,@Header("Authorization")auth:String): Call<ContactUs_Response>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("customer/reply_message")
    fun ReplyNotification(@QueryMap queryMab: Map<String, String>,@Header("Authorization")auth:String): Call<ContactUs_Response>


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

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("cities")
    fun getCountries(@QueryMap map:Map<String,String>,@Header("X-localization")lang:String): Call<Cities_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("receivepoints")
    fun getRecivesPoints(@QueryMap map:Map<String,String>,@Header("X-localization")lang:String): Call<RecivesPoints_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("states")
    fun getStates(@QueryMap map:Map<String,String>,@Header("X-localization")lang:String): Call<Cities_Response>


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
    @POST("customer/makeOrder")
    fun Order(
        @QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<Order_Response>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("{language}")
    fun MyGifts(
        @Path(value = "language", encoded = true)language:String,@Header("Authorization")auth:String): Call<Gifts_Response>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("{language}")
    fun AddGifts(
        @Path(value = "language", encoded = true)language:String,@QueryMap map:Map<String,String>
        ,@Header("Authorization")auth:String): Call<AddToCart_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("customer/updateFirebaseToken")
    fun SentToken(@QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<SentMessage_Response>


}