package com.cutzegypt.ui.offers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cutzegypt.R
import com.cutzegypt.adapter.LoadStateViewHolder
import com.cutzegypt.adapter.ProductsGridByIdAdapter
import com.cutzegypt.base.BaseFragment
import com.cutzegypt.data.remote.model.LocationModel
import com.cutzegypt.data.remote.model.ProductsResponse
import com.cutzegypt.databinding.FragmentOffersBinding
import com.cutzegypt.ui.detailsproduct.DetailsProductViewModel
import com.cutzegypt.utils.SharedData
import com.cutzegypt.utils.Status
import com.google.gson.Gson
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
 * Use the [OffersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class OffersFragment : BaseFragment<FragmentOffersBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    override var idLayoutRes: Int = R.layout.fragment_offers

    var linearView = true
    var bundle = Bundle()
    private var searchJob: Job? = null
    private val productReviwesViewModel: DetailsProductViewModel by viewModels()
    private var data: SharedData? = null
    private var token: String? = String()
    private lateinit var detailsProduct: ProductsResponse.Data
    private lateinit var productsGridAdapter : ProductsGridByIdAdapter
    var location: LocationModel?= LocationModel()


    val mViewModel: OffersViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        initAdapter()
        initGridUI()
        setupView()
        subscriberemoveFavourit()
        subscribeAddFavourit()

    }
    private fun subscribeAddFavourit() {
        productReviwesViewModel.addFavouritResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.add_favourit),
                        Toast.LENGTH_SHORT
                    ).show()
                    dismissLoading()
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
    fun initAdapter(){
        productsGridAdapter = ProductsGridByIdAdapter(requireContext(),productData = object :
            ProductsGridByIdAdapter.ProductItemListener {
            override fun itemClicked(productData: ProductsResponse.Data?) {
                val bundle2 = Bundle()
                bundle2.putParcelable("item", productData)
                Navigation.findNavController(mViewDataBinding.root)
                    .navigate(R.id.action_offersFragment_to_detailsProductFragment, bundle2);
            }
            override fun itemFavourit(productData: ProductsResponse.Data?) {
                detailsProduct=productData!!
                token = data?.getValue(SharedData.ReturnValue.STRING, "token")
                checkIsFavourit(detailsProduct.isFavoirte!!)
            }
        })
    }

    private fun init() {
        mViewDataBinding.productsViewModel = mViewModel
        mViewDataBinding.shimmerLayout.startShimmerAnimation()
        mViewDataBinding.SwipCategories.setOnRefreshListener(this)
        data = SharedData(requireContext())
        mViewModel.token.value=data?.getValue(SharedData.ReturnValue.STRING, "token")
        var gsonData= Gson()
        var json:String?=data?.getValue(SharedData.ReturnValue.STRING, "json")
        location=gsonData.fromJson(json, LocationModel::class.java)

        mViewModel.lat.value= location?.lat
        mViewModel.lng.value= location?.lng
    }


    private fun setupView() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            mViewModel.listData.collect {
                productsGridAdapter.submitData(it)
            }
        }


    }

    private fun initGridUI() {

        mViewDataBinding.recyclerProductsGrid.isMotionEventSplittingEnabled=false
        mViewDataBinding.recyclerProductsGrid.setLayoutManager(LinearLayoutManager(requireContext()))
        mViewDataBinding.recyclerProductsGrid.adapter = productsGridAdapter
//        productsGridAdapter.type = 1
        mViewDataBinding.recyclerProductsGrid.adapter =
            productsGridAdapter.withLoadStateFooter(footer = LoadStateViewHolder.LoadingStateAdapter {
                productsGridAdapter.retry()
            })

        productsGridAdapter.addLoadStateListener {
            val status = it.source.refresh is LoadState.Loading
            if (status) {
                hideEmptyPage()
                showLoading()
            } else {
                dismissLoading()
                checkEmpryProduct(it)
            }

        }

    }
    private fun checkEmpryProduct(it : CombinedLoadStates){
        if (it.source.refresh is LoadState.Error && productsGridAdapter.itemCount==0){
            showEmptyPage()
        }
    }


    fun showEmptyPage(){
        mViewDataBinding.RelaEmpty.visibility= View.VISIBLE
    }
    fun hideEmptyPage(){
        mViewDataBinding.RelaEmpty.visibility= View.INVISIBLE
    }

    override fun onResume() {
        super.onResume()
        productsGridAdapter.type = 0
    }

    override fun onPause() {
        super.onPause()
        productsGridAdapter.type = 0
    }


    override fun onStop() {
        super.onStop()
        productsGridAdapter.type = 0
    }

    override fun onRefresh() {
        mViewDataBinding.SwipCategories.isRefreshing = false
        productsGridAdapter.refresh()
    }


    private fun subscriberemoveFavourit() {
        productReviwesViewModel.removeFavouritResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.remove_favourit),
                        Toast.LENGTH_SHORT
                    ).show()
                    dismissLoading()
                    productsGridAdapter.refresh()
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

    fun checkIsFavourit(postion: Int) {
        if (postion==1) {
            token?.let { it1 -> productReviwesViewModel.removeFavourit(detailsProduct.id.toString(), it1) }
        } else {
            token?.let { it1 -> productReviwesViewModel.addFavourit(detailsProduct.id.toString(), it1) }
        }

    }


}