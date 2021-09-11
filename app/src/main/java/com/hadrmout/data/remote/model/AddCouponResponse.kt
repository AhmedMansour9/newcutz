package com.hadrmout.data.remote.model


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
        @SerializedName("promocode")
        var code: String?,
        @SerializedName("discount")
        var discount: Double?,
        @SerializedName("cart_total")
        var total: Double?
    ) : Parcelable
}