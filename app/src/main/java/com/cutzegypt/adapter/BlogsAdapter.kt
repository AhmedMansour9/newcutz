package com.cutzegypt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cutzegypt.data.remote.model.BlogsResponse
import com.cutzegypt.databinding.ItemBlogBinding

class BlogsAdapter (context: Context, var productData: ProductItemListener) :
    PagingDataAdapter<BlogsResponse.Data, RecyclerView.ViewHolder>(
        DataDifferntiator
    ) {

    var context: Context
    init {
        this.context=context
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): RecyclerView.ViewHolder {
            return ProductsLinearViewHolder(
                ItemBlogBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            )

    }




    inner class ProductsLinearViewHolder(var mTradersResponse: ItemBlogBinding) :
        RecyclerView.ViewHolder(mTradersResponse.root) {

        fun onBind(position: BlogsResponse.Data?) {
            mTradersResponse.model = position
            itemView.setOnClickListener(){
                productData.itemClicked(position)
            }


        }


    }


    interface ProductItemListener {
        fun itemClicked(productData: BlogsResponse.Data?);

    }

    object DataDifferntiator : DiffUtil.ItemCallback<BlogsResponse.Data>() {

        override fun areItemsTheSame(
            oldItem: BlogsResponse.Data,
            newItem: BlogsResponse.Data
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: BlogsResponse.Data,
            newItem: BlogsResponse.Data
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as ProductsLinearViewHolder).onBind(getItem(position))

    }

}