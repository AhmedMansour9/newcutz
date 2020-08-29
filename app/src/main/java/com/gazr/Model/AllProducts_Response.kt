package com.gazr.Model


import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class AllProducts_Response(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("success")
    var success: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("data")
        var `data`: List<Data>,
        @SerializedName("meta")
        var meta: Meta?
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class Data(
            @SerializedName("best_seller")
            var bestSeller: String?,
            @SerializedName("brand")
            var brand: String?,
            @SerializedName("category")
            var category: String?,
            @SerializedName("count_solid")
            var countSolid: Int?,
            @SerializedName("created_by")
            var createdBy: String?,
            @SerializedName("currency")
            var currency: String?,
            @SerializedName("description")
            var description: String?,
            @SerializedName("discount")
            var discount: Int?,
            @SerializedName("expire_date_hot_deal")
            var expireDateHotDeal: String?,
            @SerializedName("featured")
            var featured: String?,
            @SerializedName("hot_deal")
            var hotDeal: String?,
            @SerializedName("hot_deal_price")
            var hotDealPrice: String?,
            @SerializedName("id")
            var id: Int?,
            @SerializedName("image")
            var image: String?,
            @SerializedName("is_new")
            var isNew: String?,
            @SerializedName("IsProductFavoirte")
            var isProductFavoirte: Int?,
            @SerializedName("link_youtube")
            var linkYoutube: String?,
            @SerializedName("name")
            var name: String?,
            @SerializedName("number_clicks")
            var numberClicks: Int?,
            @SerializedName("number_views")
            var numberViews: Int?,
            @SerializedName("off_50")
            var off50: String?,
            @SerializedName("on_sale")
            var onSale: String?,
            @SerializedName("porduct_sku_code")
            var porductSkuCode: String?,
            @SerializedName("product_code")
            var productCode: String?,
            @SerializedName("productImages")
            var productImages: List<ProductImage?>?,
            @SerializedName("ProductInCart")
            var productInCart: Int?,
            @SerializedName("ProductInCartQty")
            var productInCartQty: Int?,
            @SerializedName("ProductInCartTotal")
            var productInCartTotal: Double?,
            @SerializedName("product_serial_number")
            var productSerialNumber: String?,
            @SerializedName("reviews")
            var reviews: List<Review>,
            @SerializedName("sale_price")
            var salePrice: Int?,
            @SerializedName("short_description")
            var shortDescription: String?,
            @SerializedName("status")
            var status: String?,
            @SerializedName("stock")
            var stock: Int?,
            @SerializedName("stock_limit_alert")
            var stockLimitAlert: Int?,
            @SerializedName("subcategory")
            var subcategory: String?,
            @SerializedName("total")
            var total: String?,
            @SerializedName("total_number_review")
            var totalNumberReview: Int?,
            @SerializedName("total_rate")
            var totalRate: Double?,
            @SerializedName("total_with_currency")
            var totalWithCurrency: String?,
            @SerializedName("trending")
            var trending: String?,
            @SerializedName("updated_by")
            var updatedBy: String?,
            @SerializedName("product_link")
            var product_link: String?,
           @SerializedName("unit")
            var unit: String?,
            @SerializedName("unit_value")
            var unit_value: String?

        ) : Parcelable {
            @SuppressLint("ParcelCreator")
            @Parcelize
            data class ProductImage(
                @SerializedName("id")
                var id: Int?,
                @SerializedName("image")
                var image: String?,
                @SerializedName("product_id")
                var productId: Int?
            ) : Parcelable

            @SuppressLint("ParcelCreator")
            @Parcelize
            data class Review(
                @SerializedName("comment")
                var comment: String?,
                @SerializedName("created_at")
                var createdAt: String?,
                @SerializedName("customer")
                var customer: Customer?,
                @SerializedName("review")
                var review: Int?
            ) : Parcelable {
                @SuppressLint("ParcelCreator")
                @Parcelize
                data class Customer(
                    @SerializedName("created_at")
                    var createdAt: String?,
                    @SerializedName("email")
                    var email: String?,
                    @SerializedName("firebaseToken")
                    var firebaseToken: String?,
                    @SerializedName("full_name")
                    var fullName: String?,
                    @SerializedName("gender")
                    var gender: String?,
                    @SerializedName("image")
                    var image: String?,
                    @SerializedName("phone")
                    var phone: String?,
                    @SerializedName("status")
                    var status: String?
                ) : Parcelable
            }
        }

        @SuppressLint("ParcelCreator")
        @Parcelize
        data class Meta(
            @SerializedName("current_page")
            var currentPage: Int?,
            @SerializedName("hasMorePages")
            var hasMorePages: Boolean?,
            @SerializedName("last_page")
            var lastPage: Int?,
            @SerializedName("per_page")
            var perPage: Int?,
            @SerializedName("total")
            var total: Int?
        ) : Parcelable
    }
}