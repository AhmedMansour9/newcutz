package com.hadrmout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hadrmout.R
import com.hadrmout.data.remote.model.ProductsResponse
import com.hadrmout.databinding.RowProductBinding
import kotlinx.android.synthetic.main.row_product.view.*

class ProductCollectionAdapter (list :List<ProductsResponse.Data>,var productData: ProductItemListener) :
    RecyclerView.Adapter<ProductCollectionAdapter.DeveloperViewHolder>() {

    var listInformation: List<ProductsResponse.Data>
    init {
        listInformation=list
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<RowProductBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.row_product, viewGroup, false
        )

        return DeveloperViewHolder(mTradersResponse)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentStudent = listInformation[i]
        mDeveloperViewHolder.mTradersResponse.model = currentStudent
        mDeveloperViewHolder.onBind(currentStudent)



    }

    override fun getItemCount(): Int {
        return listInformation.size
    }

    interface ProductItemListener {
        fun itemClicked(productData: ProductsResponse.Data?);
        fun itemFavourit(productData: ProductsResponse.Data?);

    }

    inner class DeveloperViewHolder(var mTradersResponse: RowProductBinding) :
        RecyclerView.ViewHolder(mTradersResponse.root){

        fun onBind(position: ProductsResponse.Data?) {

            mTradersResponse.model = position

            fun checkToken() {
                    if (position!!.isFavoirte) {
                        position.isFavoirte = false
                        itemView.Img_Favourit.setImageResource(R.drawable.ic_emptyfavourit)
                    } else {
                        position.isFavoirte = true
                        itemView.Img_Favourit.setImageResource(R.drawable.ic_favourit)
                    }

            }
            itemView.Img_Favourit.setOnClickListener { view ->
                run {
                    productData.itemFavourit(position)
                    checkToken()
                }
            }

            itemView.setOnClickListener { view ->
                run {
                    productData.itemClicked(position)
                }
            }
        }

    }


}