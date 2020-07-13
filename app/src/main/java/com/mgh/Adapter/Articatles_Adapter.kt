package com.mgh.Adapter

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mgh.Model.News_Response
import com.mgh.R
import com.mgh.View.DetailsArticatal_View
import com.squareup.picasso.Picasso

class Articatles_Adapter  (val userList: List<News_Response.Data>)
    : RecyclerView.Adapter<Articatles_Adapter.ViewHolder>() {
    //this method is returning the view for each item in the list
    lateinit var ProductsDetails: DetailsArticatal_View

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Articatles_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_articatle, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: Articatles_Adapter.ViewHolder, position: Int) {
        holder.bindItems(userList.get(position))
        holder.itemView.setOnClickListener() {
            ProductsDetails.details(userList.get(position))
        }
    }

    fun onClick(ProductsDetail: DetailsArticatal_View) {
        this.ProductsDetails = ProductsDetail
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context = itemView.context

        fun bindItems(dataModel: News_Response.Data) {
            val title = itemView.findViewById(R.id.T_Title) as TextView
//            val T_Description = itemView.findViewById(R.id.T_Description) as TextView
            val img = itemView.findViewById(R.id.imag_product) as ImageView

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                title.setText(
                        Html.fromHtml(
                                dataModel.title,
                                Html.FROM_HTML_MODE_LEGACY  )
                )
            } else {
                title.text = dataModel.title
            }

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                T_Description.setText(
//                    Html.fromHtml(
//                        dataModel.description,
//                        Html.FROM_HTML_MODE_LEGACY  )
//                )
//            } else {
//                HtmlCompat.fromHtml(dataModel.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
//
//            }
            Picasso.get()
                .load(dataModel.image)
                    .placeholder(R.drawable.place_holder)
                .resize(500, 500)
                .into(img)

        }
    }
}