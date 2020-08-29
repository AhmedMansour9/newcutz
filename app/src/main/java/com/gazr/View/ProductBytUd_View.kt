package com.gazr.View

import com.gazr.Model.Categories_Response


interface ProductBytUd_View {

    fun Id(categories: Categories_Response.Data.Data)
    fun Sub_Id(categories: Categories_Response.Data.Data.Subcategory)

}