package com.cairocart.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class SocialResponse(
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
        @SerializedName("address")
        var address: String?,
        @SerializedName("fax")
        var fax: String?,
        @SerializedName("phone")
        var phone: String?,
        @SerializedName("socail_media")
        var socailMedia: List<SocailMedia?>?,
        @SerializedName("whatsapp")
        var whatsapp: String?
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class SocailMedia(
            @SerializedName("link")
            var link: String?,
            @SerializedName("name")
            var name: String?
        ) : Parcelable
    }
}