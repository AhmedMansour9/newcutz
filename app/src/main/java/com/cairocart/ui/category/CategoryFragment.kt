package com.cairocart.ui.category

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import com.cairocart.R
import com.cairocart.TreeItemController
import com.cairocart.base.BaseFragment
import com.cairocart.data.remote.model.CatModel
import com.cairocart.data.remote.model.CategoriesResponse
import com.cairocart.data.remote.model.MessageEvent
import com.cairocart.data.remote.model.Node
import com.cairocart.databinding.CategoryFragmentBinding
import com.cairocart.mapper.toTree
import com.cairocart.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


@AndroidEntryPoint
class CategoryFragment : BaseFragment<CategoryFragmentBinding>() {

    override var idLayoutRes: Int = R.layout.category_fragment

    val mViewModel: CategoryViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }

    private val controller = TreeItemController(
        ::onCatModelClicked
    )

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
        return navController.getViewModelStoreOwner(R.id.graph_home)
    }

    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent(messsg: MessageEvent) {/* Do something */
        if (messsg.Message.equals("cat")) {
            val bundle = Bundle()
            bundle.putParcelable("cat", messsg.catmodel)
            Navigation.findNavController(mViewDataBinding.root)
                .navigate(R.id.action_T_Categories_to_productsById, bundle);
        }
    };
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

}