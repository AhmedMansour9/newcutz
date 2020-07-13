package com.mgh.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mgh.Model.AllProducts_Response
import com.mgh.R
import com.mgh.SharedPrefManager
import com.mgh.View.DetailsProduct_id
import com.mgh.View.ProductDetails_View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_product.view.*

class AllProducts_Adapter (context:Context,val userList: List<AllProducts_Response.Data.Data>)
    : RecyclerView.Adapter<AllProducts_Adapter.ViewHolder>() {
    lateinit var detailsProduct_id: DetailsProduct_id
    lateinit var ProductsDetails: ProductDetails_View
         var con=context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllProducts_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_favourit, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: AllProducts_Adapter.ViewHolder, position: Int) {
        holder.bindItems(userList.get(position))
        holder.itemView.setOnClickListener(){
            ProductsDetails.Details(userList.get(position))
        }

        holder.Img_Favourit.setOnClickListener(){

            if (userList.get(position).isProductFavoirte==1){
                userList.get(position).isProductFavoirte==0
                holder.Img_Favourit.setImageResource(R.drawable.ic_emptyfavourit)
                detailsProduct_id.AddToFavourit("customer/removeFavorite",userList.get(position).id.toString())

            }else {
                userList.get(position).isProductFavoirte==1
                holder.Img_Favourit.setImageResource(R.drawable.ic_productfavourit)
                detailsProduct_id.AddToFavourit("customer/addFavorite",userList.get(position).id.toString())

            }
        }


    }
    fun onClickFavourit(ProductsDetail: DetailsProduct_id){
        this.detailsProduct_id=ProductsDetail
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
        private val context:Context =itemView.context
        val Img_Favourit = itemView.findViewById(R.id.Img_Favourit) as ImageView

        fun bindItems(dataModel: AllProducts_Response.Data.Data) {
            val title = itemView.findViewById(R.id.Title) as TextView
            val img = itemView.findViewById(R.id.Img_Product) as ImageView
            var T_Descrption=itemView.findViewById(R.id.T_SmallDescrip)as TextView
            title.text = dataModel.name
            T_Descrption.text=dataModel.description
//                if (dataModel.sale_price.equals("0.00")) {
                    itemView.T_SalePrice.text = dataModel.total.toString()+" "+
                            context.resources.getString(R.string.currency)
//                } else {
//                    itemView.T_SalePrice.text = dataModel.sale_price+" "+dataModel.currency
//                    itemView.T_OriginalPrice.text = dataModel.price_general+" "+dataModel.currency
//                    itemView.T_OriginalPrice.setPaintFlags(itemView.T_OriginalPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
//            }

            if (dataModel.isProductFavoirte==1){
                itemView.Img_Favourit.setImageResource(R.drawable.ic_productfavourit)
            }else {
                itemView.Img_Favourit.setImageResource(R.drawable.ic_emptyfavourit)
            }

            Picasso.get()
                .load(dataModel.image)
                    .placeholder(R.drawable.place_holder)
                .resize(500,500)
                .into(img)

        }
    }
}