package com.cutz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cutz.R
import com.cutz.data.remote.model.MyOrdersResponse
import com.cutz.databinding.ItemCartmyorderBinding
import com.facebook.appevents.suggestedevents.ViewOnClickListener
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.android.synthetic.main.item_myorder.view.*

class CartOrderDetailsAdapter (var mListCart: MutableList<MyOrdersResponse.Data.OrderDetial>,var status:String,var context: Context) :
    RecyclerView.Adapter<CartOrderDetailsAdapter.DeveloperViewHolder>() {



    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<ItemCartmyorderBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_cartmyorder, viewGroup, false
        )

        return DeveloperViewHolder(mTradersResponse)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentStudent = mListCart!![i]
        mDeveloperViewHolder.mListResponse.model = currentStudent
        mDeveloperViewHolder.onBind(mListCart!![i],i)
    }

    override fun getItemCount(): Int {
        return if (mListCart != null) {
            mListCart!!.size
        } else {
            0
        }
    }

    fun setDeveloperList(mDeveloperModel: MutableList<MyOrdersResponse.Data.OrderDetial>) {
        this.mListCart = mDeveloperModel
        notifyDataSetChanged()
    }
    inner class DeveloperViewHolder(var mListResponse: ItemCartmyorderBinding) :
        RecyclerView.ViewHolder(mListResponse.root) {
        fun onBind(position: MyOrdersResponse.Data.OrderDetial,i:Int) {
            setStatus(status)
//        if(i==mListCart.size-1){
//            mListResponse.Relaa.visibility=View.VISIBLE
//        }else {
//            mListResponse.Relaa.visibility=View.GONE
//        }

        }

        private fun setStatus(position: String?) {
            if(status.equals("pendding")){
                itemView.T_Status.text=context.resources.getString(R.string.pending)
            }
            else if(status.equals("inShipment")){
                itemView.T_Status.text=context.resources.getString(R.string.shipping)

            }
            else if(status.equals("onDelivery")){
                itemView.T_Status.text=context.resources.getString(R.string.ondelivry)

            }
            else if(equals("On arrival")){
                itemView.T_Status.text=context.resources.getString(R.string.onarrival)

            }
            else if(status.equals("completed")){
                itemView.T_Status.text=context.resources.getString(R.string.completed)
            }
            else if(status.equals("canceled")){
                itemView.T_Status.text=context.resources.getString(R.string.canceled)
            }
        }


    }


}