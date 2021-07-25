package com.cutzegypt.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class SectionsResponse(
    @SerializedName("data")
    var `data`: List<Data>?,
    @SerializedName("error")
    var error: String?,
    @SerializedName("status")
    var status: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("categories")
        var categories: List<Category>?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("image")
        var image: String?,

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
            var subCategories: List<SubCategory?>?,
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
        }
    }
}