package com.cutz.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class AddCouponResponse(
    @SerializedName("data")
    var `data`: Data,
    @SerializedName("error")
    var error: String?,
    @SerializedName("status")
    var status: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("code")
        var code: String?,
        @SerializedName("discount")
        var discount: Double?,
        @SerializedName("total")
        var total: Int?,
        @SerializedName("totalWithPromo")
        var totalWithPromo: Int?
    ) : Parcelable
}