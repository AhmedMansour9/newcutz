package com.hadrmout.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hadrmout.ChangeLanguage
import com.hadrmout.R
import com.hadrmout.adapter.CollectionsAapter
import com.hadrmout.adapter.GridSections_Adapter
import com.hadrmout.adapter.RelatedProductAdapter
import com.hadrmout.base.BaseFragment
import com.hadrmout.data.remote.model.*
import com.hadrmout.databinding.FragmentHomeBinding
import com.hadrmout.ui.detailsproduct.DetailsProductFragment
import com.hadrmout.ui.nointernet.NoInternertActivity
import com.hadrmout.ui.offers.OffersViewModel
import com.hadrmout.ui.resultsearch.ResultSerachFragment
import com.hadrmout.utils.SharedData
import com.hadrmout.utils.Status
import com.hadrmout.utils.isConnected
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class HomeFragment  : BaseFragment<FragmentHomeBinding>() ,SwipeRefreshLayout.OnRefreshListener {

    override var idLayoutRes: Int = R.layout.fragment_home
    private var data: SharedData? = null
    val mViewModel: HomeViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }
    private lateinit var token: String
    lateinit var productsGridAdapter: RelatedProductAdapter
    var status: String = "restaurant"
    val mViewModelOffers: OffersViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }
    var location: LocationModel? = LocationModel()
    private var catAdapter =
        GridSections_Adapter {
            val bundle = Bundle()
            bundle.putParcelable("data", it)
            bundle.putString("type", "section")
            Navigation.findNavController(mViewDataBinding.root)
                .navigate(R.id.action_homeFragment_to_productById, bundle);
        }

    private var collectionsAdapter = CollectionsAapter(object :
        CollectionsAapter.CategoryItemListener {
        override fun itemClicked(productData: ProductsResponse.Data) {

            val newDialogFragment = DetailsProductFragment()
            val bundle = Bundle()
            bundle.putParcelable("item", productData)
            newDialogFragment.setArguments(bundle)
            val transaction: FragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()
            newDialogFragment.show(transaction, "New_Dialog_Fragment")
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycle()
        init()
        onClickButtons()
        initAdapter()
        callApis()
        categoryObserver()
        subscriberegetOffers()
        subscriberegetBestProducts()
    }
    private fun hidePromo(){
        mViewDataBinding.promo.visibility=View.GONE
        mViewDataBinding.recyclerCollections.visibility=View.GONE
    }

    private fun showPromo(){
        mViewDataBinding.promo.visibility=View.VISIBLE
        mViewDataBinding.recyclerCollections.visibility=View.VISIBLE
    }

    private fun callApis() {
        mViewModel.getCategory(
            ChangeLanguage.getLanguage(requireContext()),
            "Bearer $token",
            status
        )
        getOffers()
        getBestProducts()

    }

    private fun subscriberegetBestProducts() {
        mViewModel.collectionResponse.observe(viewLifecycleOwner, Observer {
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


    private fun getOffers() {
        status.let { mViewModelOffers.getProductsOffers(token, it) }

    }

    private fun subscriberegetOffers() {
        mViewModelOffers.productsResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    if(it.data?.data !=null){
                        it.data?.let { it1 -> setData(it1.data) }
                    }else {
                        hidePromo()
                    }
                    dismissLoading()

                }
                Status.LOADING -> {
                    showLoading()

                }

                Status.ERROR -> {
                    dismissLoading()
                    hidePromo()
                }
            }
        })
    }

    private fun setData(data: ProductsResponse?) {
        if (data != null)
            collectionsAdapter.setList(data.data)
    }

    private fun onClickButtons() {
        mViewDataBinding.btnRestaurant.setOnClickListener() {
            mViewDataBinding.btnRestaurant.setBackgroundResource(R.drawable.bc_darkred)
            mViewDataBinding.btnRestaurant.setTextColor(resources.getColor(R.color.white))
            mViewDataBinding.btnButcher.setBackgroundColor(Color.TRANSPARENT)
            mViewDataBinding.btnButcher.setTextColor(resources.getColor(R.color.yellow))
            status = "restaurant";
            data?.putValue("type", status)
            callApis()
        }
        mViewDataBinding.btnButcher.setOnClickListener() {
            mViewDataBinding.btnButcher.setBackgroundResource(R.drawable.bc_darkred)
            mViewDataBinding.btnButcher.setTextColor(resources.getColor(R.color.white))
            mViewDataBinding.btnRestaurant.setBackgroundColor(Color.TRANSPARENT)
            mViewDataBinding.btnRestaurant.setTextColor(resources.getColor(R.color.yellow))
            status = "butchery";
            data?.putValue("type", "butchery")
            callApis()
        }
    }

    fun initAdapter() {
        productsGridAdapter = RelatedProductAdapter(productData = object :
            RelatedProductAdapter.ProductItemListener {
            override fun itemClicked(productData: ProductsResponse.Data?) {
                val newDialogFragment = DetailsProductFragment()
                val bundle = Bundle()
                bundle.putParcelable("item", productData)
                newDialogFragment.setArguments(bundle)
                val transaction: FragmentTransaction =
                    requireActivity().supportFragmentManager.beginTransaction()
                newDialogFragment.show(transaction, "New_Dialog_Fragment")
            }

        })

    }



    private fun setData(mutableList: MutableList<ProductsResponse.Data>) {
//        mViewDataBinding.TRelated.visibility = View.VISIBLE

        if (mutableList.size > 0) {
            showPromo()
            mViewDataBinding.recyclerBestSeller.visibility = View.VISIBLE
            mViewDataBinding.best.visibility = View.VISIBLE
            mViewDataBinding.recyclerBestSeller.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            mViewDataBinding.recyclerBestSeller.adapter = productsGridAdapter
            productsGridAdapter.setDeveloperList(mutableList)
        }
    }

    private fun getBestProducts() {
        mViewModel.getCollections(token, status)
    }





    private fun initRecycle() {
        mViewDataBinding.recyclerCategories.setHasFixedSize(true)
        mViewDataBinding.recyclerCategories.layoutManager =
         LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false )
        mViewDataBinding.recyclerCategories.adapter = catAdapter

        mViewDataBinding.recyclerCollections.setHasFixedSize(true)
        mViewDataBinding.recyclerCollections.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        mViewDataBinding.recyclerCollections.adapter = collectionsAdapter

    }
    private fun init() {
        mViewModel.Lang.value = ChangeLanguage.getLanguage(requireContext())
        mViewDataBinding.SwipHome.setOnRefreshListener(this)
        data = SharedData(requireContext())
        data?.putValue("type", "restaurant")
        token = data?.getValue(SharedData.ReturnValue.STRING, "token")!!

    }

    private fun categoryObserver() {
        mViewModel.categoryResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    addData(it.data?.data as MutableList<SectonsResponse.Data>)
                }
                Status.LOADING -> {
                    showLoading()
                }
                Status.ERROR -> {
                    dismissLoading ()
                }
            }
        })
    }


    private fun addData(data: MutableList<SectonsResponse.Data>) {
        catAdapter.setList(data)
    }



