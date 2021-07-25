package com.cutzegypt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cutzegypt.R
import com.cutzegypt.data.remote.model.SectonsResponse
import com.cutzegypt.databinding.RowHerocategoryBinding
import kotlinx.android.synthetic.main.row_herocategory.view.*

class CategoryAdapter(var context:Context,var categoryData: CategoryItemListener) :
    RecyclerView.Adapter<CategoryAdapter.DeveloperViewHolder>() {

    private var mListModel: MutableList<SectonsResponse.Data.Category>? = arrayListOf()

    var lastSelectedPosition = 0

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<RowHerocategoryBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.row_herocategory, viewGroup, false
        )

        return DeveloperViewHolder(mTradersResponse)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentStudent = mListModel?.get(i)
        mDeveloperViewHolder.mListMo.model = currentStudent
        mDeveloperViewHolder.onBind(i)
        if (lastSelectedPosition == 0 && i == 0) {
            categoryData.itemClicked(currentStudent!!)
            mDeveloperViewHolder.itemView.Vieww.visibility = View.VISIBLE
        }
    }


    override fun getItemCount(): Int {
        return if (mListModel != null) {
            mListModel!!.size
        } else {
            0
        }
    }

    fun setList(mDeveloperModel: MutableList<SectonsResponse.Data.Category>) {
        this.mListModel = mDeveloperModel
        notifyDataSetChanged()
    }

    inner class DeveloperViewHolder(var mListMo: RowHerocategoryBinding) :
        RecyclerView.ViewHolder(mListMo.root) {

        fun onBind(position: Int) {
            if (lastSelectedPosition == position) {
                itemView.Vieww.visibility = View.VISIBLE
                categoryData.itemClicked(mListModel?.get(position)!!)
                itemView.T_Name.setTextColor(ContextCompat.getColor(context, R.color.red))
            } else {
                itemView.Vieww.visibility = View.GONE
                itemView.T_Name.setTextColor(ContextCompat.getColor(context, android.R.color.white))
            }

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
        fun itemClicked(productData: SectonsResponse.Data.Category);

    }

}