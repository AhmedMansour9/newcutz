package com.hadrmout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hadrmout.R
import com.hadrmout.data.remote.model.ProductsResponse
import com.hadrmout.databinding.RowFavouritBinding
import com.hadrmout.databinding.RowProductBinding
import kotlinx.android.synthetic.main.row_product.view.*

class WishlistAdapter ( var productData: ProductItemListener) :
    RecyclerView.Adapter<WishlistAdapter.DeveloperViewHolder>() {

    private var mList: MutableList<ProductsResponse.Data>? = arrayListOf()


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<RowFavouritBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.row_favourit, viewGroup, false
        )

        return DeveloperViewHolder(mTradersResponse)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentStudent = mList!![i]
        mDeveloperViewHolder.mTradersResponse.model = currentStudent
        mDeveloperViewHolder.onBind(currentStudent)



    }

    override fun getItemCount(): Int {
        return if (mList != null) {
            mList!!.size
        } else {
            0
        }
    }

    fun setDeveloperList(mDeveloperModel: MutableList<ProductsResponse.Data>) {
        this.mList = mDeveloperModel
        notifyDataSetChanged()
    }

    interface ProductItemListener {
        fun itemClicked(productData: ProductsResponse.Data?);
        fun itemFavourit(productData: ProductsResponse.Data?);

    }

    inner class DeveloperViewHolder(var mTradersResponse: RowFavouritBinding) :
        RecyclerView.ViewHolder(mTradersResponse.root){

        fun onBind(position: ProductsResponse.Data?) {

            mTradersResponse.model = position

            fun checkToken() {
                position?.isFavoirte = false
//                itemView.Img_Favourit.setImageResource(R.drawable.ic_emptyfavourit)

            }
//            itemView.Img_Favourit.setOnClickListener { view ->
//                run {
//                    productData.itemFavourit(position)
//                    checkToken()
//                }
//            }

            itemView.setOnClickListener { view ->
                run {
                    productData.itemClicked(position)
                }
            }
        }

    }

}