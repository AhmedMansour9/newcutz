package com.mgh.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.mgh.Fragments.Product_Details
import com.mgh.Model.ExtraAdditonal_Response
import com.mgh.R
import com.mgh.View.ChechBox_View
import com.mgh.View.OnClickProductColorView
import com.mgh.View.onClickFilter_View
import com.chicchicken.Model.Checkbox_Id
import com.chicchicken.Model.Checkboxs
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_additonal.view.*
import java.util.ArrayList

class AddetionalDetails_Adapter (context: Context, val userList: List<ExtraAdditonal_Response.Data>)
    : RecyclerView.Adapter<AddetionalDetails_Adapter.ViewHolder>() {
    lateinit var productbyid: onClickFilter_View
    lateinit var checkbox: ChechBox_View

    var row_index:Int = -1
    var context: Context =context
    companion object {
        var IdList: MutableList<Checkbox_Id> = ArrayList()
    }
    lateinit var  onClickProductSizesView: OnClickProductColorView
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): AddetionalDetails_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_additonal, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: AddetionalDetails_Adapter.ViewHolder, position: Int) {
        holder.itemView.T_Title.text=userList.get(position).title
        holder.itemView.T_Pricee.text=userList.get(position).priceGeneral+" "+ Product_Details.Currency

        Picasso.get()
                .load("http://chic-chicken.co.il" + userList.get(position).image)
                .resize(500, 500)
                .into(holder.itemView.Img_cat)

        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val puthesData = Checkboxs()
                puthesData.id=userList.get(position).id
                if (IdList.isEmpty()) {
                    val checkbox_id = Checkbox_Id()
                    checkbox_id.setId(userList.get(position).id)
                    IdList.add(checkbox_id)
                    checkbox.plus(userList.get(position).priceGeneral)

                } else {
                    val poistion = userList.get(position).id
                    for (i in IdList.indices) {
                        if (IdList.get(i).getId() === poistion) {
                            IdList.removeAt(i).getId()
                        }
                    }
                     checkbox.plus(userList.get(position).priceGeneral)

                    val checkbox_id = Checkbox_Id()
                    checkbox_id.setId(userList.get(position).id)
                    IdList.add(checkbox_id)
                }
            } else {
                checkbox.minus(userList.get(position).priceGeneral)
                val poistion = userList.get(position).id
                for (i in IdList.indices) {
                    if (IdList.get(i).getId() === poistion) {
                        IdList.removeAt(i).getId()
                    }
                }
            }

        }


    }
    fun onClickCheck(onClickProductColorView: ChechBox_View) {
        this.checkbox = onClickProductColorView
    }

    fun onClickProductSize(onClickProductColorView: OnClickProductColorView) {
        this.onClickProductSizesView = onClickProductColorView
    }
    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }


    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        private val context: Context = itemView.context
        var checkBox = itemView.findViewById(R.id.checkBox) as CheckBox

    }
}