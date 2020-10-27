package com.cairocart.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.cairocart.Model.SliderHome_Model
import com.cairocart.R
import com.cairocart.View.ProductBytUd_View
import com.squareup.picasso.Picasso

class Banners_Adapter (context: Context, val userList: List<SliderHome_Model.Slider_Home>)
    : RecyclerView.Adapter<Banners_Adapter.ViewHolder>() {
    lateinit var productbyid: ProductBytUd_View
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Banners_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_banner, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: Banners_Adapter.ViewHolder, position: Int) {
        holder.bindItems(userList.get(position))

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }
    fun onClick(productI: ProductBytUd_View){
        this.productbyid=productI
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context = itemView.context

        fun bindItems(dataModel: SliderHome_Model.Slider_Home) {
            val img = itemView.findViewById(R.id.Img_Banner) as ImageView

            Picasso.get()
                .load(dataModel.image)
                .placeholder(R.drawable.place_holder)
                .fit()
                .into(img)
        }
    }
}