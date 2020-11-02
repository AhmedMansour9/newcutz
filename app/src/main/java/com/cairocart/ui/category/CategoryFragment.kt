package com.cairocart.ui.category

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.cairocart.R
import com.cairocart.base.BaseFragment
import com.cairocart.data.remote.model.CatModel
import com.cairocart.data.remote.model.Categories_Response
import com.cairocart.data.remote.model.Node
import com.cairocart.databinding.CategoryFragmentBinding
import com.cairocart.TreeItemController
import com.cairocart.mapper.toTree
import com.cairocart.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<CategoryFragmentBinding>() {

    override var idLayoutRes: Int = R.layout.category_fragment

    private val mViewModel: CategoryViewModel by viewModels()

    private val controller = TreeItemController(::onCatModelClicked)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewDataBinding.treeItemsRv.setController(controller)

        categoryObserver()
    }

    private fun categoryObserver() {
        mViewModel.categoryResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    Log.d("CategoryFragment", "categoryObserver: " + it.data.toString())
                    addData(it.data?.data)
                }
                Status.LOADING -> {
                    showLoading()
                }

                Status.ERROR -> {
                   dismissLoading()
                    // toast

                }
            }
        })
    }

    var tree: Node<CatModel>? = null
    private fun addData(data: Categories_Response.DataCategory?) {
        tree = data?.toTree()
        tree?.let {
            controller.setData(it)
        }
    }


    private fun onCatModelClicked(node: Node<CatModel>) {
        node.value = node.value.copy(isExpanded = !node.value.isExpanded)
        tree?.let {
            controller.setData(it)
        }
    }
}