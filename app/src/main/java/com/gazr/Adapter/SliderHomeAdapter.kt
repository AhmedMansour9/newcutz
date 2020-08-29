package com.gazr.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.gazr.Model.SliderHome_Model
import com.gazr.R
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.row_slider.view.*


class SliderHomeAdapter(private val listSlider: List<SliderHome_Model.Slider_Home>,
                        private val context: Context) : SliderViewAdapter<SliderHomeAdapter.SliderAdapterVH>() {


    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.row_slider, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        viewHolder.bindItems(listSlider.get(position))
       }

    override fun getCount(): Int {
        //slider view count could be dynamic size
        return listSlider.size
    }

    inner class SliderAdapterVH(itemView: View) : ViewHolder(itemView) {
        fun bindItems(dataModel: SliderHome_Model.Slider_Home) {
            Glide.with(context)
                    .load(dataModel.image)
                    .into(itemView.image)
        }

    }

}


