package com.hadrmout.ui.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hadrmout.R
import com.hadrmout.adapter.BlogsAdapter
import com.hadrmout.adapter.LoadStateViewHolder
import com.hadrmout.base.BaseFragment
import com.hadrmout.data.remote.model.BlogsResponse
import com.hadrmout.databinding.FragmentRecipesBinding
import com.hadrmout.ui.detailsblog.DetailsReciepFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecipesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class RecipesFragment : BaseFragment<FragmentRecipesBinding>(),
    SwipeRefreshLayout.OnRefreshListener {
    override var idLayoutRes: Int = R.layout.fragment_recipes
    private var searchJob: Job? = null
    private lateinit var productsGridAdapter: BlogsAdapter

    val mViewModel: ReciepesViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        initAdapter()
        initUI()
        setupView()
    }

    private fun init() {
        mViewDataBinding.productsViewModel = mViewModel
        mViewDataBinding.SwipCategories.setOnRefreshListener(this)
//        mViewModel.Lang.value = ChangeLanguage.getLanguage(requireContext())
    }

    fun initAdapter() {
        productsGridAdapter = BlogsAdapter(requireContext(), productData = object :
            BlogsAdapter.ProductItemListener {
            override fun itemClicked(productData: BlogsResponse.Data?) {
                val bundle = Bundle()
                bundle.putParcelable("data", productData)
                val newDialogFragment = DetailsReciepFragment()
                newDialogFragment.arguments = bundle
                val transaction: FragmentTransaction =
                    requireActivity().supportFragmentManager.beginTransaction()
                newDialogFragment.show(transaction, "New_Dialog_Fragment")
            }

        })
    }

    private fun initUI() {
        mViewDataBinding.recyclerBlogs.isMotionEventSplittingEnabled = false
        mViewDataBinding.recyclerBlogs.setLayoutManager(LinearLayoutManager(requireContext()))
        mViewDataBinding.recyclerBlogs.adapter = productsGridAdapter
        mViewDataBinding.recyclerBlogs.adapter =
            productsGridAdapter.withLoadStateFooter(footer = LoadStateViewHolder.LoadingStateAdapter {
                productsGridAdapter.retry()
            })
        productsGridAdapter.addLoadStateListener {
            val status = it.source.refresh is LoadState.Loading
            if (status) {
//                hideEmptyPage()
                showLoading()
            } else {
                dismissLoading()
//                checkEmpryProduct(it)
            }


        }

    }

    private fun setupView() {
        searchJob?.cancel()

        searchJob = lifecycleScope.launch {
            mViewModel.listData.collect {
                productsGridAdapter.submitData(it)
            }
        }

    }

    override fun onRefresh() {
        mViewDataBinding.SwipCategories.isRefreshing = false
        productsGridAdapter.refresh()
    }

}