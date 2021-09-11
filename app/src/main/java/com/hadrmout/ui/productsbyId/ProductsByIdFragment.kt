package com.hadrmout.ui.productsbyId

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.hadrmout.R
import com.hadrmout.adapter.CategoryAdapter
import com.hadrmout.adapter.GridProductsAdapter
import com.hadrmout.base.BaseFragment
import com.hadrmout.data.remote.model.LocationModel
import com.hadrmout.data.remote.model.ProductsResponse
import com.hadrmout.data.remote.model.SectonsResponse
import com.hadrmout.databinding.FragmentProductsByIdBinding
import com.hadrmout.ui.detailsproduct.DetailsProductFragment
import com.hadrmout.ui.detailsproduct.DetailsProductViewModel
import com.hadrmout.ui.nointernet.NoInternertActivity
import com.hadrmout.utils.SharedData
import com.hadrmout.utils.Status
import com.hadrmout.utils.isConnected
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

@AndroidEntryPoint
class ProductsById : BaseFragment<FragmentProductsByIdBinding>(),
    SwipeRefreshLayout.OnRefreshListener {
    var linearView = true
    lateinit var details: SectonsResponse.Data.Category.SubCategory
    lateinit var detailsSections: SectonsResponse.Data
    lateinit var detailsparts: SectonsResponse.Data.Category.Parts

    var bundle = Bundle()
    private var searchJob: Job? = null
    private val productReviwesViewModel: DetailsProductViewModel by viewModels()
    override var idLayoutRes: Int = R.layout.fragment_products_by_id
    private var data: SharedData? = null
    private var token: String? = String()
    private var type: String? = String()
    private var product_Id: String? = String()
    private var Type: String? = String()
    private lateinit var detailsProduct: ProductsResponse.Data
    private lateinit var productsGridAdapter: GridProductsAdapter
    var location: LocationModel? = LocationModel()
    private lateinit var catAdapter: CategoryAdapter
    lateinit var items: SectonsResponse.Data

    val mViewModel: ProductsByIdViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        initAdapterCategory()
        initAdapter()
        initRecycle()
        getData()
        subscribeAddFavourit()
        subscriberemoveFavourit()
        subscriberegetProducts()
        onClickSearch()
        onClickBack()

    }

    private fun onClickBack() {
        mViewDataBinding.ImgBack.setOnClickListener() {
            findNavController().popBackStack()
        }
    }

    fun initAdapter() {
        productsGridAdapter = GridProductsAdapter(requireContext(), productData = object :
            GridProductsAdapter.ProductItemListener {
            override fun itemClicked(productData: ProductsResponse.Data?) {
                val newDialogFragment = DetailsProductFragment()
                val bundle = Bundle()
                bundle.putParcelable("item", productData)
                newDialogFragment.setArguments(bundle)
                val transaction: FragmentTransaction =
                    requireActivity().supportFragmentManager.beginTransaction()
                newDialogFragment.show(transaction, "New_Dialog_Fragment")
            }

            override fun itemFavourit(productData: ProductsResponse.Data?) {
                detailsProduct = productData!!
                token = data?.getValue(SharedData.ReturnValue.STRING, "token")
                checkIsFavourit(detailsProduct.isFavoirte!!)
            }
        })
    }


    private fun subscriberegetProducts() {
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
                    showEmptyPage()
                }
            }
        })
    }

    private fun setData(data: ProductsResponse?) {
        if (data != null) {
            mViewDataBinding.recyclerProducts.visibility = View.VISIBLE
            productsGridAdapter.setDeveloperList(data.data)
            hideEmptyPage()
        } else {
            showEmptyPage()
        }
    }


    private fun initAdapterCategory() {
        catAdapter =
            CategoryAdapter(requireContext(), object : CategoryAdapter.CategoryItemListener {
                override fun itemClicked(productData: SectonsResponse.Data.Category) {
                    mViewDataBinding.recyclerProducts.visibility = View.GONE
                    onClickItem(productData)

                }
            })
    }

    private fun onClickItem(productData: SectonsResponse.Data.Category) {
        product_Id = productData.id.toString()
        getProducts(product_Id!!)


    }

    private fun getProducts(id: String) {
        mViewModel.getProducts(
            token!!,
            detailsSections.id.toString(),
            id,
            Type!!
        )
    }

    private fun initRecycle() {
        mViewDataBinding.recyclerCategroies.setHasFixedSize(true)
        mViewDataBinding.recyclerCategroies.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        mViewDataBinding.recyclerCategroies.adapter = catAdapter

        mViewDataBinding.recyclerProducts.setHasFixedSize(true)
        mViewDataBinding.recyclerProducts.setLayoutManager(
            GridLayoutManager(
                requireContext(), 2
            )
        )
        mViewDataBinding.recyclerProducts.adapter = productsGridAdapter


    }

    private fun onClickSearch() {
        mViewDataBinding.ESearch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                productsGridAdapter.filter.filter(s.toString())

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
            }
        })
    }


    private fun init() {
        mViewDataBinding.productsViewModel = mViewModel
        mViewDataBinding.SwipCategories.setOnRefreshListener(this)
        data = SharedData(requireContext())
    }


    private fun getData() {
        bundle = this.requireArguments()
        type = bundle.getString("type")
        mViewModel.type.value = type
        detailsSections = bundle.getParcelable("data")!!

        mViewDataBinding.TTitle.text = detailsSections.title
        token = data?.getValue(SharedData.ReturnValue.STRING, "token")
        Type = data?.getValue(SharedData.ReturnValue.STRING, "type")
        var gsonData = Gson()
        var json: String? = data?.getValue(SharedData.ReturnValue.STRING, "json")
        location = gsonData.fromJson(json, LocationModel::class.java)
        items = bundle.getParcelable("data")!!
        items.categories?.let { catAdapter.setList(it) }
        type = bundle.getString("type")

    }


    private fun checkEmpryProduct(it: CombinedLoadStates) {
        if (it.source.refresh is LoadState.Error && productsGridAdapter.itemCount == 0) {
            showEmptyPage()
        }
    }


    fun showEmptyPage() {
        mViewDataBinding.RelaEmpty.visibility = View.VISIBLE
    }

    fun hideEmptyPage() {
        mViewDataBinding.RelaEmpty.visibility = View.INVISIBLE
    }


