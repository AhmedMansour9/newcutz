package com.hadrmout.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class CategoriesResponse(
    @SerializedName("data")
    var `data`: List<Data>?,
    @SerializedName("message")
    var error: String?,
    @SerializedName("status")
    var status: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("id")
        var id: Int?,
        @SerializedName("icon")
        var image: String?,
        @SerializedName("parts")
        var parts: List<Part?>?,
        @SerializedName("section_id")
        var sectionId: Int?,
        @SerializedName("sub_categories")
        var subCategories: MutableList<SectonsResponse.Data.Category.SubCategory>,
        @SerializedName("name")
        var title: String?
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class Part(
            @SerializedName("id")
            var id: Int?,
            @SerializedName("title")
            var title: String?
        ) : Parcelable

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