package com.cutz.ui.productsbyId

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
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
import com.cutz.R
import com.cutz.adapter.LoadStateViewHolder
import com.cutz.adapter.ProductsGridByIdAdapter
import com.cutz.base.BaseFragment
import com.cutz.data.remote.model.SectonsResponse
import com.cutz.data.remote.model.MessageEvent
import com.cutz.data.remote.model.ProductsResponse
import com.cutz.databinding.FragmentProductsByIdBinding
import com.cutz.ui.bottomnavigate.BottomNavigateActivity
import com.cutz.ui.detailsproduct.DetailsProductFragment
import com.cutz.ui.nointernet.NoInternertActivity
import com.cutz.ui.detailsproduct.DetailsProductViewModel
import com.cutz.ui.resultsearch.ResultSerachFragment
import com.cutz.utils.SharedData
import com.cutz.utils.Status
import com.cutz.utils.isConnected
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@AndroidEntryPoint
class ProductsById : BaseFragment<FragmentProductsByIdBinding>(), ProductByIdNavigator,
    SwipeRefreshLayout.OnRefreshListener {
    var linearView = true
    lateinit var details: SectonsResponse.Data.Category.SubCategory
    lateinit var detailsparts: SectonsResponse.Data.Category.Parts

    var bundle = Bundle()
    private var searchJob: Job? = null
    private val productReviwesViewModel: DetailsProductViewModel by viewModels()
    override var idLayoutRes: Int = R.layout.fragment_products_by_id
    private var data: SharedData? = null
    private var token: String? = String()
    private var type: String? = String()

    private lateinit var detailsProduct: ProductsResponse.Data
    private lateinit var productsGridAdapter : ProductsGridByIdAdapter

    val mViewModel: ProductsByIdViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel.navigator = this

        init()
        initAdapter()
        getData()
        initGridUI()
        setupView()
        subscribeAddFavourit()
        subscriberemoveFavourit()
        subscribeChangesCatId()
        subscribeFiltertion()
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
                    Navigation.findNavController(mViewDataBinding.root).navigate(R.id.action_productsById_to_resultSerachFragment,bundle);
                }
                return@OnEditorActionListener true
            }
            false
        })
    }
   fun initAdapter(){
         productsGridAdapter = ProductsGridByIdAdapter(requireContext(),productData = object :
            ProductsGridByIdAdapter.ProductItemListener {
            override fun itemClicked(productData: ProductsResponse.Data?) {

                val newDialogFragment = DetailsProductFragment()
                val bundle = Bundle()
                bundle.putParcelable("item",productData)
                newDialogFragment.setArguments(bundle)
                val transaction: FragmentTransaction =
                    requireActivity().supportFragmentManager.beginTransaction()
                newDialogFragment.show(transaction, "New_Dialog_Fragment")

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
        type=bundle.getString("type")
        mViewModel.type.value=type
        if(type.equals("sub")){
            details = bundle.getParcelable("cat")!!
            mViewModel.Id.value=details.id.toString()
            mViewDataBinding.TName.text=details.title
        }else {
            detailsparts = bundle.getParcelable("cat")!!
            mViewModel.Id.value=detailsparts.id.toString()
            mViewDataBinding.TName.text=detailsparts.title
        }

//        mViewModel.Lang.value = ChangeLanguage.getLanguage(requireContext())
        mViewModel.userId.value= data?.getValue(SharedData.ReturnValue.STRING, "token")
        productsGridAdapter.token=data?.getValue(SharedData.ReturnValue.STRING, "token")

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

    private fun checkEmpryProduct(it : CombinedLoadStates){
        if (it.source.refresh is LoadState.Error && productsGridAdapter.itemCount==0){
            showEmptyPage()
        }
    }


    fun showEmptyPage(){
        mViewDataBinding.RelaEmpty.visibility=View.VISIBLE
    }
    fun hideEmptyPage(){
        mViewDataBinding.RelaEmpty.visibility=View.INVISIBLE
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
        val intent=Intent(requireContext(), BottomNavigateActivity::class.java)
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
           if(it){
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
            token?.let { it1 -> productReviwesViewModel.removeFavourit(detailsProduct.id.toString(), it1) }
        } else {
            token?.let { it1 -> productReviwesViewModel.addFavourit(detailsProduct.id.toString(), it1) }
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


    private fun subscribeFiltertion() {
        mViewModel.filter.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val bundle2 = Bundle()
                bundle2.putString("cat_Id", it.cat_Id)
                bundle2.putString("brand_Id", it.brand_Id)
                bundle2.putString("min_Price", it.min_Price)
                bundle2.putString("max_Price", it.max_Price)
                Navigation.findNavController(mViewDataBinding.root)
                    .navigate(R.id.action_productsById_to_resultFiltertionFragment, bundle2);
                mViewModel.filter.value = null
            }
        })
    }
}