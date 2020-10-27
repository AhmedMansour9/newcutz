package com.cairocart.Adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cairocart.Model.AllProducts_Response
import com.cairocart.R
import com.cairocart.View.DetailsProduct_id
import com.cairocart.View.ProductDetails_View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_deal.view.*
import java.text.SimpleDateFormat
import java.util.*

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
            itemView.T_TotalPrice.text=dataModel.total.toString()+ dataModel.currency
            itemView.T_TotalPrice.setPaintFlags(itemView.T_TotalPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

//            itemView.RatingBarHome.rating=dataModel.review!!.toFloat()


                itemView.T_SmallDescripHome.text = dataModel.shortDescription


            if (dataModel.productInCart==0){
                itemView.Img_CartHOme.setImageResource(R.drawable.ic_emptycart)
            }else {
                itemView.Img_CartHOme.setImageResource(R.drawable.ic_cart)
            }

            getDays(dataModel.expireDateHotDeal.toString())


        }

        fun getDays(date:String) {
//            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

            val datenow = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())

            val format = "yyyy-MM-dd HH:mm:ss"
            val sdf = SimpleDateFormat(format)

            val dateObj1: Date = sdf.parse(datenow)
            val dateObj2: Date = sdf.parse(date)
            println(dateObj2.toString() + "\n")

            val diff: Long = dateObj2.getTime() - dateObj1.getTime()

            val diffDays = (diff / (24 * 60 * 60 * 1000)).toInt()
            println("difference between days: $diffDays")
            var different: Long =dateObj2.getTime() - dateObj1.getTime()

            val secondsInMilli: Long = 1000
            val minutesInMilli = secondsInMilli * 60
            val hoursInMilli = minutesInMilli * 60
            val daysInMilli = hoursInMilli * 24

            val elapsedDays = different / daysInMilli
            different = different % daysInMilli

            val elapsedHours = different / hoursInMilli
            different = different % hoursInMilli

            val elapsedMinutes = different / minutesInMilli
            different = different % minutesInMilli

            val elapsedSeconds = different / secondsInMilli
            itemView.T_Day.text=elapsedDays.toString()
            itemView.T_Hour.text=elapsedHours.toString()
            itemView.T_Minuts.text=elapsedMinutes.toString()

        }
    }

}