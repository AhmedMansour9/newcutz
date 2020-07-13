package com.mgh.Fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mgh.Activities.Login
import com.mgh.Activities.Navigation.Companion.drawerLayout
import com.mgh.Adapter.*
import com.mgh.ChangeLanguage
import com.mgh.Model.*
import com.mgh.R
import com.mgh.SharedPrefManager
import com.mgh.View.DetailsArticatal_View
import com.mgh.View.DetailsProduct_id
import com.mgh.View.ProductBytUd_View
import com.mgh.View.ProductDetails_View
import com.mgh.ViewModel.Cart_ViewModel
import com.mgh.ViewModel.Categories_ViewModel
import com.mgh.ViewModel.SliderHome_ViewModel
import com.mgh.ViewModel.getAllProducts_ViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_favourit.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_home.view.recycler_Categroies
import kotlinx.android.synthetic.main.fragment_home.view.viewPager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener, DetailsProduct_id,
    ProductDetails_View , DetailsArticatal_View, ProductBytUd_View {


    //    lateinit var allproducts: Cart_ViewModel
    var toolbarhome: Toolbar? = null
    private lateinit var DataSaver: SharedPreferences
    var swipeTimer: Timer? = null
    val handler = Handler()
    val Update = Runnable {
        if (currentPage == NUM_PAGES) {
            currentPage = 0
        }
        root.viewPager!!.setCurrentItem(currentPage++, true)
    }
    lateinit var DetailsDeal: ProductDeal_Response.Data
    private var currentPage = 0
    private var NUM_PAGES = 0
    private var UserToken:String?= String()
    lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_home, container, false)
        init()
        SwipRefresh()
        openCart()
        getNumberOfCart()
        openAllBestSalling()
        SearchKeyBoard()
        openAllCategories()
        return root
    }

    private fun openAllBestSalling() {
        root.T_SeeBestSall.setOnClickListener() {
            var view: Int = root.Rela_Home.id

            var productsByid = AllFilterProducts()
            val bundle = Bundle()
            bundle.putInt("view", view)
            bundle.putString("type", "all")
            bundle.putString("name", null)
            bundle.putString("link", "bestSelling")
            productsByid.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_Home, productsByid)
                .addToBackStack(null).commit()
        }
    }

    private fun openAllCategories() {
        root.T_SeeCategorues.setOnClickListener() {
            var productsByid = Menu_Categories()
            val bundle = Bundle()
            productsByid.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_Home, productsByid)
                .addToBackStack(null).commit()
        }
    }


    private fun openCart() {
        root.Img_Cart.setOnClickListener() {
            if(UserToken.isNullOrEmpty()){
              startActivity(Intent(requireContext(),Login::class.java))
                return@setOnClickListener
            }
            var view: Int = root.Rela_Home.id
            var productsByid = Cart()
            val bundle = Bundle()
            bundle.putInt("view", view)
            productsByid.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction().add(view, productsByid)
                .addToBackStack(null).commit()
        }
    }

    fun getNumberOfCart() {
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
        var allproducts: Cart_ViewModel = ViewModelProvider.NewInstanceFactory().create(
            Cart_ViewModel::class.java
        )
        this.context!!.applicationContext?.let {
            allproducts.getData(ChangeLanguage.getLanguage(requireContext()),UserToken, it
            ).observe(viewLifecycleOwner, Observer<AllProducts_Response> { loginmodel ->

                if (loginmodel != null) {
                    if(loginmodel.data!!.data.size>0)
                    root.T_notification_numdetai.visibility = View.VISIBLE
                    root.T_notification_numdetai.text = loginmodel.data!!.data.size.toString()

                }

            })
        }

    }

    fun SwipRefresh() {
        root.SwipHome.setOnRefreshListener(this)
        root.SwipHome.isRefreshing = true
        root.SwipHome.isEnabled = true
        root.SwipHome.setColorSchemeResources(
            R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark
        )
        root.SwipHome.post(Runnable {
            getSlider()
            getDealsProducts()
            getBestSalling()
            getAllCategories()

        })
    }

    fun getAllCategories() {
        var categories: Categories_ViewModel = ViewModelProvider.NewInstanceFactory().create(
            Categories_ViewModel::class.java
        )
        this.context!!.applicationContext?.let {
            categories.getCategories("1", it)
                .observe(viewLifecycleOwner, Observer<Categories_Response>
                { loginmodel ->
                    if (loginmodel != null) {
                        root.T_TotalCatrgories.text = "( " + loginmodel.data?.meta?.total + " "+resources.getString(R.string.category) + " )"
                        val listAdapter =
                            CategoriesHome_Adapter(
                                context!!.applicationContext,
                                loginmodel.data?.data as List<Categories_Response.Data.Data>
                            )
                    listAdapter.onClick(this)
                        root.recycler_Categroies.layoutManager = LinearLayoutManager(
                            this.context!!.applicationContext,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        root.recycler_Categroies.setAdapter(listAdapter)
                        root.recycler_Categroies.overScrollMode = View.OVER_SCROLL_NEVER


                    }
                })
        }
    }


    fun getSlider() {
        var SliderHome: SliderHome_ViewModel = ViewModelProvider.NewInstanceFactory().create(
            SliderHome_ViewModel::class.java
        )
        this.context!!.applicationContext?.let {
            SliderHome.getData(ChangeLanguage.getLanguage(it), it)
                .observe(viewLifecycleOwner, Observer<SliderHome_Model> { loginmodel ->
                    if (loginmodel != null) {
                        viewPager!!.adapter = this.context?.let { it1 ->
                            Slider_Adapter(
                                it1,
                                loginmodel.data as ArrayList<SliderHome_Model.Slider_Home>
                            )
                        }

                        root.view_pager_circle_indicator.setViewPager(viewPager)
                        NUM_PAGES = loginmodel.data.size
                        swipeTimer = Timer()
                        swipeTimer!!.schedule(object : TimerTask() {
                            override fun run() {
                                handler.post(Update)
                            }
                        }, 3000, 3000)
                    }
                })
        }
    }


    fun getBestSalling() {
        var allproducts: getAllProducts_ViewModel = ViewModelProvider.NewInstanceFactory().create(
            getAllProducts_ViewModel::class.java
        )
        this.context!!.applicationContext?.let {
            allproducts.getLatest(
                "1", ChangeLanguage.getLanguage(it)
                , "bestSelling", UserToken, it
            ).observe(viewLifecycleOwner, Observer<AllProducts_Response> { loginmodel ->
                root.SwipHome.isRefreshing = false
                if (loginmodel != null) {
                    root.T_TotalBestSalling.text = "( " + loginmodel.data?.meta?.total + " " +
                            resources.getString(R.string.product) + " )"
                    var listAdapter =
                        Offers_Adapter(
                            context!!.applicationContext,
                            loginmodel.data?.data as List<AllProducts_Response.Data.Data>
                        )
                    listAdapter.onClick(this)
                    listAdapter.onClickFavourit(this)
                    root.recycler_BestSales.layoutManager = LinearLayoutManager(
                        this.context!!.applicationContext,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    root.recycler_BestSales.setAdapter(listAdapter)
                    root.recycler_BestSales.overScrollMode = View.OVER_SCROLL_NEVER

                }
            })
        }


    }

    fun getDealsProducts() {
        var allproducts: getAllProducts_ViewModel = ViewModelProvider.NewInstanceFactory().create(
            getAllProducts_ViewModel::class.java
        )
        this.context!!.applicationContext?.let {
            allproducts.getLatest(
                "1", ChangeLanguage.getLanguage(it)
                , "hotDeal",UserToken, it
            ).observe(viewLifecycleOwner, Observer<AllProducts_Response> { loginmodel ->
                root.SwipHome.isRefreshing = false
                if (loginmodel != null) {
                    root.T_TotalOffers.text =
                        "( " + loginmodel.data?.meta?.total + " " + resources.getString(R.string.product) + " )"

                    var listAdapter =
                        Deails_Adapter(
                            context!!.applicationContext,
                            loginmodel.data?.data as List<AllProducts_Response.Data.Data>
                        )
                    listAdapter.onClick(this)
                    listAdapter.onClickFavourit(this)
                    root.recycler_Deals.layoutManager = LinearLayoutManager(
                        this.context!!.applicationContext,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    root.recycler_Deals.setAdapter(listAdapter)
                    root.recycler_Deals.overScrollMode = View.OVER_SCROLL_NEVER

                }
            })
        }


    }


    override fun onRefresh() {
        getSlider()
        getDealsProducts()
        getBestSalling()
        getAllCategories()
    }

    override fun DetailsProducts(detailsProduct: AllProducts_Response.Data.Data) {

    }

    override fun Details(detailsProduct: AllProducts_Response.Data.Data) {
        var productsByid = Product_Details()
        val bundle = Bundle()
        bundle.putString("type", null)
        bundle.putParcelable("ProductItem", detailsProduct)
        productsByid.arguments = bundle
        activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_Home, productsByid)
            .addToBackStack(null).commit()
    }

    override fun AddToFavourit(api: String, Productid: String) {
        if(UserToken!=null){
            var allproducts: getAllProducts_ViewModel = ViewModelProvider.NewInstanceFactory().create(
                getAllProducts_ViewModel::class.java
            )
            this.context!!.applicationContext?.let {
                allproducts.getAddFavouritProducts(
                    api, Productid,
                    DataSaver.getString("token", null)!!, it
                ).observe(viewLifecycleOwner, Observer<AddFav_Response> {

                })
            }

        }

    }


    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent(messsg: MessageEvent) {/* Do something */
        Log.d("IGNORE", "Logging view to curb warnings: $messsg")
        UserToken=DataSaver.getString("token", null)

        getDealsProducts()
        getBestSalling()
        getNumberOfCart()


    };
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

    }


    override fun onResume() {
        super.onResume()
    }

    fun init() {
        root.E_Search.setFocusableInTouchMode(false);
        root.E_Search.setFocusable(false);
        root.E_Search.setFocusableInTouchMode(true);
        root.E_Search.setFocusable(true);
        toolbarhome = root.findViewById(R.id.toolbarhome)

        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
        UserToken=DataSaver.getString("token", null)

        val toggle = ActionBarDrawerToggle(
            activity,
            drawerLayout,
            toolbarhome,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.apply {
            syncState()
            isDrawerIndicatorEnabled = false
            setHomeAsUpIndicator(R.drawable.ic_homemenu)
            setToolbarNavigationClickListener { drawerLayout!!.openDrawer(GravityCompat.START) }
        }
        drawerLayout?.addDrawerListener(toggle)

    }

    override fun details(details: News_Response.Data) {
        var productsByid = DetailsArticatle_Fragment()
        val bundle = Bundle()
        bundle.putParcelable("articitalItem", details)
        productsByid.arguments = bundle
        activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_Home, productsByid)
            .addToBackStack(null).commit()

    }

    private fun SearchKeyBoard() {
        root.E_Search.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (!root.E_Search.text.toString().isEmpty()) {
                    var productsByid = AllFilterProducts()
                    val bundle = Bundle()
                    bundle.putString("type", "search")
                    bundle.putString("link", "searchProduct")
                    bundle.putString("name", root.E_Search.text.toString())

                    productsByid.arguments = bundle
                    activity!!.supportFragmentManager.beginTransaction()
                        .add(R.id.Rela_Home, productsByid)
                        .addToBackStack(null).commit()
                }
                return@OnEditorActionListener true
            }
            false
        })
    }

    override fun Id(categories: Categories_Response.Data.Data) {
        var productsByid = AllFilterProducts()
        val bundle = Bundle()
        bundle.putString("type", "search")
        bundle.putString("link", "searchProduct")
        bundle.putString("name", null)
        bundle.putString("id", categories.id.toString())

        productsByid.arguments = bundle
        activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_Home, productsByid)
            .addToBackStack(null).commit()

}

}