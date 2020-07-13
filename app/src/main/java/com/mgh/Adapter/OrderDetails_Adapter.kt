package com.mgh.Adapter

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mgh.R
import com.mgh.SharedPrefManager
import com.mgh.View.ProductDetails_View
import com.bumptech.glide.Glide
import com.mgh.Model.MyOrders_Response
import com.mgh.Model.OrderDetails_Response
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_deal.view.*

class OrderDetails_Adapter ( val userList: List<MyOrders_Response.Data.OrderDetail>)
    : RecyclerView.Adapter<OrderDetails_Adapter.ViewHolder>() {
    //this method is returning the view for each item in the list
    lateinit var ProductsDetails: ProductDetails_View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetails_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_orderdetails, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: OrderDetails_Adapter.ViewHolder, position: Int) {
        holder.bindItems(userList.get(position))

    }
    fun onClick(ProductsDetail: ProductDetails_View){
        this.ProductsDetails=ProductsDetail
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context =itemView.context

        fun bindItems(dataModel: MyOrders_Response.Data.OrderDetail) {
            itemView.Titl_Homee.text = dataModel.name
            itemView.T_SalePriceHome.text = dataModel.total.toString()+" "+
                    context.resources.getString(R.string.currency)
            Picasso.get()
                .load(dataModel.image)
                .resize(500, 500)
                .into(itemView.Img_ProductHome)

//            itemView.RatingBarHome.rating=dataModel.review!!.toFloat()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                itemView.T_SmallDescripHome.setText(
                    Html.fromHtml(
                        dataModel.description,
                        Html.FROM_HTML_MODE_LEGACY  )
                )
            } else {
                itemView.T_SmallDescripHome.text = dataModel.description
            }


        }

        }
    }
