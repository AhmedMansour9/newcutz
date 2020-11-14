package com.cairocart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cairocart.data.remote.model.ProductsResponse
import com.cairocart.databinding.RowProductBinding
import com.cairocart.databinding.RowProductgridBinding

class ProductsGridByIdAdapter(var productData: ProductItemListener) :
    PagingDataAdapter<ProductsResponse.Data, RecyclerView.ViewHolder>(
        DataDifferntiator
    ) {

    var type: Int = 0
    set(value) {
        field=value
        notifyDataSetChanged()
    }



    override fun getItemViewType(position: Int): Int {
        if (type == 0) return 0 else return 1
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): RecyclerView.ViewHolder {
        if (type == 0)
            return ProductsLinearViewHolder(
                RowProductBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            )
        else
            return ProductsGridViewHolder(
                RowProductgridBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
            )


    }

    inner class ProductsGridViewHolder(var mTradersResponse: RowProductgridBinding) :
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


    inner class ProductsLinearViewHolder(var mTradersResponse: RowProductBinding) :
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

        override fun areItemsTheSame(
            oldItem: ProductsResponse.Data,
            newItem: ProductsResponse.Data
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ProductsResponse.Data,
            newItem: ProductsResponse.Data
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == 0) {
            (holder as ProductsLinearViewHolder).onBind(getItem(position))
        } else {
            (holder as ProductsGridViewHolder).onBind(getItem(position))

        }
    }


}