package com.cutz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cutz.R
import com.cutz.data.remote.model.ProductsResponse
import com.cutz.databinding.RowInformationBinding

class InformationAdapter(list :List<ProductsResponse.Data.Instruction> ) :
    RecyclerView.Adapter<InformationAdapter.DeveloperViewHolder>() {

    var listInformation: List<ProductsResponse.Data.Instruction>
      init {
          listInformation=list
      }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<RowInformationBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.row_information, viewGroup, false
        )

        return DeveloperViewHolder(mTradersResponse)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentStudent = listInformation[i]
        mDeveloperViewHolder.mTradersResponse.model = currentStudent




    }

    override fun getItemCount(): Int {
        return listInformation.size
    }



    inner class DeveloperViewHolder(var mTradersResponse: RowInformationBinding) :
        RecyclerView.ViewHolder(mTradersResponse.root){

    }



}