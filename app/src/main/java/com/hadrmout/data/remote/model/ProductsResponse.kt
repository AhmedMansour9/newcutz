package com.hadrmout.data.remote.model


import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class ProductsResponse(
    @SerializedName("data")
    var `data`: MutableList<Data>,
    @SerializedName("message")
    var error: String?,
    @SerializedName("status")
    var status: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(

        @SerializedName("additions")
        var additions: List<Addition>,
        @SerializedName("weights")
        var weights: List<Weights>,
        @SerializedName("rate")
        var avgRate: Double,
        @SerializedName("description")
        var description: String,
        @SerializedName("total_price")
        var finalPrice: String,
        @SerializedName("time")
        var time: String,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("image")
        var image: String?,
        @SerializedName("slider")
        var images: List<String>,
        @SerializedName("in_favourites")
        var isFavoirte: Boolean,
        @SerializedName("original_price")
        var mainPrice: String,
        @SerializedName("short_description")
        var shortDescription: String,
        @SerializedName("name")
        var title: String
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class Weights(
            @SerializedName("id")
            var id: Int?,
            @SerializedName("price")
            var price: Int?,
            @SerializedName("name")
            var title: String

        ) : Parcelable {
            override fun toString(): String {
                return title
            }
        }

        @SuppressLint("ParcelCreator")
        @Parcelize
        data class Addition(
            @SerializedName("id")
            var id: Int,
            @SerializedName("name")
            var title: String,
            @SerializedName("price")
            var price: String,
        ) : Parcelable

    }
}