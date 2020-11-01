package com.cairocart.ui.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.cairocart.R
import com.cairocart.base.BaseFragment
import com.cairocart.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>() {

    override var idLayoutRes: Int = R.layout.home_fragment

    private val mViewModel: HomeViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

}