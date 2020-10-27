package com.cairocart.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Cart_Response(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: String,
    @SerializedName("status")
    val status: Boolean
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("items")
        val list: List<X>,
        @SerializedName("totalPrice")
        val price: Double
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class X(
            @SerializedName("id")
            val cartId: Int,
            @SerializedName("description")
            val description: String,
            @SerializedName("images")
            val image: List<String>,
            @SerializedName("product_id")
            val productId: String,
            @SerializedName("title")
            val productName: String,
            @SerializedName("userQuantity")
            val quantity: String,
            @SerializedName("size")
            val size: String,
            @SerializedName("total_price_with_addtions")
            val totalPriceWithAddtions: String,
            @SerializedName("total_unit_price")
            val totalUnitPrice: Int,
            @SerializedName("price")
            val unitPrice: String
        ) : Parcelable
    }
}