//    private fun collectionsObserver() {
//        mViewModel.collectionResponse.observe(viewLifecycleOwner, Observer {
//            when (it.staus) {
//                Status.SUCCESS -> {
//                    dismissLoading()
//                    addDataCollection(it.data?.data as MutableList<CollectionsResponse.Data>)
//                }
//                Status.LOADING -> {
//                    showLoading()
//                }
//                Status.ERROR -> {
//                    dismissLoading ()
//                }
//            }
//        })
//    }
//
//
//
//    private fun addDataCollection(data: MutableList<CategoriesResponse.Data>) {
//    }


//    override fun itemClicked(node: CatModel) {
//             val bundle = Bundle()
//            bundle.putParcelable("cat", node)
//            Navigation.findNavController(mViewDataBinding.root)
//                .navigate(R.id.action_T_Categories_to_productsById, bundle);
//    }

    override fun onRefresh() {
        mViewDataBinding.SwipHome.isRefreshing = false
        if (requireContext().isConnected) {
//            mViewModel.getCategory()
            mViewModel.getCollections(token, status)
        } else {
            startActivity(Intent(requireContext(), NoInternertActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        checkInternet()
    }

    fun checkInternet(){
        if(!requireContext().isConnected){
            startActivity(Intent(requireContext(), NoInternertActivity::class.java))
        }
    }

}