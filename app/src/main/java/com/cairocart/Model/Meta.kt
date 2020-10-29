package com.cairocart.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Meta(
    var current_page: Int,
    var hasMorePages: Boolean,
    var last_page: Int,
    var per_page: Int,
    var total: Int
): Parcelable