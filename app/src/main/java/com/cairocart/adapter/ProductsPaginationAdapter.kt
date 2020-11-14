package com.cairocart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cairocart.data.remote.model.ProductsResponse
import com.cairocart.databinding.RowProductBinding

class ProductsPaginationAdapter (var productData: ProductItemListener) : PagingDataAdapter<ProductsResponse.Data,ProductsPaginationAdapter.DeveloperViewHolder>(DataDifferntiator) {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        return DeveloperViewHolder(RowProductBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup,false  )
        )
    }


    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, position: Int) {
        mDeveloperViewHolder.onBind(getItem(position))
    }

    inner class DeveloperViewHolder(var mTradersResponse: RowProductBinding) :
        RecyclerView.ViewHolder(mTradersResponse.root) {

        fun onBind(position: ProductsResponse.Data?) {
            mTradersResponse.model = position


            itemView.setOnClickListener { view ->
                run {
                    productData.itemClicked(position)
                }
            }
        }


    }

    interface ProductItemListener {
        fun itemClicked(productData: ProductsResponse.Data?);
    }

    object DataDifferntiator : DiffUtil.ItemCallback<ProductsResponse.Data>() {

        override fun areItemsTheSame(oldItem: ProductsResponse.Data, newItem: ProductsResponse.Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductsResponse.Data, newItem: ProductsResponse.Data): Boolean {
            return oldItem == newItem
        }
    }


}