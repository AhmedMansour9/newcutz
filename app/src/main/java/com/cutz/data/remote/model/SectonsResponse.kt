package com.cutz.data.remote.model


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
        @SerializedName("categories")
        var categories: MutableList<Category>?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("image")
        var image: String?="",
        @SerializedName("image2")
        var image2: String?="",
        @SerializedName("title")
        var title: String?
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class Category(
            @SerializedName("id")
            var id: Int?,
            @SerializedName("image")
            var image: String?,
            @SerializedName("section_id")
            var sectionId: Int?,
            @SerializedName("sub_categories")
            var subCategories: MutableList<SubCategory>,
            @SerializedName("parts")
            var parts: MutableList<Parts>,

            @SerializedName("title")
            var title: String?
        ) : Parcelable {
            @SuppressLint("ParcelCreator")
            @Parcelize
            data class SubCategory(
                @SerializedName("category_id")
                var categoryId: Int?,

                @SerializedName("id")
                var id: Int?,
                @SerializedName("image")
                var image: String?,
                @SerializedName("title")
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