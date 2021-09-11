package com.hadrmout.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
class RequestCreateOrder (
    @SerializedName("promocode")
    var code: String?,
    @SerializedName("discount")
    var discount: String?,
    @SerializedName("address_id")
    var address_id: String?

): Parcelable
