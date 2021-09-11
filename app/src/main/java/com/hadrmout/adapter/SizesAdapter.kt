package com.hadrmout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hadrmout.R
import com.hadrmout.data.remote.model.ProductsResponse
import com.hadrmout.databinding.ItemSizeBinding

class SizesAdapter (var categoryData: ItemListener) : RecyclerView.Adapter<SizesAdapter.DeveloperViewHolder>() {

    private var mListModel: MutableList<ProductsResponse.Data.Addition>? = arrayListOf()

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

    fun setList(mDeveloperModel: MutableList<ProductsResponse.Data.Addition>) {
        this.mListModel = mDeveloperModel
        notifyDataSetChanged()
    }

    inner class DeveloperViewHolder(var mListMo: ItemSizeBinding) :
        RecyclerView.ViewHolder(mListMo.root){

        fun onBind(data: ProductsResponse.Data.Addition?, position: Int) {


            mListMo.ChecBox.setOnCheckedChangeListener { buttonView, isChecked ->
                run {
                    categoryData.itemClicked(data!!)
                }
            }


        }

    }

    interface ItemListener {
        fun itemClicked(productData: ProductsResponse.Data.Addition);

    }

}