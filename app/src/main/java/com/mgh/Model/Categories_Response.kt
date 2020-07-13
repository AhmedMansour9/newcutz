package com.mgh.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Categories_Response(
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
        var `data`: MutableList<Data>,
        @SerializedName("meta")
        var meta: Meta?
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class Data(
            @SerializedName("id")
            var id: Int?,
            @SerializedName("image")
            var image: String?,
            @SerializedName("name")
            var name: String?
        ) : Parcelable

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