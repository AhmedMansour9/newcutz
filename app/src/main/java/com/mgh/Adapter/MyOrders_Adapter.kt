package com.mgh.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mgh.Model.MyOrders_Response
import com.mgh.R
import com.mgh.View.DetailsMyOrdersView
import com.mgh.View.PlusId_View
import kotlinx.android.synthetic.main.item_myorder.view.*

class MyOrders_Adapter  ( val userList: List<MyOrders_Response.Data>)
    : RecyclerView.Adapter<MyOrders_Adapter.ViewHolder>() {
    lateinit var orderDetai: DetailsMyOrdersView

    companion object
    {
        lateinit var plusId_View: PlusId_View
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrders_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_myorder, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: MyOrders_Adapter.ViewHolder, position: Int) {
        holder.bindItems(userList.get(position))
        holder.itemView.setOnClickListener(){
            this.orderDetai.showDetailsMyOrders(userList.get(position))
        }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }
    fun OnClick(orderDetais: DetailsMyOrdersView) {
        orderDetai=orderDetais
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context =itemView.context

        fun bindItems(dataModel: MyOrders_Response.Data) {

            itemView.T_order_id.text= dataModel.id.toString()
//            val orderStatusValue = dataModel.sta

                itemView.T_date.text=dataModel.createdAt
            val pi = dataModel.total!!.toDouble()
            val s = "%.2f".format(pi)


//            itemView.T_date.text=dataModel.da
            itemView.T_price.text=s + dataModel.orderDetails!!.get(0)!!.currency


        }
    }
}
