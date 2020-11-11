package com.cairocart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cairocart.data.remote.model.ProductsByIdResponse
import com.cairocart.databinding.RowProductBinding
import com.cairocart.databinding.RowProductgridBinding

class ProductsGridByIdAdapter : RecyclerView.Adapter<ProductsGridByIdAdapter.DeveloperViewHolder>() {

    private var mProductsModel = ArrayList<ProductsByIdResponse.Data>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        return DeveloperViewHolder(
            RowProductgridBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, position: Int) {
        mDeveloperViewHolder.onBind(position)

    }

    override fun getItemCount(): Int {
        return mProductsModel.size
    }

    fun setDeveloperList(mDeveloperModel: MutableList<ProductsByIdResponse.Data>) {
        this.mProductsModel.addAll(mDeveloperModel)
        this.notifyDataSetChanged()
    }

    inner class DeveloperViewHolder(var mTradersResponse: RowProductgridBinding) :
        RecyclerView.ViewHolder(mTradersResponse.root) {
        fun onBind(position: Int) {
            val currentStudent = mProductsModel[position]
            mTradersResponse.model = currentStudent

        }
    }
}