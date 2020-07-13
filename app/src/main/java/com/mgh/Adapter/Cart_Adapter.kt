package com.mgh.Adapter

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.mgh.Model.Cart_Response
import com.mgh.R
import com.bumptech.glide.Glide
import com.mgh.Model.AllProducts_Response
import com.mgh.SharedPrefManager
import com.mgh.View.PlusId_View
import kotlinx.android.synthetic.main.row_cart.view.*

class Cart_Adapter(context: Context, val userList: List<AllProducts_Response.Data.Data>)
    : RecyclerView.Adapter<Cart_Adapter.ViewHolder>() {
    companion object
    {
        lateinit var plusId_View: PlusId_View
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Cart_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_cart, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: Cart_Adapter.ViewHolder, position: Int) {
        holder.bindItems(userList.get(position))
        holder.itemView.img_delete.setOnClickListener(){
            plusId_View.delete(userList.get(position).id.toString())
            userList.drop(position)
            notifyDataSetChanged()

        }
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
            itemView.T_SmallDescrip.text=dataModel.shortDescription
            itemView.T_Counter.text= dataModel.productInCartQty.toString()

            itemView.T_Price.text=dataModel.productInCartTotal.toString()+" "+dataModel.currency
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                itemView.T_SmallDescrip.setText(
                    Html.fromHtml(
                        dataModel.description,
                        Html.FROM_HTML_MODE_LEGACY  )
                )
            } else {
                dataModel.description?.let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY) }

            }

            Glide.with(context)
                .load( dataModel.image).into(itemView.img_cart)


           itemView.T_Plus.setOnClickListener(){
               var count :Int= (itemView.T_Counter.text as String).toInt()
               count++
               itemView.T_Counter.text=count.toString()
               itemView.T_Plus.isEnabled=false
               plusId_View.Plus_Id(dataModel.id.toString(),count.toString())
           }
            itemView.T_Minus.setOnClickListener(){
                var count = Integer.parseInt(itemView.T_Counter.text.toString())
                if(count>1) {
                    count--
                    itemView.T_Counter.text=count.toString()
                    itemView.T_Minus.isEnabled=false
                    plusId_View.Plus_Id(dataModel.id.toString(),count.toString())
                }
            }


        }
    }
    }
