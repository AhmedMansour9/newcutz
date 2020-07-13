package com.mgh.Adapter

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mgh.Model.AllProducts_Response
import com.mgh.R
import com.mgh.View.DetailsProduct_id
import com.mgh.View.ProductDetails_View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_deal.view.*

class Deails_Adapter (context: Context, val userList: List<AllProducts_Response.Data.Data>)
    : RecyclerView.Adapter<Deails_Adapter.ViewHolder>() {
    lateinit var detailsProduct_id: DetailsProduct_id
    lateinit var ProductsDetails: ProductDetails_View
    var con=context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Deails_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_deal, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: Deails_Adapter.ViewHolder, position: Int) {
        holder.bindItems(userList.get(position))
        holder.itemView.setOnClickListener(){
            ProductsDetails.Details(userList.get(position))
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
        private val context: Context =itemView.context

        fun bindItems(dataModel: AllProducts_Response.Data.Data) {
//            itemView.T_Day.text=dataModel.data.day.toString()
//            itemView.T_Hour.text=dataModel.data.hour.toString()
//            itemView.T_Minuts.text=dataModel.data.min.toString()
            var discount=(dataModel.total!!.toDouble()/dataModel.hotDealPrice!!.toDouble())*100
            var disc=100-discount.toInt()
            itemView.T_Offer.text = disc.toString()+"%"

            itemView.Titl_Homee.text = dataModel.name
            itemView.T_SalePriceHome.text = dataModel.hotDealPrice.toString()+" "+
                    dataModel.currency
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

            if (dataModel.productInCart==0){
                itemView.Img_CartHOme.setImageResource(R.drawable.ic_emptycart)
            }else {
                itemView.Img_CartHOme.setImageResource(R.drawable.ic_cart)
            }



        }
    }

}