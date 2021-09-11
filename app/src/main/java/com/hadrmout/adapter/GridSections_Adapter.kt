package com.hadrmout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.hadrmout.R
import com.hadrmout.data.remote.model.SectonsResponse
import com.hadrmout.databinding.ItemSectionBinding

class GridSections_Adapter (private val onSelectItem:(SectonsResponse.Data)->Unit) : RecyclerView.Adapter<GridSections_Adapter.DeveloperViewHolder>() {

    private var mListModel: MutableList<SectonsResponse.Data>? = arrayListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<ItemSectionBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_section, viewGroup, false
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

    fun setList(mDeveloperModel: MutableList<SectonsResponse.Data>) {
        this.mListModel = mDeveloperModel
        notifyDataSetChanged()
    }

    inner class DeveloperViewHolder(var mListMo: ItemSectionBinding) :
        RecyclerView.ViewHolder(mListMo.root){

        fun onBind(position: Int) {

            val lp = itemView.layoutParams
            if (lp is FlexboxLayoutManager.LayoutParams){
                val flexboxLp = itemView.layoutParams as FlexboxLayoutManager.LayoutParams
                flexboxLp.flexGrow = 1.0f
            }
            itemView.setOnClickListener {
                onSelectItem(mListModel?.get(position)!!)
            }

        }

    }



}