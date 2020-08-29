package com.gazr.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gazr.Model.AllProducts_Response
import com.gazr.R
import com.gazr.View.onClickFilter_View

class Sizes_Adapter  (context: Context, val userList: List<AllProducts_Response.Data.Data.Review>)
    : RecyclerView.Adapter<Sizes_Adapter.ViewHolder>() {
    lateinit var productbyid: onClickFilter_View
    var row_index:Int = -2
    var context: Context =context
    var status:Boolean=false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Sizes_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_addetional, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: Sizes_Adapter.ViewHolder, position: Int) {
        holder.T_Titlee.text=userList.get(position).customer!!.fullName
        holder.T_Desc.text=userList.get(position).comment
      holder.RatingBar.rating=userList.get(position).review!!.toFloat()
        var str = userList.get(position).createdAt
        str = str?.replace("\\s.*".toRegex(), "")
        holder.T_Date.text=str


    }
    fun onClickProductSize(onClickProductColorView: onClickFilter_View) {
        this.productbyid = onClickProductColorView
    }
    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }


    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context = itemView.context
        var T_Titlee = itemView.findViewById(R.id.T_Titlee) as TextView
        var T_Desc = itemView.findViewById(R.id.T_Desc) as TextView
        val RatingBar = itemView.findViewById(R.id.RatingBar) as RatingBar
        var T_Date = itemView.findViewById(R.id.T_Date) as TextView


    }
}