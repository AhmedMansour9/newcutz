package com.cairocart.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cairocart.Model.Gifts_Response
import com.cairocart.R
import com.cairocart.View.DetailsGifts_View
import com.cairocart.View.PlusId_View

class Gifts_Adapter  ( val userList: List<Gifts_Response.Data>)
    : RecyclerView.Adapter<Gifts_Adapter.ViewHolder>() {
    lateinit var orderDetai: DetailsGifts_View

    companion object
    {
        lateinit var plusId_View: PlusId_View
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Gifts_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_gift, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: Gifts_Adapter.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener(){
            this.orderDetai.details(userList.get(position))
        }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }
    fun OnClick(orderDetais: DetailsGifts_View) {
        orderDetai=orderDetais
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context =itemView.context


    }
}
