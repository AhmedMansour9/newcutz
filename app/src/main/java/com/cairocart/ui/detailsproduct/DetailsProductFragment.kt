package com.cairocart.ui.detailsproduct

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cairocart.R
import com.cairocart.base.BaseFragment
import com.cairocart.databinding.FragmentDetailsProductBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsProductFragment : BaseFragment<FragmentDetailsProductBinding>() {
    override var idLayoutRes: Int = R.layout.fragment_details_product

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }


}