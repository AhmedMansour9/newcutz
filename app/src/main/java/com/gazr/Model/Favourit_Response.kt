package com.gazr.Model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@SuppressLint("ParcelCreator")
@Parcelize
class Favourit_Response (
        @SerializedName("data")
        val `data`: List<AllProducts_Model>,
        @SerializedName("error")
        val error: String,
        @SerializedName("status")
        val status: Boolean
): Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class AllProducts_Model(
            @SerializedName("description")
            val description: String,
            @SerializedName("id")
            val product_id: String,
            @SerializedName("short_description")
            val shortdescription: String,
            @SerializedName("price_general")
            val price_general: String,
            @SerializedName("benefits")
            val benefits: String,
            @SerializedName("ingredients")
            val ingredients: String,
            @SerializedName("currency")
            val currency: String,
            @SerializedName("Wishlist_state")
            var Wishlist_state: Boolean,
            @SerializedName("image")
            val image: String,
            @SerializedName("title")
            val title: String
    ) : Parcelable
}