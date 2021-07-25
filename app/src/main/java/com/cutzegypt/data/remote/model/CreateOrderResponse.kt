package com.cutzegypt.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class CreateOrderResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("error")
    var error: String?,
    @SerializedName("status")
    var status: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("created_at")
        var createdAt: CreatedAt?,

        @SerializedName("customer_email")
        var customerEmail: String?,
        @SerializedName("customer_floor_number")
        var customerFloorNumber: String?,
        @SerializedName("customer_home_number")
        var customerHomeNumber: String?,
        @SerializedName("customer_name")
        var customerName: String?,
        @SerializedName("customer_phone")
        var customerPhone: String?,

        @SerializedName("customer_region")
        var customerRegion: String?,
        @SerializedName("customer_street")
        var customerStreet: String?,
        @SerializedName("delivery_fees")
        var deliveryFees: Int?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("langtude")
        var langtude: Int?,
        @SerializedName("lattude")
        var lattude: Int?,
        @SerializedName("order_detials")
        var orderDetials: List<OrderDetial?>?,
        @SerializedName("payment_method")
        var paymentMethod: String?,
        @SerializedName("payment_status")
        var paymentStatus: String?,
        @SerializedName("promocode")
        var promocode: String?,
        @SerializedName("status")
        var status: String?,
        @SerializedName("taxes")
        var taxes: Int?,
        @SerializedName("total")
        var total: Int?
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class CreatedAt(
            @SerializedName("date")
            var date: String?,
            @SerializedName("timezone")
            var timezone: String?,
            @SerializedName("timezone_type")
            var timezoneType: Int?
        ) : Parcelable

        @SuppressLint("ParcelCreator")
        @Parcelize
        data class OrderDetial(
            @SerializedName("id")
            var id: Int?,
            @SerializedName("price")
            var price: Int?,
            @SerializedName("product")
            var product: Product?,
            @SerializedName("qty")
            var qty: Int?
        ) : Parcelable {
            @SuppressLint("ParcelCreator")
            @Parcelize
            data class Product(
                @SerializedName("addtions")
                var addtions: List<Addtion?>?,
                @SerializedName("AvgRate")
                var avgRate: Int?,
                @SerializedName("BBQ")
                var bBQ: String?,
                @SerializedName("country")
                var country: String?,
                @SerializedName("country_flag")
                var countryFlag: String?,
                @SerializedName("currency")
                var currency: String?,
                @SerializedName("description")
                var description: String?,
                @SerializedName("discount")
                var discount: Int?,
                @SerializedName("EliteProduct")
                var eliteProduct: String?,
                @SerializedName("FinalPrice")
                var finalPrice: Int?,
                @SerializedName("id")
                var id: Int?,
                @SerializedName("image")
                var image: String?,
                @SerializedName("images")
                var images: List<Image?>?,
                @SerializedName("instructions")
                var instructions: List<Instruction?>?,
                @SerializedName("IsFavoirte")
                var isFavoirte: Int?,
                @SerializedName("MainPrice")
                var mainPrice: Int?,
                @SerializedName("measr_unit")
                var measrUnit: String?,
                @SerializedName("stock")
                var stock: Int?,
                @SerializedName("subCategory_id")
                var subCategoryId: Int?,
                @SerializedName("title")
                var title: String?
            ) : Parcelable {
                @SuppressLint("ParcelCreator")
                @Parcelize
                data class Addtion(
                    @SerializedName("currency")
                    var currency: String?,
                    @SerializedName("id")
                    var id: Int?,
                    @SerializedName("price")
                    var price: Int?,
                    @SerializedName("title")
                    var title: String?
                ) : Parcelable

                @SuppressLint("ParcelCreator")
                @Parcelize
                data class Image(
                    @SerializedName("id")
                    var id: Int?,
                    @SerializedName("image")
                    var image: String?,
                    @SerializedName("product_id")
                    var productId: Int?
                ) : Parcelable

                @SuppressLint("ParcelCreator")
                @Parcelize
                data class Instruction(
                    @SerializedName("description")
                    var description: String?,
                    @SerializedName("id")
                    var id: Int?,
                    @SerializedName("image")
                    var image: String?,
                    @SerializedName("title")
                    var title: String?
                ) : Parcelable

                @SuppressLint("ParcelCreator")
                @Parcelize
                data class Rate(
                    @SerializedName("customer")
                    var customer: Customer?,
                    @SerializedName("feedback")
                    var feedback: String?,
                    @SerializedName("id")
                    var id: Int?,
                    @SerializedName("rate")
                    var rate: Int?
                ) : Parcelable {
                    @SuppressLint("ParcelCreator")
                    @Parcelize
                    data class Customer(
                        @SerializedName("created_at")
                        var createdAt: CreatedAt?,
                        @SerializedName("email")
                        var email: String?,
                        @SerializedName("gender")
                        var gender: String?,
                        @SerializedName("id")
                        var id: Int?,
                        @SerializedName("image")
                        var image: String?,
                        @SerializedName("name")
                        var name: String?,
                        @SerializedName("phone")
                        var phone: String?
                    ) : Parcelable {
                        @SuppressLint("ParcelCreator")
                        @Parcelize
                        data class CreatedAt(
                            @SerializedName("date")
                            var date: String?,
                            @SerializedName("timezone")
                            var timezone: String?,
                            @SerializedName("timezone_type")
                            var timezoneType: Int?
                        ) : Parcelable
                    }
                }
            }
        }
    }
}