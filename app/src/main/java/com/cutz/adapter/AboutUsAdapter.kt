package com.cutz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cutz.data.remote.model.AboutUsResponse
import com.cutz.data.remote.model.BlogsResponse
import com.cutz.databinding.ItemAboutusBinding
import com.cutz.databinding.ItemBlogBinding

class AboutUsAdapter(context: Context) :
    PagingDataAdapter<AboutUsResponse.Data, RecyclerView.ViewHolder>(
        DataDifferntiator
    ) {

    var context: Context
    init {
        this.context=context
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): RecyclerView.ViewHolder {
        return ProductsLinearViewHolder(
            ItemAboutusBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        )

    }




    inner class ProductsLinearViewHolder(var mTradersResponse: ItemAboutusBinding) :
        RecyclerView.ViewHolder(mTradersResponse.root) {

        fun onBind(position: AboutUsResponse.Data?) {
            mTradersResponse.model = position


        }


    }


    interface ProductItemListener {
        fun itemClicked(productData: AboutUsResponse.Data?);

    }

    object DataDifferntiator : DiffUtil.ItemCallback<AboutUsResponse.Data>() {

        override fun areItemsTheSame(
            oldItem: AboutUsResponse.Data,
            newItem: AboutUsResponse.Data
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AboutUsResponse.Data,
            newItem: AboutUsResponse.Data
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProductsLinearViewHolder).onBind(getItem(position))

    }


}