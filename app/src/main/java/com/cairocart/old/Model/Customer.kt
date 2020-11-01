package com.cairocart.old.Model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Customer(
         var created_at: String?,
         var email: String?,
         var firebaseToken: String?,
         var full_name: String?,
         var gender: String?,
         var image: String?,
         var phone: String?,
         var status: String?
      ) : Parcelable