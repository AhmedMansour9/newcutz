package com.hadrmout.ui.productcollection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hadrmout.R
import com.hadrmout.adapter.ProductCollectionAdapter
import com.hadrmout.base.BaseFragment
import com.hadrmout.data.remote.model.CollectionsResponse
import com.hadrmout.data.remote.model.ProductsResponse
import com.hadrmout.databinding.FragmentProductCollectionBinding
import com.hadrmout.ui.detailsproduct.DetailsProductFragment
import com.hadrmout.ui.detailsproduct.DetailsProductViewModel
import com.hadrmout.ui.recipes.ReciepesViewModel
import com.hadrmout.utils.SharedData
import com.hadrmout.utils.Status
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductCollection.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class ProductCollection : BaseFragment<FragmentProductCollectionBinding>(){
    override var idLayoutRes: Int = R.layout.fragment_product_collection
    private lateinit var dataproduct:CollectionsResponse.Data
    private lateinit var productsGridAdapter: ProductCollectionAdapter
    private var data: SharedData? = null
    private var token: String? = String()
    var bundle = Bundle()
    private val productReviwesViewModel: DetailsProductViewModel by viewModels()
    private lateinit var detailsProduct: ProductsResponse.Data

    val mViewModel: ReciepesViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        data = SharedData(requireContext())
       getData()
        initAdapter()
        initUI()
        subscribeAddFavourit()
        subscriberemoveFavourit()

    }
    private fun getData() {
        token = data?.getValue(SharedData.ReturnValue.STRING, "token")
        bundle = this.requireArguments()
        dataproduct = bundle.getParcelable("data")!!
        if(dataproduct.products.size == 0){
            mViewDataBinding.RelaEmpty.visibility=View.VISIBLE
        }

    }
    fun initAdapter() {
        productsGridAdapter = ProductCollectionAdapter(dataproduct.products, productData = object :
            ProductCollectionAdapter.ProductItemListener {
            override fun itemClicked(productData: ProductsResponse.Data?) {
                val bundle = Bundle()
                bundle.putParcelable("item", productData)
                val newDialogFragment = DetailsProductFragment()
                newDialogFragment.arguments = bundle
                val transaction: FragmentTransaction =
                    requireActivity().supportFragmentManager.beginTransaction()
                newDialogFragment.show(transaction, "New_Dialog_Fragment")
            }

            override fun itemFavourit(productData: ProductsResponse.Data?) {
                detailsProduct=productData!!
                checkIsFavourit(detailsProduct?.isFavoirte!!)

            }

        })
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
    private fun initUI() {
        mViewDataBinding.recyclerProductsGrid.isMotionEventSplittingEnabled = false
        mViewDataBinding.recyclerProductsGrid.setLayoutManager(LinearLayoutManager(requireContext()))
        mViewDataBinding.recyclerProductsGrid.adapter = productsGridAdapter
    }

    fun checkIsFavourit(postion: Boolean) {
        if (postion) {
            token?.let { it1 -> productReviwesViewModel.removeFavourit(detailsProduct.id.toString(), it1) }
        } else {
            token?.let { it1 -> productReviwesViewModel.addFavourit(detailsProduct.id.toString(), it1) }
        }

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


}