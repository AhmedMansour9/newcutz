package com.hadrmout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.hadrmout.R
import com.hadrmout.databinding.RowSliderproductBinding
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.row_sliderproduct.view.*

class SliderProductAdapter(var mList: MutableList<String>?) :
    SliderViewAdapter<SliderProductAdapter.DeveloperViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<RowSliderproductBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.row_sliderproduct, viewGroup, false
        )

        return DeveloperViewHolder(mTradersResponse)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentStudent = mList!![i]
        Glide.with(mDeveloperViewHolder.itemView.context)
            .load(currentStudent)
            .placeholder(R.drawable.img_fake)
            .into(mDeveloperViewHolder.itemView.image)
    }


    override fun getCount(): Int {
        return if (mList != null) {
            mList!!.size
        } else {
            0
        }
    }


    inner class DeveloperViewHolder(var mTradersResponse: RowSliderproductBinding) :
        SliderViewAdapter.ViewHolder(mTradersResponse.root)




}