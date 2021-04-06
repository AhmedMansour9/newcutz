package com.cutz.ui.aboutus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cutz.ChangeLanguage
import com.cutz.R
import com.cutz.adapter.AboutUsAdapter
import com.cutz.adapter.BlogsAdapter
import com.cutz.adapter.LoadStateViewHolder
import com.cutz.base.BaseFragment
import com.cutz.data.remote.model.BlogsResponse
import com.cutz.databinding.FragmentAboutUsBinding
import com.cutz.ui.recipes.ReciepesViewModel
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
 * Use the [AboutUsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class AboutUsFragment : BaseFragment<FragmentAboutUsBinding>(),
    SwipeRefreshLayout.OnRefreshListener {
    override var idLayoutRes: Int = R.layout.fragment_about_us
    private var searchJob: Job? = null
    private lateinit var productsGridAdapter: AboutUsAdapter

    val mViewModel: AboutUsViewModel by navGraphViewModels(R.id.graph_home) {
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
        mViewDataBinding.SwipCategories.setOnRefreshListener(this)
        mViewModel.Lang.value = ChangeLanguage.getLanguage(requireContext())
    }

    fun initAdapter() {
        productsGridAdapter = AboutUsAdapter(requireContext())
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