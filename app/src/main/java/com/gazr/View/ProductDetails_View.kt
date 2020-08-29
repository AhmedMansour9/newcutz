package com.gazr.View

import com.gazr.Model.AllProducts_Response


interface ProductDetails_View {

    fun Details(detailsProduct: AllProducts_Response.Data.Data)

    fun DetailsProducts(detailsProduct: AllProducts_Response.Data.Data)

}