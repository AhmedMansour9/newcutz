package com.cairocart.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class SendRequestLogin_Model(
    @SerializedName("password")
    var password: String?,
    @SerializedName("username")
    var username: String?
) : Parcelable