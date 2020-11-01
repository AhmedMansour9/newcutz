//package com.cairocart.old.Model
//
//
//import android.annotation.SuppressLint
//import android.os.Parcelable
//import androidx.annotation.Keep
//import com.google.gson.annotations.SerializedName
//import com.squareup.moshi.Json
//import com.squareup.moshi.JsonClass
//import kotlinx.android.parcel.Parcelize
//
////@Keep
////@JsonClass(generateAdapter = true)
////data class GenralResponse(
////    @Json(name = "data") val `data`: Data,
////    @Json(name = "message") val message: String,
////    @Json(name = "success") val success: Boolean
////) {
////    @Keep
////    @JsonClass(generateAdapter = true)
////    data class Data(
////        @Json(name = "data") val `data`: List<Data>,
////        @Json(name = "meta") val meta: Meta
////    ) {
////        @Keep
////        @JsonClass(generateAdapter = true)
////        data class Data(
////            @Json(name = "best_seller") val bestSeller: Boolean,
////            @Json(name = "brand") val brand: String,
////            @Json(name = "category") val category: String,
////            @Json(name = "count_solid") val countSolid: Int,
////            @Json(name = "created_by") val createdBy: String,
////            @Json(name = "currency") val currency: String,
////            @Json(name = "description") val description: String,
////            @Json(name = "discount") val discount: Int,
////            @Json(name = "expire_date_hot_deal") val expireDateHotDeal: String,
////            @Json(name = "featured") val featured: Boolean,
////            @Json(name = "hot_deal") val hotDeal: Boolean,
////            @Json(name = "hot_deal_price") val hotDealPrice: Int,
////            @Json(name = "id") val id: Int,
////            @Json(name = "image") val image: String,
////            @Json(name = "is_new") val isNew: Boolean,
////            @Json(name = "IsProductFavoirte") val isProductFavoirte: Int,
////            @Json(name = "link_youtube") val linkYoutube: String,
////            @Json(name = "name") val name: String,
////            @Json(name = "number_clicks") val numberClicks: Int,
////            @Json(name = "number_views") val numberViews: Int,
////            @Json(name = "off_50") val off50: Boolean,
////            @Json(name = "on_sale") val onSale: Boolean,
////            @Json(name = "porduct_sku_code") val porductSkuCode: String,
////            @Json(name = "product_code") val productCode: String,
////            @Json(name = "productImages") val productImages: List<ProductImage>,
////            @Json(name = "ProductInCart") val ProductInCart: Int,
////            @Json(name = "ProductInCartColor") val ProductInCartColor: String,
////            @Json(name = "ProductInCartQty") val ProductInCartQty: Int,
////            @Json(name = "ProductInCartTotal") val ProductInCartTotal: Int,
////            @Json(name = "product_serial_number") val productSerialNumber: String,
////            @Json(name = "reviews") val reviews: List<String>,
////            @Json(name = "sale_price") val salePrice: Int,
////            @Json(name = "short_description") val short_description: String,
////            @Json(name = "status") val status: String,
////            @Json(name = "stock") val stock: Int,
////            @Json(name = "stock_limit_alert") val stockLimitAlert: Int,
////            @Json(name = "subcategory") val subcategory: String,
////            @Json(name = "total") val total: Int,
////            @Json(name = "total_number_review") val total_number_review: Int,
////            @Json(name = "total_rate") val total_rate: Int,
////            @Json(name = "total_with_currency") val totalWithCurrency: String,
////            @Json(name = "trending") val trending: Boolean,
////            @Json(name = "updated_by") val updatedBy: String
////        ) {
////            @Keep
////            @JsonClass(generateAdapter = true)
////            data class ProductImage(
////                @Json(name = "id") val id: Int,
////                @Json(name = "image") val image: String,
////                @Json(name = "product_id") val productId: Int
////            ) }
////
////        @Keep
////        @JsonClass(generateAdapter = true)
////        data class Meta(
////            @Json(name = "current_page") val currentPage: Int,
////            @Json(name = "hasMorePages") val hasMorePages: Boolean,
////            @Json(name = "last_page") val lastPage: Int,
////            @Json(name = "per_page") val perPage: Int,
////            @Json(name = "total") val total: Int
////        )
////    }
////}
//
//
//@Parcelize
// data class GenralResponse(
//    var `data`: Data?,
//    var message: String?,
//    @SerializedName("success")
//    var success: Boolean?
//) : Parcelable
//@SuppressLint("ParcelCreator")
//@Parcelize
//data class Data(
//   @SerializedName("data")
//   var `data`: MutableList<Data>?= arrayListOf(),
//   @SerializedName("meta")
//   var meta: Meta?
//) : Parcelable {
//
//
//
//}
//@SuppressLint("ParcelCreator")
//@Parcelize
//data class Data(
//   @SerializedName("best_seller")
//   var bestSeller: String?,
//   @SerializedName("brand")
//   var brand: String?,
//   @SerializedName("category")
//   var category: String?,
//   @SerializedName("count_solid")
//   var countSolid: Int?,
//   @SerializedName("created_by")
//   var createdBy: String?,
//   @SerializedName("currency")
//   var currency: String?,
//   @SerializedName("description")
//   var description: String?,
//   @SerializedName("discount")
//   var discount: Double?,
//   @SerializedName("expire_date_hot_deal")
//   var expireDateHotDeal: String?,
//   @SerializedName("featured")
//   var featured: String?,
//   @SerializedName("hot_deal")
//   var hotDeal: String?,
//   @SerializedName("hot_deal_price")
//   var hotDealPrice: String?,
//   @SerializedName("id")
//   var id: Int?,
//   @SerializedName("image")
//   var image: String?,
//   @SerializedName("is_new")
//   var isNew: String?,
//   @SerializedName("IsProductFavoirte")
//   var isProductFavoirte: Int?,
//   @SerializedName("link_youtube")
//   var linkYoutube: String?,
//   @SerializedName("name")
//   var name: String?,
//   @SerializedName("number_clicks")
//   var numberClicks: Int?,
//   @SerializedName("number_views")
//   var numberViews: Int?,
//   @SerializedName("off_50")
//   var off50: String?,
//   @SerializedName("on_sale")
//   var onSale: String?,
//   @SerializedName("porduct_sku_code")
//   var porductSkuCode: String?,
//   @SerializedName("product_code")
//   var productCode: String?,
//   @SerializedName("productImages")
//   var productImages: MutableList<ProductImage?>?,
//   @SerializedName("ProductInCart")
//   var ProductInCart: Int?,
//   @SerializedName("ProductInCartQty")
//   var ProductInCartQty: Int?,
//   @SerializedName("ProductInCartTotal")
//   var ProductInCartTotal: Double?,
//   @SerializedName("product_serial_number")
//   var productSerialNumber: String?,
//   @SerializedName("reviews")
//   var reviews: MutableList<Review>,
//   @SerializedName("sale_price")
//   var salePrice: Double,
//   @SerializedName("short_description")
//   var shortDescription: String?,
//   @SerializedName("status")
//   var status: String?,
//   @SerializedName("stock")
//   var stock: Int?,
//   @SerializedName("stock_limit_alert")
//   var stockLimitAlert: Int?,
//   @SerializedName("subcategory")
//   var subcategory: String?,
//   @SerializedName("total")
//   var total: Double?,
//   @SerializedName("total_number_review")
//   var total_number_review: Int?,
//   @SerializedName("total_rate")
//   var totalRate: Double?,
//   @SerializedName("total_with_currency")
//   var totalWithCurrency: String?,
//   @SerializedName("trending")
//   var trending: String?,
//   @SerializedName("updated_by")
//   var updatedBy: String?,
//   @SerializedName("product_link")
//   var product_link: String?,
//   @SerializedName("unit")
//   var unit: String?,
//   @SerializedName("unit_value")
//   var unit_value: String?
//
//) : Parcelable {
//   @SuppressLint("ParcelCreator")
//   @Parcelize
//   data class ProductImage(
//      @SerializedName("id")
//      var id: Int?,
//      @SerializedName("image")
//      var image: String?,
//      @SerializedName("product_id")
//      var productId: Int?
//   ) : Parcelable
//
//   @SuppressLint("ParcelCreator")
//   @Parcelize
//   data class Review(
//      @SerializedName("comment")
//      var comment: String?,
//      @SerializedName("created_at")
//      var createdAt: String?,
//      @SerializedName("customer")
//      var customer: Customer?,
//      @SerializedName("review")
//      var review: Int?
//   ) : Parcelable {
//      @SuppressLint("ParcelCreator")
//      @Parcelize
//      data class Customer(
//         @SerializedName("created_at")
//         var createdAt: String?,
//         @SerializedName("email")
//         var email: String?,
//         @SerializedName("firebaseToken")
//         var firebaseToken: String?,
//         @SerializedName("full_name")
//         var fullName: String?,
//         @SerializedName("gender")
//         var gender: String?,
//         @SerializedName("image")
//         var image: String?,
//         @SerializedName("phone")
//         var phone: String?,
//         @SerializedName("status")
//         var status: String?
//      ) : Parcelable
//   }
//}
//@SuppressLint("ParcelCreator")
//@Parcelize
//data class Meta(
//   @SerializedName("current_page")
//   var currentPage: Int?,
//   @SerializedName("hasMorePages")
//   var hasMorePages: Boolean?,
//   @SerializedName("last_page")
//   var lastPage: Int?,
//   @SerializedName("per_page")
//   var perPage: Int?,
//   @SerializedName("total")
//   var total: Int?
//) : Parcelable