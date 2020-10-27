package com.cairocart.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cairocart.Fragments.Product_Details
import com.cairocart.Model.ExtraAdditonal_Response
import com.cairocart.R
import com.cairocart.View.ChechBox_View
import com.cairocart.View.OnClickProductColorView
import com.cairocart.View.onClickFilter_View
import com.chicchicken.Model.Checkbox_Id
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_additonal.view.*
import java.util.ArrayList
import kotlin.jvm.functions.*


class AddetionalCart_Adapter (context: Context, val userList: List<ExtraAdditonal_Response.Data>)
    : RecyclerView.Adapter<AddetionalCart_Adapter.ViewHolder>() {
    lateinit var productbyid: onClickFilter_View
    lateinit var checkbox: ChechBox_View

    var row_index:Int = -1
    var context: Context =context
    companion object {
        var IdList: MutableList<Checkbox_Id> = ArrayList()
    }
    lateinit var  onClickProductSizesView: OnClickProductColorView
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): AddetionalCart_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_addetionalcart, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: AddetionalCart_Adapter.ViewHolder, position: Int) {
        holder.itemView.T_Title.text=userList.get(position).title
        holder.itemView.T_Pricee.text=userList.get(position).priceGeneral+" "+ Product_Details.Currency

        Picasso.get()
                .load("http://chic-chicken.co.il" + userList.get(position).image)
                .resize(500, 500)
                .into(holder.itemView.Img_cat)



    }
    fun onClickCheck(onClickProductColorView: ChechBox_View) {
        this.checkbox = onClickProductColorView
    }

    fun onClickProductSize(onClickProductColorView: OnClickProductColorView) {
        this.onClickProductSizesView = onClickProductColorView
    }
    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }


    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        private val context: Context = itemView.context

    }



}