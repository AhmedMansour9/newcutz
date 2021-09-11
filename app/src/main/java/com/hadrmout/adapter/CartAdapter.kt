
package com.hadrmout.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hadrmout.R
import com.hadrmout.data.remote.model.ListCartResponse
import com.hadrmout.databinding.ItemCartBinding
import kotlinx.android.synthetic.main.item_cart.view.*

class CartAdapter( context: Context,var itemclick:CartItemListner) :
    RecyclerView.Adapter<CartAdapter.DeveloperViewHolder>() {
     var context:Context

    init {
        this.context=context
    }
    private var mListCart: MutableList<ListCartResponse.Data.CartItem>? = arrayListOf()


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<ItemCartBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_cart, viewGroup, false
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

    inner class DeveloperViewHolder(var mListResponse: ItemCartBinding) :
        RecyclerView.ViewHolder(mListResponse.root) {
        var counter = 1
        var totalPrice: Int = 1

        fun onBind(position: ListCartResponse.Data.CartItem) {
            setTotal(position)
            setPrice(position)
            onClickPlus(position)
            onCLickMinus(position)
            onClickDelete(position)


        }

        @SuppressLint("SetTextI18n")
        private fun setPrice(position: ListCartResponse.Data.CartItem) {
            position?.finalPrice
            mListResponse.TSalePrice.text =
                context.resources.getString(R.string.price) + " " + position.finalPrice + " " + context.resources.getString(
                    R.string.currency
                )


        }

        fun onClickDelete(position: ListCartResponse.Data.CartItem) {
            itemView.Img_Delete.setOnClickListener() {
                run {
                    itemclick.deleteItem(position.id.toString())
                }
            }
        }

        fun onCLickMinus(position: ListCartResponse.Data.CartItem) {
            itemView.Img_Minus.setOnClickListener() {
                checkMinCart(position)

            }
        }

        fun onClickPlus(position: ListCartResponse.Data.CartItem) {
            itemView.Img_Plus.setOnClickListener() {
                checkMaxCart(position)
            }
        }

        @SuppressLint("SetTextI18n")
        fun setTotal(position: ListCartResponse.Data.CartItem) {
            itemView.T_TotalPrice.text =
                context.resources.getString(R.string.total_price) + " " + position.totalPriceItems.toString() + " " + context.resources.getString(
                    R.string.currency
                )

        }

        @SuppressLint("SetTextI18n")
        fun checkMaxCart(details: ListCartResponse.Data.CartItem) {
//            var counter = itemView.T_Quantity.text.toString().toInt()
//            val countValue: Int = details.product?.stock!!
//            val defult = itemView.T_Quantity.text.toString().toInt()
//            if (countValue > defult) {
            counter++
            itemView.T_Quantity.text = counter.toString()
            run {
                itemclick.ediItem(details.id.toString(), itemView.T_Quantity.text.toString())
            }
//            } else {
//                Toast.makeText(
//                    context,
//                    context.resources.getString(R.string.max_cart) + " " + details.product?.stock,
//                    Toast.LENGTH_LONG
//                ).show()
//            }

        }

        @SuppressLint("SetTextI18n")
        fun checkMinCart(details: ListCartResponse.Data.CartItem) {
            var counter = itemView.T_Quantity.text.toString().toInt()
            if (counter > 1) {
                itemView.Img_Plus.isEnabled=true
                counter--
                itemView.T_Quantity.text = counter.toString()
                run {
                    itemclick.ediItem(details.id.toString(), itemView.T_Quantity.text.toString())
                }
            }
        }
    }

    interface CartItemListner{

        fun ediItem(item_id:String,qty:String)

        fun deleteItem(item:String)

    }

}