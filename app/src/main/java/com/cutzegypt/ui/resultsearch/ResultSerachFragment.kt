package com.cutzegypt.ui.resultsearch

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cutzegypt.ChangeLanguage
import com.cutzegypt.R
import com.cutzegypt.adapter.LoadStateViewHolder
import com.cutzegypt.adapter.ProductsGridByIdAdapter
import com.cutzegypt.base.BaseFragment
import com.cutzegypt.data.remote.model.LocationModel
import com.cutzegypt.data.remote.model.MessageEvent
import com.cutzegypt.data.remote.model.ProductsResponse
import com.cutzegypt.databinding.FragmentResultSerachBinding
import com.cutzegypt.ui.bottomnavigate.BottomNavigateActivity
import com.cutzegypt.ui.detailsproduct.DetailsProductViewModel
import com.cutzegypt.ui.nointernet.NoInternertActivity
import com.cutzegypt.ui.productsbyId.ProductByIdNavigator
import com.cutzegypt.utils.SharedData
import com.cutzegypt.utils.Status
import com.cutzegypt.utils.isConnected
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResultSerachFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class ResultSerachFragment : BaseFragment<FragmentResultSerachBinding>(), ProductByIdNavigator,
    SwipeRefreshLayout.OnRefreshListener {
    var linearView = true
    var bundle = Bundle()
    private var searchJob: Job? = null
    var search:String?= String()
    private val productReviwesViewModel: DetailsProductViewModel by viewModels()
    override var idLayoutRes: Int = R.layout.fragment_result_serach
    private var data: SharedData? = null
    private var token: String? = String()
    private lateinit var detailsProduct: ProductsResponse.Data
    private lateinit var productsGridAdapter : ProductsGridByIdAdapter
    var location: LocationModel?= LocationModel()

    val mViewModel: ResultSerachViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel.navigator = this

        init()
        initAdapter()
        searchAutocComplete()
        getData()
        initGridUI()
        setupView()
        subscribeAddFavourit()
        subscriberemoveFavourit()
        subscribeChangesCatId()

    }

    private fun searchAutocComplete() {
        mViewDataBinding.ESearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                mViewModel.searchValue.value=mViewDataBinding.ESearch.text.toString()
                mViewDataBinding.TSearch.text=resources.getString(R.string.search_key)+" : "+mViewDataBinding.ESearch.text.toString()
                productsGridAdapter.retry()
                productsGridAdapter.refresh()
                mViewDataBinding.recyclerProductsGrid.visibility=View.GONE
            }
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length != 0) {
                    mViewModel.searchValue.value=mViewDataBinding.ESearch.text.toString()
                    mViewDataBinding.TSearch.text=resources.getString(R.string.search_key)+" : "+mViewDataBinding.ESearch.text.toString()
                    productsGridAdapter.retry()
                }else {
                    mViewModel.searchValue.value=mViewDataBinding.ESearch.text.toString()
                    mViewDataBinding.TSearch.text=""

                }
            }
        })
    }

    fun initAdapter(){
        productsGridAdapter = ProductsGridByIdAdapter(requireContext(), productData = object :
            ProductsGridByIdAdapter.ProductItemListener {
            override fun itemClicked(productData: ProductsResponse.Data?) {
                val bundle2 = Bundle()
                bundle2.putParcelable("item", productData)
                Navigation.findNavController(mViewDataBinding.root)
                    .navigate(R.id.action_resultSerachFragment_to_detailsProductFragment, bundle2);
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
    }


    private fun setupView() {
        searchJob?.cancel()

        searchJob = lifecycleScope.launch {
            mViewModel.listData.collect {
                productsGridAdapter.submitData(it)
            }
        }

    }

    private fun getData() {
        bundle = this.requireArguments()
        search = bundle.getString("search")!!
        mViewModel.searchValue.value=search
        mViewDataBinding.TSearch.text=resources.getString(R.string.search_key)+" : "+search
        mViewModel.Lang = ChangeLanguage.getLanguage(requireContext())
        mViewModel.userId.value= data?.getValue(SharedData.ReturnValue.STRING, "token")
        productsGridAdapter.token=data?.getValue(SharedData.ReturnValue.STRING, "token")
        var gsonData= Gson()
        var json:String?=data?.getValue(SharedData.ReturnValue.STRING, "json")
        location=gsonData.fromJson(json, LocationModel::class.java)

        mViewModel.lat.value= location?.lat
        mViewModel.lng.value= location?.lng
    }

    private fun initGridUI() {
        mViewDataBinding.recyclerProductsGrid.isMotionEventSplittingEnabled=false
        mViewDataBinding.recyclerProductsGrid.setLayoutManager(LinearLayoutManager(requireContext()))
        mViewDataBinding.recyclerProductsGrid.adapter = productsGridAdapter
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

    private fun checkEmpryProduct(it: CombinedLoadStates){
        if (it.source.refresh is LoadState.Error && productsGridAdapter.itemCount==0){
            mViewDataBinding.toggleButton.visibility=View.GONE
            showEmptyPage()
        }
    }


    fun showEmptyPage(){
        mViewDataBinding.RelaEmpty.visibility=View.VISIBLE
    }
    fun hideEmptyPage(){
        mViewDataBinding.RelaEmpty.visibility=View.INVISIBLE
        mViewDataBinding.recyclerProductsGrid.visibility=View.VISIBLE

    }

    override fun onStart() {
        super.onStart()
        productsGridAdapter.type = 0
    }

    private fun changeViewList() {
        if (linearView) {
            mViewDataBinding.recyclerProductsGrid.setLayoutManager(
                LinearLayoutManager(
                    requireContext()
                )
            )
            productsGridAdapter.type = 0
        } else {
            mViewDataBinding.recyclerProductsGrid.setLayoutManager(
                GridLayoutManager(
                    requireContext(), 2
                )
            )
            productsGridAdapter.type = 1
        }
    }

    override fun onClickGrid() {
        selectGrid()
        unselectLinear()
        changeViewList()

    }

    override fun onClickLinear() {
        selectLinear()
        unselectGrid()
        changeViewList()
    }

    override fun onClickFilter() {
//        val newDialogFragment = FiltertionFragment()
//        val transaction: FragmentTransaction =
//            requireActivity().supportFragmentManager.beginTransaction()
//        newDialogFragment.show(transaction, "New_Dialog_Fragment")
    }

    override fun openHome() {
        val intent= Intent(requireContext(), BottomNavigateActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    fun selectGrid() {
        linearView = false
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
        linearView = true
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
        productsGridAdapter.type = 0
    }

    override fun onResume() {
        super.onResume()
        productsGridAdapter.type = 0
    }

    override fun onPause() {
        super.onPause()
        productsGridAdapter.type = 0
    }

    override fun onRefresh() {
        mViewDataBinding.SwipCategories.isRefreshing = false

        refreshData()
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

    private fun subscribeChangesCatId() {
        mViewModel.checkChanges.observe(viewLifecycleOwner, Observer {
            if (it) {
                productsGridAdapter.refresh()
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

    fun checkIsFavourit(postion: Int) {
        if (postion==1) {
            token?.let { it1 -> productReviwesViewModel.removeFavourit(
                detailsProduct.id.toString(),
                it1
            ) }
        } else {
            token?.let { it1 -> productReviwesViewModel.addFavourit(
                detailsProduct.id.toString(),
                it1
            ) }
        }

    }


    @Subscribe(sticky = false, threadMode = ThreadMode.BACKGROUND)
    fun onMessageEvent(messsg: MessageEvent) {/* Do something */
        if(messsg.Message.equals("login")){
            productsGridAdapter.token=data?.getValue(SharedData.ReturnValue.STRING, "token")
        }
        if(messsg.Message.equals("network")){
            refreshData()
        }
    };
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

    }




    fun checkInternet(){
        if(!requireContext().isConnected){
            startActivity(Intent(requireContext(), NoInternertActivity::class.java))
        }
    }
    fun refreshData(){
        if(requireContext().isConnected){
            productsGridAdapter.refresh()
        }else {
            startActivity(Intent(requireContext(), NoInternertActivity::class.java))
        }
    }



}