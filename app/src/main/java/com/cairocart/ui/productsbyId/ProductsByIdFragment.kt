package com.cairocart.ui.productsbyId

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.cairocart.ChangeLanguage
import com.cairocart.R
import com.cairocart.adapter.ProductsByIdAdapter
import com.cairocart.base.BaseFragment
import com.cairocart.data.remote.model.ProductsByIdResponse
import com.cairocart.databinding.FragmentProductsByIdBinding
import com.cairocart.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsById : BaseFragment<FragmentProductsByIdBinding>(), ProductByIdNavigator {

    override var idLayoutRes: Int = R.layout.fragment_products_by_id
    private val productsAdapter = ProductsByIdAdapter()
    val mViewModel: ProductsByIdViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel.navigator = this
        mViewDataBinding.productsViewModel = mViewModel
        initUI()
        getProducts()
        productsObserver()

    }

    private fun getProducts() {
        mViewModel.getProductsById(ChangeLanguage.getLanguage(requireContext()), "109")
    }

    private fun initUI() {
        mViewDataBinding.recyclerProducts.setHasFixedSize(true)
        mViewDataBinding.recyclerProducts.adapter = productsAdapter
    }


    private fun productsObserver() {
        mViewModel.productResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    addData(it.data)
                }
                Status.LOADING -> {
                    showLoading()
                }

                Status.ERROR -> {
                    dismissLoading()
                }
            }
        })
    }

    private fun addData(data: List<ProductsByIdResponse.Data?>?) {
        productsAdapter.setDeveloperList(data as MutableList<ProductsByIdResponse.Data>)


    }

    override fun onClickGrid() {
        selectGrid()
        unselectLinear()

    }

    override fun onClickLinear() {
        selectLinear()
        unselectGrid()
    }

    fun selectGrid() {
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
        mViewDataBinding.ImgLinear.setImageResource(R.drawable.ic_linearselect)
        mViewDataBinding.RelaLinear.setBackgroundResource(R.drawable.bc_lefttoggle)
    }

    fun unselectLinear() {
        mViewDataBinding.ImgLinear.setImageResource(R.drawable.ic_linearsnonelected)
        mViewDataBinding.RelaLinear.setBackgroundColor(
            Color.TRANSPARENT
        )

    }

}