package com.hadrmout.ui.offers

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hadrmout.R
import com.hadrmout.adapter.GridProductsAdapter
import com.hadrmout.base.BaseFragment
import com.hadrmout.data.remote.model.LocationModel
import com.hadrmout.data.remote.model.ProductsResponse
import com.hadrmout.databinding.FragmentOffersBinding
import com.hadrmout.ui.detailsproduct.DetailsProductViewModel
import com.hadrmout.utils.SharedData
import com.hadrmout.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

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
    private lateinit var productsGridAdapter: GridProductsAdapter
    var location: LocationModel? = LocationModel()
    private var Type: String? = String()

    val mViewModel: OffersViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        initAdapter()
        initGridUI()
        subscriberemoveFavourit()
        subscribeAddFavourit()
        subscriberegetOffers()
        getData()

    }

    private fun getData() {
        Type?.let { mViewModel.getProductsOffers(token!!, it) }
    }

    private fun subscriberegetOffers() {
        mViewModel.productsResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    setData(it.data)
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

    private fun setData(data: ProductsResponse?) {
        if (data != null)
            productsGridAdapter.setDeveloperList(data.data)
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
    fun initAdapter() {
        productsGridAdapter = GridProductsAdapter(requireContext(), productData = object :
            GridProductsAdapter.ProductItemListener {
            override fun itemClicked(productData: ProductsResponse.Data?) {
                val bundle2 = Bundle()
                bundle2.putParcelable("item", productData)
                Navigation.findNavController(mViewDataBinding.root)
                    .navigate(R.id.action_offersFragment_to_detailsProductFragment, bundle2);
            }

            override fun itemFavourit(productData: ProductsResponse.Data?) {
                detailsProduct = productData!!
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
        token = data?.getValue(SharedData.ReturnValue.STRING, "token")
        Type = data?.getValue(SharedData.ReturnValue.STRING, "type")

    }




    private fun initGridUI() {
        mViewDataBinding.recyclerProductsGrid.isMotionEventSplittingEnabled = false
        mViewDataBinding.recyclerProductsGrid.setLayoutManager(
            GridLayoutManager(
                requireContext(), 2
            )
        )
        mViewDataBinding.recyclerProductsGrid.adapter = productsGridAdapter


    }



    fun showEmptyPage(){
        mViewDataBinding.RelaEmpty.visibility= View.VISIBLE
    }
    fun hideEmptyPage(){
        mViewDataBinding.RelaEmpty.visibility= View.INVISIBLE
    }



    override fun onRefresh() {
        mViewDataBinding.SwipCategories.isRefreshing = false
        Type?.let { mViewModel.getProductsOffers(token!!, it) }
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
                    Type?.let { mViewModel.getProductsOffers(token!!, it) }

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


    fun checkIsFavourit(postion: Boolean) {
        if (postion) {
            token?.let { it1 ->
                productReviwesViewModel.removeFavourit(
                    detailsProduct.id.toString(),
                    it1
                )
            }
        } else {
            token?.let { it1 ->
                productReviwesViewModel.addFavourit(
                    detailsProduct.id.toString(),
                    it1
                )
            }
        }

    }


}