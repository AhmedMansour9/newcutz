package com.hadrmout.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class ListCartResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var error: String?,
    @SerializedName("status")
    var status: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("items")
        var cartItems: List<CartItem>?,
        @SerializedName("totalPriceItems")
        var totalCart: String
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class CartItem(
            @SerializedName("additions")
            var weights: MutableList<Addtion>,
            @SerializedName("rate")
            var avgRate: Double,
            @SerializedName("description")
            var description: String,
            @SerializedName("price")
            var finalPrice: String,
            @SerializedName("qty")
            var qty: String,
            @SerializedName("time")
            var time: String,
            @SerializedName("id")
            var id: String?,
            @SerializedName("image")
            var image: String?,
            @SerializedName("images")
            var images: MutableList<String>?,
            @SerializedName("in_favourites")
            var isFavoirte: Boolean,
            @SerializedName("original_price")
            var mainPrice: String,
            @SerializedName("short_description")
            var shortDescription: String,
            @SerializedName("name")
            var title: String,
            @SerializedName("total")
            var totalPriceItems: String?
        )  : Parcelable {
            @SuppressLint("ParcelCreator")
            @Parcelize
            data class Addtion(
                @SerializedName("id")
                var id: Int?,
                @SerializedName("price")
                var price: Int?,
                @SerializedName("name")
                var title: String?
            ) : Parcelable

                @SuppressLint("ParcelCreator")
                @Parcelize
                data class Image(
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