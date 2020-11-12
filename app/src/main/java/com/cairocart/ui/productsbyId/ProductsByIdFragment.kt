package com.cairocart.ui.productsbyId

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cairocart.ChangeLanguage
import com.cairocart.R
import com.cairocart.adapter.ProductsByIdAdapter
import com.cairocart.adapter.ProductsGridByIdAdapter
import com.cairocart.base.BaseFragment
import com.cairocart.data.remote.model.CatModel
import com.cairocart.data.remote.model.ProductsByIdResponse
import com.cairocart.databinding.FragmentProductsByIdBinding
import com.cairocart.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsById : BaseFragment<FragmentProductsByIdBinding>(), ProductByIdNavigator {

    var checkStatus = 0
    var page=1
    override var idLayoutRes: Int = R.layout.fragment_products_by_id
    private val productsAdapter = ProductsByIdAdapter(object :
        ProductsByIdAdapter.ProductItemListener {
        override fun itemClicked(productData: ProductsByIdResponse.Data) {
            val bundle2 = Bundle()
            bundle2.putParcelable("item", productData)
            Navigation.findNavController(mViewDataBinding.root)
                .navigate(R.id.action_productsById_to_detailsProductFragment,bundle2);
        }

    })
    private val productsGridAdapter = ProductsGridByIdAdapter()
    lateinit var details: CatModel
    var bundle= Bundle()

    val mViewModel: ProductsByIdViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel.navigator = this
        mViewDataBinding.productsViewModel = mViewModel
        getData()
        getProducts()
        productsObserver()

    }

    private fun getData() {
        bundle=this.requireArguments()
        details= bundle.getParcelable("cat")!!
    }

    private fun checkStatus() {
        if (checkStatus == 0) {
            initLinearUI()
        } else {
            initGridUI()
        }
    }

    private fun getProducts() {
        mViewModel.getProductsById(ChangeLanguage.getLanguage(requireContext()), details.id.toString(),page.toString())
    }

    private fun initLinearUI() {
        mViewDataBinding.recyclerProducts.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recyclerProducts.adapter = productsAdapter
    }

    private fun initGridUI() {
        mViewDataBinding.recyclerProducts.setLayoutManager(GridLayoutManager(requireContext(), 2))
        mViewDataBinding.recyclerProducts.adapter = productsGridAdapter
    }

    private fun productsObserver() {
        mViewModel.productResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    addData(it.data as MutableList<ProductsByIdResponse.Data>)
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

    private fun addData(data: MutableList<ProductsByIdResponse.Data>) {
        checkStatus()
        productsAdapter.setDeveloperList(data)
        productsGridAdapter.setDeveloperList(data)


    }

    override fun onClickGrid() {
        selectGrid()
        unselectLinear()
        getProducts()
    }

    override fun onClickLinear() {
        selectLinear()
        unselectGrid()
        getProducts()
    }

    fun selectGrid() {
        checkStatus = 1
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
        checkStatus = 0
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