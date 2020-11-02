package com.cairocart.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.cairocart.R
import com.cairocart.base.BaseFragment
import com.cairocart.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class HomeFragment  : BaseFragment<FragmentHomeBinding>()  {

    override var idLayoutRes: Int = R.layout.fragment_home


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}