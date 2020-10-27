package com.cairocart.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Categories_Response(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("status")
    var status: Status?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("children_data")
        var childrenData: List<ChildrenData?>?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("image")
        var image: String?,
        @SerializedName("is_active")
        var isActive: Boolean?,
        @SerializedName("level")
        var level: Int?,
        @SerializedName("name")
        var name: String?,
        @SerializedName("parent_id")
        var parentId: Int?,
        @SerializedName("position")
        var position: Int?,
        @SerializedName("product_count")
        var productCount: Int?
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class
        ChildrenData(
            @SerializedName("children_data")
            var childrenData: List<ChildrenData?>?,
            @SerializedName("id")
            var id: Int?,
            @SerializedName("image")
            var image: String?,
            @SerializedName("is_active")
            var isActive: Boolean?,
            @SerializedName("level")
            var level: Int?,
            @SerializedName("name")
            var name: String?,
            @SerializedName("parent_id")
            var parentId: Int?,
            @SerializedName("position")
            var position: Int?,
            @SerializedName("product_count")
            var productCount: Int?
        ) : Parcelable
    }

    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Status(
        @SerializedName("code")
        var code: Int?,
        @SerializedName("message")
        var message: String?
    ) : Parcelable

}