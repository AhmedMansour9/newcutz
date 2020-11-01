package com.cairocart.old.Model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class SendRequestLogin_Model(
    @SerializedName("password")
    var password: String?,
    @SerializedName("username")
    var username: String?
) : Parcelable
