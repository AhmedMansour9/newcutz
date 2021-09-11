package com.hadrmout.data.remote.model

import android.annotation.SuppressLint
import android.os.Parcelable
import android.text.TextUtils
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class LoginRequest(
    @SerializedName("password")
    var password: String = "",
    @SerializedName("email")
    var username: String = ""
) : Parcelable {

    fun empty() = TextUtils.isEmpty(password) && TextUtils.isEmpty(username)
}