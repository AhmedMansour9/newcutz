package com.cutz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cutz.R
import com.cutz.data.remote.model.CategoriesResponse
import com.cutz.data.remote.model.SectonsResponse
import com.cutz.databinding.ItemMenucategoryBinding
import com.cutz.databinding.RowHerocategoryBinding
import kotlinx.android.synthetic.main.row_herocategory.view.*

class CategoriesMenuAdapter (var categoryData: CategoryItemListener) :
    RecyclerView.Adapter<CategoriesMenuAdapter.DeveloperViewHolder>() {

    private var mListModel: MutableList<CategoriesResponse.Data>? = arrayListOf()

    var lastSelectedPosition = 0

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<ItemMenucategoryBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_menucategory, viewGroup, false
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

    fun setList(mDeveloperModel: MutableList<CategoriesResponse.Data>) {
        this.mListModel = mDeveloperModel
        notifyDataSetChanged()
    }

    inner class DeveloperViewHolder(var mListMo: ItemMenucategoryBinding) :
        RecyclerView.ViewHolder(mListMo.root) {

        fun onBind(position: Int) {

            itemView.setOnClickListener { view ->
                lastSelectedPosition = position
                run {
                    categoryData.itemClicked(mListModel?.get(position)!!)
                }
                notifyDataSetChanged();
            }
        }

    }

    interface CategoryItemListener {
        fun itemClicked(productData: CategoriesResponse.Data);

    }
}