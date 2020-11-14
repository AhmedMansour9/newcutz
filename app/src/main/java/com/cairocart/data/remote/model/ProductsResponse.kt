package com.cairocart.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class ProductsResponse(
    @SerializedName("data")
    var `data`: List<Data>,
    @SerializedName("status")
    var status: Status?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("attribute_set_id")
        var attributeSetId: Int?,
        @SerializedName("cart_max_allowed")
        var cartMaxAllowed: Int?,
        @SerializedName("cart_min_allowed")
        var cartMinAllowed: Int?,
        @SerializedName("created_at")
        var createdAt: String?,
        @SerializedName("custom_attributes")
        var customAttributes: List<CustomAttribute?>?,
        @SerializedName("description")
        var description: String?,
        @SerializedName("has_cross_sell_products")
        var hasCrossSellProducts: Boolean?,
        @SerializedName("has_related_products")
        var hasRelatedProducts: Boolean?,
        @SerializedName("has_reviews")
        var hasReviews: Boolean?,
        @SerializedName("has_up_sell_products")
        var hasUpSellProducts: Boolean?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("image_url")
        var imageUrl: String?,
        @SerializedName("is_in_wishlist")
        var isInWishlist: Boolean?,
        @SerializedName("is_salable")
        var isSalable: Boolean?,
        @SerializedName("media_gallery_entries")
        var mediaGalleryEntries: List<MediaGalleryEntry?>?,
        @SerializedName("name")
        var name: String?,
        @SerializedName("price")
        var price: String?,
        @SerializedName("qty")
        var qty: Int?,
        @SerializedName("sku")
        var sku: String?,
        @SerializedName("status")
        var status: Int?,
        @SerializedName("type_id")
        var typeId: String?,
        @SerializedName("updated_at")
        var updatedAt: String?
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class CustomAttribute(
            @SerializedName("attribute_code")
            var attributeCode: String?,
        ) : Parcelable

        @SuppressLint("ParcelCreator")
        @Parcelize
        data class MediaGalleryEntry(
            @SerializedName("disabled")
            var disabled: Boolean?,
            @SerializedName("file")
            var `file`: String?,
            @SerializedName("id")
            var id: Int?,
            @SerializedName("media_type")
            var mediaType: String?,
            @SerializedName("position")
            var position: Int?,
            @SerializedName("types")
            var types: List<String?>?,
            @SerializedName("url")
            var url: String?
        ) : Parcelable
    }


    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Status(
        @SerializedName("code")
        var code: Int?,
        @SerializedName("message")
        var message: String?
    ) : Parcelable
}