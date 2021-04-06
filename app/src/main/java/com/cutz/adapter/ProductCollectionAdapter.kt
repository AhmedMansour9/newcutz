package com.cutz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cutz.R
import com.cutz.data.remote.model.ProductsResponse
import com.cutz.databinding.RowInformationBinding
import com.cutz.databinding.RowProductBinding
import com.cutz.databinding.RowProductgridBinding
import kotlinx.android.synthetic.main.row_product.view.*

class ProductCollectionAdapter (list :List<ProductsResponse.Data>,var productData: ProductItemListener) :
    RecyclerView.Adapter<ProductCollectionAdapter.DeveloperViewHolder>() {

    var listInformation: List<ProductsResponse.Data>
    init {
        listInformation=list
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<RowProductBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.row_product, viewGroup, false
        )

        return DeveloperViewHolder(mTradersResponse)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentStudent = listInformation[i]
        mDeveloperViewHolder.mTradersResponse.model = currentStudent
        mDeveloperViewHolder.onBind(currentStudent)



    }

    override fun getItemCount(): Int {
        return listInformation.size
    }

    interface ProductItemListener {
        fun itemClicked(productData: ProductsResponse.Data?);
        fun itemFavourit(productData: ProductsResponse.Data?);

    }

    inner class DeveloperViewHolder(var mTradersResponse: RowProductBinding) :
        RecyclerView.ViewHolder(mTradersResponse.root){

        fun onBind(position: ProductsResponse.Data?) {

            mTradersResponse.model = position

            fun checkToken() {
                    if (position!!.isFavoirte==1) {
                        position.isFavoirte = 0
                        itemView.Img_Favourit.setImageResource(R.drawable.ic_emptyfavourit)
                    } else {
                        position.isFavoirte = 1
                        itemView.Img_Favourit.setImageResource(R.drawable.ic_favourit)
                    }

            }
            itemView.Img_Favourit.setOnClickListener { view ->
                run {
                    productData.itemFavourit(position)
                    checkToken()
                }
            }

            itemView.setOnClickListener { view ->
                run {
                    productData.itemClicked(position)
                }
            }
        }

    }


}