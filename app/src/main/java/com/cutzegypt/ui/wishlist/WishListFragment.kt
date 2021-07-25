package com.cutzegypt.ui.wishlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cutzegypt.R
import com.cutzegypt.adapter.WishlistAdapter
import com.cutzegypt.base.BaseFragment
import com.cutzegypt.data.remote.model.ProductsResponse
import com.cutzegypt.databinding.FragmentWishListBinding
import com.cutzegypt.ui.detailsproduct.DetailsProductViewModel
import com.cutzegypt.utils.SharedData
import com.cutzegypt.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WishListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class WishListFragment : BaseFragment<FragmentWishListBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    override var idLayoutRes: Int = R.layout.fragment_wish_list

    var linearView = true
    var bundle = Bundle()
    private var searchJob: Job? = null
    private val productReviwesViewModel: DetailsProductViewModel by viewModels()
    private var data: SharedData? = null
    private var token: String? = String()
    private lateinit var detailsProduct: ProductsResponse.Data
    private lateinit var productsGridAdapter : WishlistAdapter




    val mViewModel: WishListViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        initAdapter()
        initGridUI()
        setupView()
        subscriberemoveFavourit()
        subscribeListFavourit()


    }

    fun initAdapter() {
        productsGridAdapter = WishlistAdapter(productData = object :
            WishlistAdapter.ProductItemListener {
            override fun itemClicked(productData: ProductsResponse.Data?) {
                val bundle2 = Bundle()
                bundle2.putParcelable("item", productData)
                Navigation.findNavController(mViewDataBinding.root)
                    .navigate(R.id.action_wishListFragment_to_detailsProductFragment, bundle2);
            }

            override fun itemFavourit(productData: ProductsResponse.Data?) {
                detailsProduct = productData!!
                token = data?.getValue(SharedData.ReturnValue.STRING, "token")
                checkIsFavourit()
            }
        })

    }

    private fun init() {
        mViewDataBinding.productsViewModel = mViewModel
        mViewDataBinding.shimmerLayout.startShimmerAnimation()
        mViewDataBinding.SwipCategories.setOnRefreshListener(this)
        data = SharedData(requireContext())
        token=data?.getValue(SharedData.ReturnValue.STRING, "token")
    }


    private fun setupView() {
            mViewModel.getCart(token)
    }

    private fun initGridUI() {

        mViewDataBinding.recyclerProductsGrid.isMotionEventSplittingEnabled=false
        mViewDataBinding.recyclerProductsGrid.setLayoutManager(LinearLayoutManager(requireContext()))
        mViewDataBinding.recyclerProductsGrid.adapter = productsGridAdapter

    }



    fun showEmptyPage(){
        mViewDataBinding.RelaEmpty.visibility= View.VISIBLE
        mViewDataBinding.recyclerProductsGrid.visibility=View.GONE

    }
    fun hideEmptyPage(){
        mViewDataBinding.recyclerProductsGrid.visibility=View.VISIBLE
        mViewDataBinding.RelaEmpty.visibility= View.INVISIBLE
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
                    mViewModel.getCart(token)
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
    private fun subscribeListFavourit() {
        mViewModel.wishlistResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    hideEmptyPage()
                   setData(it.data?.data as MutableList<ProductsResponse.Data>)
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

    private fun setData(data: MutableList<ProductsResponse.Data>) {
        if(data!!.size>0){
            mViewDataBinding.recyclerProductsGrid.visibility=View.VISIBLE
            productsGridAdapter.setDeveloperList(data)
        }else {
            showEmptyPage()
        }

    }


    fun checkIsFavourit() {
            token?.let { it1 ->
                productReviwesViewModel.removeFavourit(
                    detailsProduct.id.toString(),
                    it1
                )
            }
    }

    override fun onRefresh() {
        mViewModel.getCart(token)
    }


}