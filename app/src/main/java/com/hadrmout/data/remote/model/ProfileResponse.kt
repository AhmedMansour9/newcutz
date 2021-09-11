package com.hadrmout.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class ProfileResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var error: String?,
    @SerializedName("status")
    var status: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("email")
        var email: String?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("avatar")
        var image: String?,
        @SerializedName("name")
        var name: String?,
        @SerializedName("phone")
        var phone: String?
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class CreatedAt(
            @SerializedName("date")
            var date: String?,
            @SerializedName("timezone")
            var timezone: String?,
            @SerializedName("timezone_type")
            var timezoneType: Int?
        ) : Parcelable
    }
}