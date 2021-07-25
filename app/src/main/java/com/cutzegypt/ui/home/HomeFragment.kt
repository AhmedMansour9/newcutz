package com.cutzegypt.ui.home

import android.annotation.SuppressLint
import android.content.Intent
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
import com.cutzegypt.ChangeLanguage
import com.cutzegypt.R
import com.cutzegypt.adapter.CollectionsAapter
import com.cutzegypt.adapter.GridSections_Adapter
import com.cutzegypt.adapter.RelatedProductAdapter
import com.cutzegypt.base.BaseFragment
import com.cutzegypt.data.remote.model.*
import com.cutzegypt.databinding.FragmentHomeBinding
import com.cutzegypt.ui.detailsproduct.DetailsProductFragment
import com.cutzegypt.ui.nointernet.NoInternertActivity
import com.cutzegypt.ui.resultsearch.ResultSerachFragment
import com.cutzegypt.utils.SharedData
import com.cutzegypt.utils.Status
import com.cutzegypt.utils.isConnected
import com.google.gson.Gson
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

    var location: LocationModel? = LocationModel()
    private var catAdapter =
        GridSections_Adapter(object : GridSections_Adapter.CategoryItemListener {
            override fun itemClicked(productData: SectonsResponse.Data) {
                val bundle = Bundle()
                bundle.putParcelable("data", productData)
                bundle.putString("type", "section")
                Navigation.findNavController(mViewDataBinding.root)
                    .navigate(R.id.action_homeFragment_to_T_Categories, bundle);
            }
        })

    private var collectionsAdapter = CollectionsAapter(object :
        CollectionsAapter.CategoryItemListener {
        override fun itemClicked(productData: SectonsResponse.Data) {
//            val bundle = Bundle()
//            bundle.putParcelable("data", productData)
//            bundle.putString("type", "collection")
//            Navigation.findNavController(mViewDataBinding.root)
//                .navigate(R.id.action_homeFragment_to_productCollection, bundle);

            val bundle = Bundle()
            bundle.putString("type", "sectionID")
            bundle.putParcelable("cat", productData)
            Navigation.findNavController(mViewDataBinding.root)
                .navigate(R.id.action_homeFragment_to_productById, bundle);
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycle()
        init()
        initAdapter()
        categoryObserver()
        onClickSearch()
        onClickLocation()
        getData()
        getRelatedProducts()
        subscribeRelatedProduct()
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

    private fun subscribeRelatedProduct() {
        mViewModel.relatedResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    setData(it.data?.data as MutableList<ProductsResponse.Data>)
                }
                Status.LOADING -> {

                }

                Status.ERROR -> {
                }
            }
        })

    }

    private fun setData(mutableList: MutableList<ProductsResponse.Data>) {
//        mViewDataBinding.TRelated.visibility = View.VISIBLE
        if (mutableList.size > 0) {
            mViewDataBinding.recyclerBestSeller.visibility = View.VISIBLE
            mViewDataBinding.best.visibility = View.VISIBLE
            mViewDataBinding.recyclerBestSeller.layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            mViewDataBinding.recyclerBestSeller.adapter = productsGridAdapter
            productsGridAdapter.setDeveloperList(mutableList)
        }
    }

    private fun getRelatedProducts() {
        mViewModel.getBestSeller(token)
    }

    @SuppressLint("SetTextI18n")
    private fun getData() {
        var gsonData = Gson()
        var json: String? = data?.getValue(SharedData.ReturnValue.STRING, "json")
        location = gsonData.fromJson(json, LocationModel::class.java)
        if (location?.area.isNullOrEmpty()) {
            var locationModel = LocationModel(
                "30.0084868000000000165528035722672939300537109375",
                "31.4284755999999987352566677145659923553466796875",
                "Tagamoa Hub"
            )
            val gson = Gson()
            val datajson: String = gson.toJson(locationModel)
            data?.putValue("json", datajson)
            mViewDataBinding.Slocation.text = "Tagamoa Hub"

        } else {
            mViewDataBinding.Slocation.text = location?.area.toString()
        }
    }

    private fun onClickLocation() {
        mViewDataBinding.EEditlocation.setOnClickListener() {
            Navigation.findNavController(mViewDataBinding.root)
                .navigate(R.id.action_homeFragment_to_changelocationFragment);

        }

    }

    private fun onClickSearch() {
        mViewDataBinding.ESearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (!mViewDataBinding.ESearch.text.toString().isNullOrEmpty()) {
                    var productsByid = ResultSerachFragment()
                    val bundle = Bundle()
                    bundle.putString("search", mViewDataBinding.ESearch.text.toString())
                    productsByid.arguments = bundle
                    Navigation.findNavController(mViewDataBinding.root)
                        .navigate(R.id.action_homeFragment_to_resultSerachFragment, bundle);
                }
                return@OnEditorActionListener true
            }
            false
        })
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
                LinearLayoutManager.VERTICAL,
                false )
        mViewDataBinding.recyclerCollections.adapter = collectionsAdapter

    }
    private fun init() {
        mViewModel.Lang.value = ChangeLanguage.getLanguage(requireContext())
        mViewDataBinding.SwipHome.setOnRefreshListener(this)
        data = SharedData(requireContext())
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
        collectionsAdapter.setList(data)
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
        mViewDataBinding.SwipHome.isRefreshing=false
        if(requireContext().isConnected){
            mViewModel.getCategory()
            mViewModel.getCollections(token)
        }else {
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