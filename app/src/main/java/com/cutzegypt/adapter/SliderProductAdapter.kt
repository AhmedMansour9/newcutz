package com.cutzegypt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.cutzegypt.R
import com.cutzegypt.data.remote.model.ProductsResponse
import com.cutzegypt.databinding.RowSliderproductBinding
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderProductAdapter(var mList: MutableList<ProductsResponse.Data.Image>?)
    : SliderViewAdapter<SliderProductAdapter.DeveloperViewHolder>()  {


    override fun onCreateViewHolder(viewGroup: ViewGroup): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<RowSliderproductBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.row_sliderproduct, viewGroup, false
        )

        return DeveloperViewHolder(mTradersResponse)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentStudent = mList!![i]
        mDeveloperViewHolder.mTradersResponse.model = currentStudent
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