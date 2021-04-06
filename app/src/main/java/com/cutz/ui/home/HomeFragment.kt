package com.cutz.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cutz.ChangeLanguage
import com.cutz.R
import com.cutz.adapter.CollectionsAapter
import com.cutz.adapter.GridSections_Adapter
import com.cutz.base.BaseFragment
import com.cutz.data.remote.model.SectonsResponse
import com.cutz.databinding.FragmentHomeBinding
import com.cutz.ui.nointernet.NoInternertActivity
import com.cutz.ui.resultsearch.ResultSerachFragment
import com.cutz.utils.SharedData
import com.cutz.utils.Status
import com.cutz.utils.isConnected
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

    private  var catAdapter= GridSections_Adapter(object : GridSections_Adapter.CategoryItemListener{
        override fun itemClicked(productData: SectonsResponse.Data) {
            val bundle = Bundle()
            bundle.putParcelable("data", productData)
            bundle.putString("type","section")
            Navigation.findNavController(mViewDataBinding.root)
                .navigate(R.id.action_homeFragment_to_T_Categories, bundle);
        }
    })

    private  var collectionsAdapter= CollectionsAapter(object : CollectionsAapter.CategoryItemListener{
        override fun itemClicked(productData: SectonsResponse.Data) {
            val bundle = Bundle()
            bundle.putParcelable("data", productData)
            bundle.putString("type","collection")
            Navigation.findNavController(mViewDataBinding.root)
                .navigate(R.id.action_homeFragment_to_T_Categories, bundle);
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycle()
        init()
        categoryObserver()
        onClickSearch()
    }

    private fun onClickSearch() {
        mViewDataBinding.ESearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (!mViewDataBinding.ESearch.text.toString().isNullOrEmpty()) {
                    var productsByid = ResultSerachFragment()
                    val bundle = Bundle()
                    bundle.putString("search", mViewDataBinding.ESearch.text.toString())
                    productsByid.arguments = bundle
                    Navigation.findNavController(mViewDataBinding.root).navigate(R.id.action_homeFragment_to_resultSerachFragment,bundle);
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