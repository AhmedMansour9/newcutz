package com.cutzegypt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cutzegypt.R
import com.cutzegypt.data.remote.model.ProductsResponse
import com.cutzegypt.databinding.ItemSizeBinding
import kotlinx.android.synthetic.main.item_size.view.*

class SizesAdapter (var categoryData: ItemListener) : RecyclerView.Adapter<SizesAdapter.DeveloperViewHolder>() {

    private var mListModel: MutableList<ProductsResponse.Data.Weights>? = arrayListOf()

    var lastSelectedPosition = -1


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<ItemSizeBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_size, viewGroup, false
        )

        return DeveloperViewHolder(mTradersResponse)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentStudent = mListModel?.get(i)
        mDeveloperViewHolder.mListMo.model = currentStudent
        mDeveloperViewHolder.onBind(currentStudent,i)


    }


    override fun getItemCount(): Int {
        return if (mListModel != null) {
            mListModel!!.size
        } else {
            0
        }
    }

    fun setList(mDeveloperModel: MutableList<ProductsResponse.Data.Weights>) {
        this.mListModel = mDeveloperModel
        notifyDataSetChanged()
    }

    inner class DeveloperViewHolder(var mListMo: ItemSizeBinding) :
        RecyclerView.ViewHolder(mListMo.root){

        fun onBind(data:ProductsResponse.Data.Weights?,position: Int) {

            if (lastSelectedPosition == position) {
                itemView.Rela_Size.visibility = View.VISIBLE
                itemView.Rela_Size.setBackgroundResource(R.drawable.bc_selectedsize)
            } else {
                itemView.Rela_Size.visibility = View.VISIBLE
                itemView.Rela_Size.setBackgroundResource(R.drawable.bc_size)
            }
            itemView.setOnClickListener() {
                lastSelectedPosition = position
                run {
                    categoryData.itemClicked(data!!)
                }
                notifyDataSetChanged();
            }


        }

    }

    interface ItemListener {
        fun itemClicked(productData: ProductsResponse.Data.Weights);

    }

}