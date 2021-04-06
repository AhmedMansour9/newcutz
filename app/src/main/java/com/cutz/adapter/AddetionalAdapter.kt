package com.cutz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cutz.R
import com.cutz.data.remote.model.ProductsResponse
import com.cutz.databinding.ItemAddetionalBinding
import kotlinx.android.synthetic.main.item_addetional.view.*
import kotlinx.android.synthetic.main.row_categoryfilter.view.*

class AddetionalAdapter(var additonal: AdditonaltemListener) :
    RecyclerView.Adapter<AddetionalAdapter.DeveloperViewHolder>() {

    private var mListModel: MutableList<ProductsResponse.Data.Addtion>? = arrayListOf()

    var lastSelectedPosition = -1


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<ItemAddetionalBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_addetional, viewGroup, false
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

    fun setList(mDeveloperModel: MutableList<ProductsResponse.Data.Addtion>) {
        this.mListModel = mDeveloperModel
        notifyDataSetChanged()
    }

    inner class DeveloperViewHolder(var mListMo: ItemAddetionalBinding) :
        RecyclerView.ViewHolder(mListMo.root) {

        fun onBind(position: Int) {


            itemView.ChecBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    run {
                        additonal.itemChecked(mListModel?.get(position)!!)
                    }

                }else {
                    run {
                        additonal.itemunChecked(mListModel?.get(position)!!)
                    }
                }
            }


        }

    }

    interface AdditonaltemListener {
        fun itemChecked(productData: ProductsResponse.Data.Addtion);
        fun itemunChecked(productData: ProductsResponse.Data.Addtion);

    }

}