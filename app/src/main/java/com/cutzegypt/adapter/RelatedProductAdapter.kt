package com.cutzegypt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cutzegypt.R
import com.cutzegypt.data.remote.model.ProductsResponse
import com.cutzegypt.databinding.RowHeroProductBinding

class RelatedProductAdapter ( var productData: ProductItemListener) :
    RecyclerView.Adapter<RelatedProductAdapter.DeveloperViewHolder>() {

    private var mList: MutableList<ProductsResponse.Data>? = arrayListOf()


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<RowHeroProductBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.row_hero_product, viewGroup, false
        )

        return DeveloperViewHolder(mTradersResponse)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentStudent = mList!![i]
        mDeveloperViewHolder.mTradersResponse.model = currentStudent
        mDeveloperViewHolder.onBind(currentStudent)



    }

    override fun getItemCount(): Int {
        return if (mList != null) {
            mList!!.size
        } else {
            0
        }
    }

    fun setDeveloperList(mDeveloperModel: MutableList<ProductsResponse.Data>) {
        this.mList = mDeveloperModel
        notifyDataSetChanged()
    }

    interface ProductItemListener {
        fun itemClicked(productData: ProductsResponse.Data?);

    }

    inner class DeveloperViewHolder(var mTradersResponse: RowHeroProductBinding) :
        RecyclerView.ViewHolder(mTradersResponse.root){

        fun onBind(position: ProductsResponse.Data?) {

            mTradersResponse.model = position



            itemView.setOnClickListener { view ->
                run {
                    productData.itemClicked(position)
                }
            }
        }

    }

}