package com.gazr.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class MyOrders_Response(
    @SerializedName("data")
    var `data`: List<Data?>?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("success")
    var success: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("created_at")
        var createdAt: String?,
        @SerializedName("customer_address")
        var customerAddress: String?,
        @SerializedName("city")
        var customerCity: String?,
        @SerializedName("customer_floor_number")
        var customerFloorNumber: String?,
        @SerializedName("customer_home_number")
        var customerHomeNumber: String?,
        @SerializedName("customer_name")
        var customerName: String?,
        @SerializedName("customer_phone")
        var customerPhone: String?,
        @SerializedName("customer_region")
        var customerRegion: String?,
        @SerializedName("customer_street")
        var customerStreet: String?,
        @SerializedName("delivery_fees")
        var delivery_fees: String?,
        @SerializedName("id")

        var id: Int?,
        @SerializedName("orderDetails")
        var orderDetails: List<OrderDetail>,
        @SerializedName("payment_method")
        var paymentMethod: String?,
        @SerializedName("payment_status")
        var paymentStatus: Int?,
        @SerializedName("promocode")
        var promocode: String?,
        @SerializedName("promocode_value")
        var promocodeValue: Int?,
        @SerializedName("shipping_number")
        var shippingNumber: String?,
        @SerializedName("total")
        var total: Int?,
        @SerializedName("status")
        var status: String?
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class OrderDetail(
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
            var productInCartTotal: Int?,
            @SerializedName("product_serial_number")
            var productSerialNumber: String?,
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
            var total: Int?,
            @SerializedName("total_number_review")
            var totalNumberReview: Int?,
            @SerializedName("total_rate")
            var totalRate: Int?,
            @SerializedName("total_with_currency")
            var totalWithCurrency: String?,
            @SerializedName("trending")
            var trending: String?,
            @SerializedName("updated_by")
            var updatedBy: String?
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
        }
    }
}