package com.cairocart.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class OrderDetails_Response(
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
        @SerializedName("code")
        val code: Int,
        @SerializedName("currency")
        val currency: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("products")
        val products: List<Product>,
        @SerializedName("shippingPrice")
        val shippingPrice: Int,
        @SerializedName("status")
        val status: String,
        @SerializedName("total")
        val total: Int
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class Product(
            @SerializedName("id")
            val id: Int,
            @SerializedName("image")
            val image: String,
            @SerializedName("price")
            val price: String,
            @SerializedName("quantity")
            val quantity: String?,
            @SerializedName("title")
            val title: String
        ) : Parcelable
    }
}