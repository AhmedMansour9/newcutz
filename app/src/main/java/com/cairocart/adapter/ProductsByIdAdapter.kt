package com.cairocart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cairocart.data.remote.model.ProductsByIdResponse
import com.cairocart.databinding.RowProductBinding

class ProductsByIdAdapter (): RecyclerView.Adapter<ProductsByIdAdapter.DeveloperViewHolder>() {

    private var mProductsModel: MutableList<ProductsByIdResponse.Data>? = arrayListOf()


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        return DeveloperViewHolder(RowProductBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, position: Int) {
        mDeveloperViewHolder.onBind(position)
    }

    override fun getItemCount(): Int {
        return if (mProductsModel != null) {
            mProductsModel!!.size
        } else {
            0
        }
    }

    fun setDeveloperList(mDeveloperModel: MutableList<ProductsByIdResponse.Data>) {
        this.mProductsModel = mDeveloperModel
        notifyDataSetChanged()
    }

    inner class DeveloperViewHolder(var mTradersResponse: RowProductBinding) :
        RecyclerView.ViewHolder(mTradersResponse.root) {


        fun onBind(position: Int) {
            val currentStudent = mProductsModel!![position]
            mTradersResponse.model = currentStudent


        }


    }


}