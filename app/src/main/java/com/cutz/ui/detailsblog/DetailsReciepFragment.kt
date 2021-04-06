package com.cutz.ui.detailsblog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cutz.R
import com.cutz.base.BaseDialogFragment
import com.cutz.data.remote.model.BlogsResponse
import com.cutz.data.remote.model.ProductsResponse
import com.cutz.databinding.FragmentDetailsReciepBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsReciepFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsReciepFragment : BaseDialogFragment<FragmentDetailsReciepBinding>() {

    override var idLayoutRes: Int = R.layout.fragment_details_reciep
    private lateinit var details: BlogsResponse.Data
    private var bundle = Bundle()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       bundle=this.requireArguments()
        details=bundle.getParcelable("data")!!
        mViewDataBinding.model=details

    }

}