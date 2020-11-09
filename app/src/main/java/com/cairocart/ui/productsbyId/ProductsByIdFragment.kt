package com.cairocart.ui.productsbyId

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cairocart.ChangeLanguage
import com.cairocart.R
import com.cairocart.adapter.ProductsByIdAdapter
import com.cairocart.base.BaseFragment
import com.cairocart.data.remote.model.ProductsByIdResponse
import com.cairocart.databinding.FragmentProductsByIdBinding
import com.cairocart.ui.category.CategoryViewModel
import com.cairocart.utils.Resource
import com.cairocart.utils.Status
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductsById.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ProductsById : BaseFragment<FragmentProductsByIdBinding>(), ProductByIdNavigator {

    override var idLayoutRes: Int = R.layout.fragment_products_by_id
    private val productsAdapter =ProductsByIdAdapter()
    val mViewModel: ProductsByIdViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel.navigator = this
        mViewDataBinding.productsViewModel = mViewModel
        initUI()
        getProducts()
//        productsObserver()

    }

    private fun getProducts() {
        mViewModel.getProductsById(ChangeLanguage.getLanguage(requireContext()),"109")
    }

    private fun initUI() {
        mViewDataBinding.recyclerProducts.setHasFixedSize(true)
        mViewDataBinding.recyclerProducts.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.recyclerProducts.adapter = productsAdapter
    }


    private fun productsObserver() {
        mViewModel.productResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    addData(it.data?.data as List<ProductsByIdResponse.Data>)
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

    private fun addData(data: List<ProductsByIdResponse.Data>) {
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