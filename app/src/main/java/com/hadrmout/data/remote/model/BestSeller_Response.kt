package com.hadrmout.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class BestSeller_Response(
    @SerializedName("data")
    var `data`: List<ProductsResponse.Data>,
    @SerializedName("error")
    var error: String?,
    @SerializedName("status")
    var status: Boolean?
) :Parcelable
