package com.hadrmout.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class VerifyResponse (
    @SerializedName("data")
    var `data`: String,
    @SerializedName("status")
    var status: Boolean,
    @SerializedName("error")
    var error: String

): Parcelable