package com.cairocart.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cairocart.Model.AllProducts_Response
import com.cairocart.R
import com.cairocart.View.DetailsProduct_id
import com.cairocart.View.ProductDetails_View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_product.view.*

class Favourit_Adapter  (val userList: List<AllProducts_Response.Data.Data>)
    : RecyclerView.Adapter<Favourit_Adapter.ViewHolder>() {
    //this method is returning the view for each item in the list
    lateinit var ProductsDetails: ProductDetails_View
    lateinit var detailsProduct_id: DetailsProduct_id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Favourit_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_favourit, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: Favourit_Adapter.ViewHolder, position: Int) {
        holder.bindItems(userList.get(position))
        holder.itemView.setOnClickListener(){
            ProductsDetails.DetailsProducts(userList.get(position))
        }

        holder.Img_Favourit.setOnClickListener(){
            detailsProduct_id.AddToFavourit("customer/removeFavorite",userList.get(position).id.toString())
            userList.drop(position)
            notifyDataSetChanged()
        }
    }
    fun onClick(ProductsDetail: ProductDetails_View){
        this.ProductsDetails=ProductsDetail
    }

    fun onClickFavourit(ProductsDetail: DetailsProduct_id){
        this.detailsProduct_id=ProductsDetail
    }


    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context =itemView.context
        val Img_Favourit = itemView.findViewById(R.id.Img_Favourit) as ImageView

        fun bindItems(dataModel: AllProducts_Response.Data.Data) {
            val title = itemView.findViewById(R.id.Title) as TextView
            val img = itemView.findViewById(R.id.Img_Product) as ImageView

            title.text = dataModel.name
           itemView.T_SalePrice.text = dataModel.total.toString()+" "+
                   dataModel.currency


            val RatingBar = itemView.findViewById(R.id.RatingBar) as RatingBar
//            RatingBar.rating=dataModel.rate!!.toFloat()

                itemView.Img_Favourit.setImageResource(R.drawable.ic_productfavourit)

            Picasso.get()
                .load(dataModel.image)
                    .resize(500,500)
                    .into(img)

        }

        }
    }
