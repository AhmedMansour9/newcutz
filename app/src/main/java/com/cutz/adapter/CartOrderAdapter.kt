package com.cutz.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cutz.R
import com.cutz.data.remote.model.ListCartResponse
import com.cutz.databinding.ItemcartorderBinding
import kotlinx.android.synthetic.main.item_cart.view.*

class CartOrderAdapter (context:Context) :
    RecyclerView.Adapter<CartOrderAdapter.DeveloperViewHolder>() {
    var context: Context

    init {
        this.context=context
    }
    private var mListCart: MutableList<ListCartResponse.Data.CartItem>? = arrayListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<ItemcartorderBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.itemcartorder, viewGroup, false
        )

        return DeveloperViewHolder(mTradersResponse)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentStudent = mListCart!![i]
        mDeveloperViewHolder.mListResponse.model = currentStudent
        mDeveloperViewHolder.onBind(mListCart!![i])
    }

    override fun getItemCount(): Int {
        return if (mListCart != null) {
            mListCart!!.size
        } else {
            0
        }
    }

    fun setDeveloperList(mDeveloperModel: MutableList<ListCartResponse.Data.CartItem>) {
        this.mListCart = mDeveloperModel
        notifyDataSetChanged()
    }
    inner class DeveloperViewHolder(var mListResponse: ItemcartorderBinding) :
        RecyclerView.ViewHolder(mListResponse.root) {
        fun onBind(position: ListCartResponse.Data.CartItem) {
            setTotal(position)
        }



        fun setTotal(position: ListCartResponse.Data.CartItem){
            itemView.T_TotalPrice.text = context.resources.getString(R.string.total_price) +" "+position.totalPriceItems
                    .toString() + context.resources.getString(R.string.currency)
        }
    }

}