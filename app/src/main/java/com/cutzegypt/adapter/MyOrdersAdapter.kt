package com.cutzegypt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cutzegypt.R
import com.cutzegypt.data.remote.model.MyOrdersResponse
import com.cutzegypt.databinding.ItemMyorderBinding
import kotlinx.android.synthetic.main.item_myorder.view.*

class MyOrdersAdapter(var context:Context,var itemclick: CartItemListner) :
    RecyclerView.Adapter<MyOrdersAdapter.DeveloperViewHolder>() {
    private var mListOrders: MutableList<MyOrdersResponse.Data>? = arrayListOf()
    private val viewPool = RecyclerView.RecycledViewPool()


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<ItemMyorderBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_myorder, viewGroup, false )
        return DeveloperViewHolder(mTradersResponse)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentStudent = mListOrders?.get(i)
        mDeveloperViewHolder.mTradersResponse.model = currentStudent
        mDeveloperViewHolder.onBind(mListOrders?.get(i))

        val childLayoutManager = LinearLayoutManager(mDeveloperViewHolder.itemView.recycler_Details.context, LinearLayoutManager.VERTICAL, false)

        mDeveloperViewHolder.itemView.recycler_Details.apply {
            layoutManager = childLayoutManager
            adapter = currentStudent?.orderDetials?.let { CartOrderDetailsAdapter(it,currentStudent.status.toString(),context) }
            setRecycledViewPool(viewPool)
        }

    }

    fun setList(mDeveloperModel: MutableList<MyOrdersResponse.Data>) {
        this.mListOrders = mDeveloperModel
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (mListOrders != null) {
            mListOrders!!.size
        } else {
            0
        }
    }


    inner class DeveloperViewHolder(var mTradersResponse: ItemMyorderBinding) :
        RecyclerView.ViewHolder(mTradersResponse.root) {
        fun onBind(position: MyOrdersResponse.Data?) {
            setStatus(position)
            mTradersResponse.TTotalPrice.text=position?.total.toString()+" "+position?.orderDetials?.get(0)?.product?.currency
            itemView.setOnClickListener() {
                itemclick.onclick(position!!)
            }

        }

        private fun setStatus(position: MyOrdersResponse.Data?) {
            if(position?.status.equals("pendding")){
                itemView.T_Status.text=context.getString(R.string.status)+" "+context.resources.getString(R.string.pending)
            }
            else if(position?.status.equals("inShipment")){
                itemView.T_Status.text=context.getString(R.string.status)+" "+context.resources.getString(R.string.shipping)

            }
            else if(position?.status.equals("onDelivery")){
                itemView.T_Status.text=context.getString(R.string.status)+" "+context.resources.getString(R.string.ondelivry)

            }
            else if(position?.status.equals("On arrival")){
                itemView.T_Status.text=context.getString(R.string.status)+" "+context.resources.getString(R.string.onarrival)

            }
            else if(position?.status.equals("completed")){
                itemView.T_Status.text=context.getString(R.string.status)+" "+context.resources.getString(R.string.completed)
            }
            else if(position?.status.equals("canceled")){
                itemView.T_Status.text=context.getString(R.string.status)+" "+context.resources.getString(R.string.canceled)
            }
        }


    }

    interface CartItemListner {

        fun onclick(list: MyOrdersResponse.Data)

    }
}