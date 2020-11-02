package com.cairocart.ui.category

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.cairocart.R
import com.cairocart.base.BaseFragment
import com.cairocart.databinding.CategoryFragmentBinding
import com.cairocart.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<CategoryFragmentBinding>() {

    override var idLayoutRes: Int = R.layout.category_fragment

    private val mViewModel: CategoryViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        categoryObserver()
    }

    private fun categoryObserver() {
        mViewModel.categoryResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    Log.d("CategoryFragment", "categoryObserver: " + it.data.toString())

                }
                Status.LOADING -> {

                }

                Status.ERROR -> {

                    // toast

                }
            }
        })
    }


}