package com.cutzegypt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cutzegypt.R
import com.cutzegypt.data.remote.model.ProductsResponse
import com.cutzegypt.databinding.RowProductBinding
import com.cutzegypt.databinding.RowProductgridBinding
import kotlinx.android.synthetic.main.row_product.view.*

class ProductFavouritAdapter (context: Context, var productData: ProductItemListener) :
    PagingDataAdapter<ProductsResponse.Data, RecyclerView.ViewHolder>(
        DataDifferntiator
    ) {
    var token: String? = String()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var type: Int = 0
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var context: Context
    init {
        this.context=context
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

            fun checkToken() {
                if (!token.isNullOrEmpty()) {
                    if (position!!.isFavoirte==1) {
                        position.isFavoirte = 0
                        itemView.Img_Favourit.setImageResource(R.drawable.ic_emptyfavourit)
                    } else {
                        position.isFavoirte = 1
                        itemView.Img_Favourit.setImageResource(R.drawable.ic_favourit)
                    }
                }
            }
            itemView.Img_Favourit.setOnClickListener { view ->
                run {
                    productData.itemClicked(position)
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


    inner class ProductsLinearViewHolder(var mTradersResponse: RowProductBinding) :
        RecyclerView.ViewHolder(mTradersResponse.root) {

        fun onBind(position: ProductsResponse.Data?) {
            mTradersResponse.model = position

            if (position!!.stock > 0) {
                itemView.img_true.setImageResource(R.drawable.img_true)
                itemView.Stock.text = context.resources.getString(R.string.in_stock)
            } else {
                itemView.img_true.setImageResource(R.drawable.img_false)
                itemView.Stock.text = context.resources.getString(R.string.out_stock)
            }

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


    interface ProductItemListener {
        fun itemClicked(productData: ProductsResponse.Data?);
        fun itemFavourit(productData: ProductsResponse.Data?);

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