package com.cairocart.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cairocart.Activities.ReplyNotification
import com.cairocart.Model.AllNotifications_Response
import com.cairocart.R
import com.cairocart.View.DetailsMyOrdersView
import com.cairocart.View.PlusId_View
import kotlinx.android.synthetic.main.item_notification.view.*

class AllNotification_Adpter (val context:Context, val userList: List<AllNotifications_Response.Data>)
    : RecyclerView.Adapter<AllNotification_Adpter.ViewHolder>() {
    lateinit var orderDetai: DetailsMyOrdersView

    companion object
    {
        lateinit var plusId_View: PlusId_View
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllNotification_Adpter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: AllNotification_Adpter.ViewHolder, position: Int) {
        holder.bindItems(userList.get(position))
        holder.itemView.setOnClickListener(){
            if(userList.get(position).type.equals("message")){
                var intent= Intent(context, ReplyNotification::class.java)
                intent.putExtra("id",userList.get(position).id.toString())
                context.startActivity(intent)
            }

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

        fun bindItems(dataModel: AllNotifications_Response.Data) {

            itemView.Title.text= dataModel.name.toString()
            itemView.Descrption.text= dataModel.description.toString()
            itemView.T_date.text=dataModel.date





        }
    }
}