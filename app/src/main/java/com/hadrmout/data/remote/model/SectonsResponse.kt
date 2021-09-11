package com.hadrmout.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class SectonsResponse(
    @SerializedName("data")
    var `data`: MutableList<Data>?,
    @SerializedName("error")
    var error: String?,
    @SerializedName("status")
    var status: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("sub_categories")
        var categories: MutableList<Category>?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("icon")
        var image: String?="",
        @SerializedName("image2")
        var image2: String?="",
        @SerializedName("name")
        var title: String?
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class Category(
            @SerializedName("id")
            var id: Int?,
            @SerializedName("icon")
            var image: String?,

            @SerializedName("name")
            var title: String?
        ) : Parcelable {
            @SuppressLint("ParcelCreator")
            @Parcelize
            data class SubCategory(
                @SerializedName("id")
                var id: Int?,
                @SerializedName("icon")
                var image: String?,
                @SerializedName("name")
                var title: String?
            ) : Parcelable

            @SuppressLint("ParcelCreator")
            @Parcelize
            data class Parts(
                @SerializedName("id")
                var id: Int,
                @SerializedName("title")
                var title: String
            ) : Parcelable
        }

    }

}