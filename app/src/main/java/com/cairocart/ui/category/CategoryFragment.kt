package com.cairocart.ui.category

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import com.cairocart.R
import com.cairocart.TreeItemController
import com.cairocart.base.BaseFragment
import com.cairocart.data.remote.model.CatModel
import com.cairocart.data.remote.model.CategoriesResponse
import com.cairocart.data.remote.model.Node
import com.cairocart.databinding.CategoryFragmentBinding
import com.cairocart.mapper.toTree
import com.cairocart.utils.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CategoryFragment : BaseFragment<CategoryFragmentBinding>() {

    override var idLayoutRes: Int = R.layout.category_fragment

    val mViewModel: CategoryViewModel by navGraphViewModels(R.id.navigation2) {
        defaultViewModelProviderFactory
    }

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
    private fun addData(data: CategoriesResponse.DataCategory?) {
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

    private fun getStoreOwner(): ViewModelStoreOwner? {
        val navController = Navigation
            .findNavController(requireActivity(), R.id.fragment)
        return navController.getViewModelStoreOwner(R.id.navigation2)
    }
}