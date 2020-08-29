package com.gazr.Adapter

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gazr.Model.AllProducts_Response
import com.gazr.R
import com.gazr.View.PlusId_View
import kotlinx.android.synthetic.main.row_cart.view.*
import kotlinx.android.synthetic.main.row_cart.view.T_Price
import kotlinx.android.synthetic.main.row_cart.view.T_SmallDescrip
import kotlinx.android.synthetic.main.row_cart.view.T_title
import kotlinx.android.synthetic.main.row_cart.view.img_cart
import kotlinx.android.synthetic.main.row_cartorder.view.*

class CartOrder (context: Context, val userList: List<AllProducts_Response.Data.Data>)
    : RecyclerView.Adapter<CartOrder.ViewHolder>() {
    companion object
    {
        lateinit var plusId_View: PlusId_View
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartOrder.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_cartorder, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: CartOrder.ViewHolder, position: Int) {
        holder.bindItems(userList.get(position))

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }
    fun OnClickPlus(plusId_Vie: PlusId_View) {

        plusId_View=plusId_Vie
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context =itemView.context

        fun bindItems(dataModel: AllProducts_Response.Data.Data) {

            itemView.T_title.text = dataModel.name
//            itemView.T_Counter.text= dataModel.productInCartQty.toString()

            itemView.T_Price.text=dataModel.productInCartTotal.toString()+" "+dataModel.currency
            itemView.T_Qty.text= dataModel.productInCartQty.toString()
            itemView.T_SmallDescrip.setText( dataModel.shortDescription )

            Glide.with(context)
                .load( dataModel.image).into(itemView.img_cart)

        }
    }
}