//    override fun onClickFilter() {
////        val newDialogFragment = FiltertionFragment()
////        val transaction: FragmentTransaction =
////            requireActivity().supportFragmentManager.beginTransaction()
////        newDialogFragment.show(transaction, "New_Dialog_Fragment")
//    }

//    override fun openHome() {
//        val intent=Intent(requireContext(), BottomNavigateActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//        startActivity(intent)
//    }

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



    override fun onRefresh() {
        mViewDataBinding.SwipCategories.isRefreshing = false
        getProducts(product_Id!!)

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

//
//    @Subscribe(sticky = false, threadMode = ThreadMode.BACKGROUND)
//    fun onMessageEvent(messsg: MessageEvent) {/* Do something */
//        if(messsg.Message.equals("login")){
//            productsGridAdapter.token=data?.getValue(SharedData.ReturnValue.STRING, "token")
//        }
//        if(messsg.Message.equals("network")){
//            refreshData()
//        }
//    };
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this)
//        }
//
//    }


    fun checkInternet() {
        if (!requireContext().isConnected) {
            startActivity(Intent(requireContext(), NoInternertActivity::class.java))
        }
    }
//    fun refreshData(){
//        if(requireContext().isConnected){
//            productsGridAdapter.refresh()
//        }else {
//            startActivity(Intent(requireContext(), NoInternertActivity::class.java))
//        }
//    }
//

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