package com.hadrmout.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hadrmout.R
import com.hadrmout.data.remote.model.SectonsResponse
import com.hadrmout.databinding.RowHerocategoryBinding
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
            mDeveloperViewHolder.itemView.T_Name.setTextColor(Color.BLACK)
            mDeveloperViewHolder.itemView.T_Name.setBackgroundResource(R.drawable.bc_yellow)
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
                itemView.T_Name.setBackgroundResource(R.drawable.bc_yellow)
                itemView.T_Name.setTextColor(Color.BLACK)
                categoryData.itemClicked(mListModel?.get(position)!!)
            } else {
                itemView.T_Name.setBackgroundColor(Color.BLACK)
                itemView.T_Name.setTextColor(Color.WHITE)

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