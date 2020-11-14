package com.cairocart.ui.detailsproduct

import android.os.Bundle
import com.cairocart.R
import com.cairocart.base.BaseFragment
import com.cairocart.data.remote.model.ProductsResponse
import com.cairocart.databinding.FragmentDetailsProductBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class DetailsProductFragment : BaseFragment<FragmentDetailsProductBinding>() {
    override var idLayoutRes: Int = R.layout.fragment_details_product
    private lateinit var details: ProductsResponse.Data
    private var bundle = Bundle()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }


}