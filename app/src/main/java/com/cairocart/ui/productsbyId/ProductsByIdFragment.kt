package com.cairocart.ui.productsbyId

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cairocart.ChangeLanguage
import com.cairocart.R
import com.cairocart.adapter.LoadStateViewHolder
import com.cairocart.adapter.ProductsGridByIdAdapter
import com.cairocart.base.BaseFragment
import com.cairocart.data.remote.model.CatModel
import com.cairocart.data.remote.model.ProductsResponse
import com.cairocart.databinding.FragmentProductsByIdBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsById : BaseFragment<FragmentProductsByIdBinding>(), ProductByIdNavigator {

    var linearView = true
    lateinit var details: CatModel
    var bundle = Bundle()
    private var searchJob: Job? = null

    override var idLayoutRes: Int = R.layout.fragment_products_by_id


    private val productsGridAdapter = ProductsGridByIdAdapter(productData = object :
        ProductsGridByIdAdapter.ProductItemListener {
        override fun itemClicked(productData: ProductsResponse.Data?) {
            val bundle2 = Bundle()
            bundle2.putParcelable("item", productData)
            Navigation.findNavController(mViewDataBinding.root)
                .navigate(R.id.action_productsById_to_detailsProductFragment, bundle2);
        }
    })


    val mViewModel: ProductsByIdViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel.navigator = this
        mViewDataBinding.productsViewModel = mViewModel
        mViewDataBinding.shimmerLayout.startShimmerAnimation()
        getData()
        initGridUI()
//        checkStatus()
        setupView()


    }

    private fun setupView() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            mViewModel.listData.collect {
                productsGridAdapter.submitData(it)
            }

        }


    }

    private fun getData() {
        bundle = this.requireArguments()
        details = bundle.getParcelable("cat")!!
        mViewModel.category_Id.value = details.id.toString()
        mViewModel.Lang.value = ChangeLanguage.getLanguage(requireContext())
    }

    private fun initGridUI() {

        mViewDataBinding.recyclerProductsGrid.setLayoutManager(
            LinearLayoutManager(
                requireContext()
            )
        )
        mViewDataBinding.recyclerProductsGrid.adapter = productsGridAdapter

        mViewDataBinding.recyclerProductsGrid.adapter =
            productsGridAdapter.withLoadStateFooter(footer = LoadStateViewHolder.LoadingStateAdapter {
                productsGridAdapter.retry()
            })

        productsGridAdapter.addLoadStateListener {
            val status = it.source.refresh is LoadState.Loading
            if (status) {
                mViewDataBinding.SwipCategories.isVisible = true
                mViewDataBinding.shimmerLayout.stopShimmerAnimation()
                mViewDataBinding.shimmerLayout.isVisible = false
            }

        }

    }

    private fun changeViewList() {
        if (linearView) {
            mViewDataBinding.recyclerProductsGrid.setLayoutManager(
                LinearLayoutManager(
                    requireContext()
                )
            )
            productsGridAdapter.type = 0
        } else {
            mViewDataBinding.recyclerProductsGrid.setLayoutManager(
                GridLayoutManager(
                    requireContext(), 2
                )
            )
            productsGridAdapter.type = 1
        }
    }

    override fun onClickGrid() {
        selectGrid()
        unselectLinear()
        changeViewList()

    }

    override fun onClickLinear() {
        selectLinear()
        unselectGrid()
        changeViewList()
    }

    fun selectGrid() {
        linearView = false
        mViewDataBinding.BtnGrid.setImageResource(R.drawable.ic_grid)
        mViewDataBinding.RelaGrid.setBackgroundResource(R.drawable.bc_righttoggle)
    }

    fun unselectGrid() {
        mViewDataBinding.BtnGrid.setImageResource(R.drawable.ic_nongrid)
        mViewDataBinding.RelaGrid.setBackgroundColor(
            Color.TRANSPARENT
        )
    }

    fun selectLinear() {
        linearView = true
        mViewDataBinding.ImgLinear.setImageResource(R.drawable.ic_linearselect)
        mViewDataBinding.RelaLinear.setBackgroundResource(R.drawable.bc_lefttoggle)
    }

    fun unselectLinear() {
        mViewDataBinding.ImgLinear.setImageResource(R.drawable.ic_linearsnonelected)
        mViewDataBinding.RelaLinear.setBackgroundColor(
            Color.TRANSPARENT
        )

    }

    override fun onStop() {
        super.onStop()
        dismissLoading()
    }

}