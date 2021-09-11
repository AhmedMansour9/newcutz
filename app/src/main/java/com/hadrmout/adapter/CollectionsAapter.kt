package com.hadrmout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hadrmout.R
import com.hadrmout.data.remote.model.ProductsResponse
import com.hadrmout.data.remote.model.SectonsResponse
import com.hadrmout.databinding.ItemCollectionBinding

class CollectionsAapter (var categoryData: CategoryItemListener) : RecyclerView.Adapter<CollectionsAapter.DeveloperViewHolder>() {

    private var mListModel: MutableList<ProductsResponse.Data>? = arrayListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<ItemCollectionBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_collection, viewGroup, false
        )

        return DeveloperViewHolder(mTradersResponse)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentStudent = mListModel?.get(i)
        mDeveloperViewHolder.mListMo.model = currentStudent
        mDeveloperViewHolder.onBind(i)



    }


    override fun getItemCount(): Int {
        return if (mListModel != null) {
            mListModel!!.size
        } else {
            0
        }
    }

    fun setList(mDeveloperModel: MutableList<ProductsResponse.Data>) {
        this.mListModel = mDeveloperModel
        notifyDataSetChanged()
    }

    inner class DeveloperViewHolder(var mListMo: ItemCollectionBinding) :
        RecyclerView.ViewHolder(mListMo.root){

        fun onBind(position: Int) {

            itemView.setOnClickListener { view ->
                run {
                    categoryData.itemClicked(mListModel?.get(position)!!)

                }
            }
        }

    }

    interface CategoryItemListener {
        fun itemClicked(productData: ProductsResponse.Data);

    }

}