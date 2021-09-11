package com.hadrmout.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hadrmout.R
import com.hadrmout.data.remote.model.ProductsResponse
import com.hadrmout.databinding.RowProductgridBinding
import kotlinx.android.synthetic.main.row_product.view.*


class GridProductsAdapter(context: Context, var productData: ProductItemListener) :
    RecyclerView.Adapter<GridProductsAdapter.DeveloperViewHolder>(), Filterable {
    var context: Context

    init {
        this.context = context

    }

    private var mListCart: MutableList<ProductsResponse.Data>? = arrayListOf()
    private var mListFilter: MutableList<ProductsResponse.Data>? = arrayListOf()


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<RowProductgridBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.row_productgrid, viewGroup, false
        )

        return DeveloperViewHolder(mTradersResponse)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentStudent = mListFilter!![i]
        mDeveloperViewHolder.mTradersResponse.model = currentStudent
        mDeveloperViewHolder.onBind(mListFilter!![i])


    }


    override fun getItemCount(): Int {
        return if (mListFilter != null) {
            mListFilter!!.size
        } else {
            0
        }
    }


    interface ProductItemListener {
        fun itemClicked(productData: ProductsResponse.Data?);
        fun itemFavourit(productData: ProductsResponse.Data?);

    }


    fun setDeveloperList(mDeveloperModel: MutableList<ProductsResponse.Data>) {
        this.mListCart = mDeveloperModel
        mListFilter = mListCart
        notifyDataSetChanged()
    }

    inner class DeveloperViewHolder(var mTradersResponse: RowProductgridBinding) :
        RecyclerView.ViewHolder(mTradersResponse.root) {

        @SuppressLint("SetTextI18n")
        fun onBind(position: ProductsResponse.Data) {

            mTradersResponse.model = position
            mTradersResponse.TSalePrice.text =
                position.finalPrice + " " + context.resources.getString(R.string.currency)
            if (!position!!.isFavoirte) {
                itemView.Img_Favourit.setImageResource(R.drawable.ic_emptyfavourit)
            } else {
                itemView.Img_Favourit.setImageResource(R.drawable.ic_favourit)
            }

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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    mListFilter = mListCart
                } else {
                    val resultList = ArrayList<ProductsResponse.Data>()
                    for (row in mListCart!!) {
                        if (row.title.contains(charSearch)
                        ) {
                            resultList.add(row)
                        }
                    }
                    mListFilter = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = mListFilter
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                mListFilter = results?.values as ArrayList<ProductsResponse.Data>
                notifyDataSetChanged()
            }
        }
    }


}

