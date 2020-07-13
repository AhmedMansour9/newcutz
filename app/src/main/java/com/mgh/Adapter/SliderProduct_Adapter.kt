package com.mgh.Adapter

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.mgh.Model.AllProducts_Response
import com.mgh.R
import com.squareup.picasso.Picasso
import java.util.ArrayList

class SliderProduct_Adapter(private val context: Context,var imageModelArrayList:  List<AllProducts_Response.Data.Data.ProductImage>) : PagerAdapter() {
    private val inflater: LayoutInflater


    init {
        inflater = LayoutInflater.from(context)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return imageModelArrayList.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout = inflater.inflate(R.layout.row_sliderproduct, view, false)!!

        val imageView = imageLayout
            .findViewById(R.id.image) as ImageView

        Picasso.get()
            .load(
                imageModelArrayList.get(position).image)
            .placeholder(R.drawable.place_holder)
            .into(imageView)

        view.addView(imageLayout, 0)

        return imageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    override fun saveState(): Parcelable? {
        return null
    }

}
