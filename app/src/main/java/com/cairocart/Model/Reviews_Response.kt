package com.cairocart.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Reviews_Response(
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
        @SerializedName("rate")
        val rate: Int,
        @SerializedName("reviews")
        val reviews: List<Review>,
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
    }
}