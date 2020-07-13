package com.mgh.Model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Register_Model(
    @SerializedName("access_token")
    var accessToken: String?,
    @SerializedName("expires_at")
    var expiresAt: String?,
    @SerializedName("token_type")
    var tokenType: String?,
    @SerializedName("userType")
    var userType: String?
) : Parcelable
