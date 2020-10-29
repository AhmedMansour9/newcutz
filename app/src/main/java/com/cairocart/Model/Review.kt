package com.cairocart.Model

import android.os.Parcelable
import com.cairocart.Model.Customer
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Review(
    var comment: String?,
    var created_at: String?,
    var customer: Customer?,
    var review: Int?
   ):Parcelable