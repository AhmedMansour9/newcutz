package com.cairocart.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class ProductDeal_Response(
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
        @SerializedName("description")
        val description: String,
        @SerializedName("discount")
        val discount: Double,
        @SerializedName("id")
        val id: Int,
        @SerializedName("day")
        val day: Int,
        @SerializedName("hour")
        val hour: Int,
        @SerializedName("min")
        val min: Int,
        @SerializedName("images")
        val images: List<String>,
        @SerializedName("isFavorite")
        val isFavorite: Boolean,
        @SerializedName("more")
        val more: String,
        @SerializedName("quantity")
        val quantity: Int,
        @SerializedName("rate")
        val rate: Int,
        @SerializedName("reviews")
        val reviews: List<Review>,
        @SerializedName("sizes")
        var sizes: List<ProductDeal_Response.Data.Size?>?,

        @SerializedName("title")
        val title: String
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class Review(
            @SerializedName("description")
            val description: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("rate")
            val rate: String
        ) : Parcelable

        @SuppressLint("ParcelCreator")
        @Parcelize
        data class Size(
            @SerializedName("id")
            var id: Int?,
            @SerializedName("price")
            var price: String?,
            @SerializedName("quantity")
            var quantity: String?


        ) : Parcelable {
            override fun toString(): String {
                return quantity!!
            }
        }
    }
}