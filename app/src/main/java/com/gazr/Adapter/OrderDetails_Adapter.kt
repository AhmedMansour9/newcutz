package com.gazr.Adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gazr.Activities.Add_Review
import com.gazr.R
import com.gazr.View.ProductDetails_View
import com.gazr.Model.MyOrders_Response
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_orderdetails.view.*
import kotlinx.android.synthetic.main.row_deal.view.*
import kotlinx.android.synthetic.main.row_deal.view.Img_ProductHome
import kotlinx.android.synthetic.main.row_deal.view.T_SalePriceHome
import kotlinx.android.synthetic.main.row_deal.view.T_SmallDescripHome
import kotlinx.android.synthetic.main.row_deal.view.Titl_Homee

class OrderDetails_Adapter (var context:Context, val userList: List<MyOrders_Response.Data.OrderDetail>)
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
        holder.itemView.Btn_AddReview.setOnClickListener(){
            var intent= Intent(context, Add_Review::class.java)
            intent.putExtra("id",userList.get(position).id.toString())
            context.startActivity(intent)
        }

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
            itemView.T_SmallDescripHome.text = dataModel.shortDescription



        }

        }
    }
