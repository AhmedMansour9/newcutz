package com.gazr.Fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.gazr.Activities.Login
import com.gazr.Adapter.Offers_Adapter
import com.gazr.ChangeLanguage
import com.gazr.EndlessScrollListener
import com.gazr.Model.AddFav_Response
import com.gazr.Model.AddToCart_Response
import com.gazr.Model.AllProducts_Response
import com.gazr.Model.MessageEvent
import com.gazr.R
import com.gazr.View.DetailsProduct_id
import com.gazr.View.ProductDetails_View
import com.gazr.ViewModel.AddToCart_ViewModel
import com.gazr.ViewModel.getAllProducts_ViewModel
import com.gazr.utils.Loading
import kotlinx.android.synthetic.main.fragment_all_filter_products.view.*
import kotlinx.android.synthetic.main.fragment_all_filter_products.view.E_Search
import kotlinx.android.synthetic.main.fragment_all_filter_products.view.SwipFavourits
import kotlinx.android.synthetic.main.fragment_all_filter_products.view.T_Nofavourit
import kotlinx.android.synthetic.main.fragment_all_filter_products.view.recycler_Favourits
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [AllFilterProducts.newInstance] factory method to
 * create an instance of this fragment.
 */
class AllFilterProducts : Fragment() , SwipeRefreshLayout.OnRefreshListener, ProductDetails_View,
    DetailsProduct_id {
    var Type:String?= String()
    var link:String?= String()
    var name:String?= String()
    var cat_id:String?= String()
    var sub_id:String?= String()

    var toolbarhome: Toolbar?=null
    var my_page = 1
    var hasMorePages:Boolean=true
    lateinit var result_List: MutableList<AllProducts_Response.Data.Data>
    lateinit var endlessScrollListener: EndlessScrollListener
    private lateinit var DataSaver: SharedPreferences
   var bundle:Bundle?= Bundle()
    lateinit var  listAdapter:Offers_Adapter

    lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_all_filter_products, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(requireContext())
        result_List=  mutableListOf<AllProducts_Response.Data.Data>()
        init()
        SwipRefresh()
        initScroll()
        SearchKeyBoard()
        TextChanger()
        return root
    }

    private fun SearchKeyBoard() {
        root.E_Search.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                link="searchProduct"
                Type="search"
                name=root.E_Search.text.toString()
                my_page=1
                result_List.clear()
                listAdapter.notifyDataSetChanged()
                root.SwipFavourits.isRefreshing=true
                    getSearchProducts(my_page)
                return@OnEditorActionListener true
            }
            false
        })
    }

    fun initScroll(){

        endlessScrollListener = object : EndlessScrollListener(root.recycler_Favourits.layoutManager!!) {
            override fun onLoadMore(currentPage: Int, totalItemCount: Int) {
                if(Type.equals("all")) {
                    if (hasMorePages) {
                        my_page++
                        getProducts(my_page)
                    }
                }else {
                    if (hasMorePages) {
                        my_page++
                        getSearchProducts(my_page)
                    }
                }


            }

            override fun onScroll(firstVisibleItem: Int, dy: Int, scrollPosition: Int) {}
        }
    }
    fun init() {
        bundle=this.arguments
        link=bundle?.getString("link")
        Type=bundle?.getString("type")
        name=bundle?.getString("name")
        cat_id=bundle?.getString("id")
        sub_id=bundle?.getString("sub_id")
        listAdapter =
            Offers_Adapter(requireContext(),result_List)
        listAdapter.onClick(this)
        listAdapter.onClickFavourit(this)
        root.recycler_Favourits.setLayoutManager(
            GridLayoutManager(
                requireContext()
                , 2
            )
        )
        root.recycler_Favourits.setAdapter(listAdapter)

        toolbarhome=root.findViewById(R.id.toolbarFavourit)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(requireContext())

    }

    fun SwipRefresh(){
        root.SwipFavourits.setOnRefreshListener(this)
        root.SwipFavourits.isRefreshing=true
        root.SwipFavourits.isEnabled = true
        root.SwipFavourits.setColorSchemeResources(
            R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark
        )
        root.SwipFavourits.post(Runnable {
            if(Type.equals("all")) {
                getProducts(my_page)
            }else {
                getSearchProducts(my_page)
            }
        })
    }
    fun TextChanger(){
        root.E_Search.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                link="searchProduct"
                Type="search"
                name=root.E_Search.text.toString()
                my_page=1
                result_List.clear()
                listAdapter.notifyDataSetChanged()
                root.SwipFavourits.isRefreshing=true
                getSearchProducts(my_page)
            }
        })
    }

    fun getProducts( page:Int){
        var allproducts: getAllProducts_ViewModel =  ViewModelProvider.NewInstanceFactory().create(
            getAllProducts_ViewModel::class.java)
        context?.let { ChangeLanguage.getLanguage(it) }?.let {
            allproducts.getLatest(
                page.toString(), it
                ,link!!,DataSaver.getString("token",null))
                .observe(viewLifecycleOwner, Observer<AllProducts_Response> { loginmodel ->
                    root.SwipFavourits.isRefreshing=false
                    if(loginmodel!=null) {
                        root.T_TotalProd.text="( "+loginmodel.data!!.meta!!.total!!.toString()+" "+resources.getString(R.string.product)+" )"

                        hasMorePages= loginmodel.data?.meta?.hasMorePages!!
                        result_List.addAll(loginmodel.data!!.data)
                        listAdapter.notifyItemRangeInserted(
                            listAdapter.getItemCount(),
                            result_List.size
                        )
                        root.recycler_Favourits.addOnScrollListener(endlessScrollListener)

                    }else {
                        root.T_Nofavourit.visibility=View.VISIBLE
                        root.recycler_Favourits.visibility=View.GONE
                    }
                })
        }

    }
    fun getSearchProducts( page:Int){
        var allproducts: getAllProducts_ViewModel =  ViewModelProvider.NewInstanceFactory().create(
            getAllProducts_ViewModel::class.java)
        context?.let { ChangeLanguage.getLanguage(it) }?.let {
            allproducts.getFilteredData(
                page.toString(), it
                ,link!!,DataSaver.getString("token",null),name,cat_id,sub_id)
                .observe(viewLifecycleOwner, Observer<AllProducts_Response> { loginmodel ->
                    root.SwipFavourits.isRefreshing=false
                    if(loginmodel!=null) {
                        if(loginmodel.data!!.data.size>0){
                            root.T_TotalProd.text="( "+loginmodel.data!!.meta!!.total.toString()+" "+resources.getString(R.string.product)+" )"
                            hasMorePages= loginmodel.data?.meta?.hasMorePages!!
                            result_List.addAll(loginmodel.data!!.data)
                            listAdapter.notifyItemRangeInserted(
                                listAdapter.getItemCount(),
                                result_List.size
                            )
                            root.recycler_Favourits.addOnScrollListener(endlessScrollListener)
                            root.T_Nofavourit.visibility=View.GONE
                            root.recycler_Favourits.visibility=View.VISIBLE

                        }else {
                            root.T_Nofavourit.visibility=View.VISIBLE
                            root.recycler_Favourits.visibility=View.GONE
                        }

                    }
                })
        }

    }

    override fun Details(detailsProduct: AllProducts_Response.Data.Data) {
        var productsByid= Product_Details()
        val bundle = Bundle()
        bundle.putString("type",null)
        bundle.putParcelable("ProductItem", detailsProduct)
        productsByid.arguments=bundle
        Navigation.findNavController(root).navigate(R.id.action_allFilterProducts_to_product_Details,bundle);
    }
    override fun DetailsProducts(detailsProduct: AllProducts_Response.Data.Data) {

    }

    override fun onRefresh() {
        result_List.clear()
        listAdapter.notifyDataSetChanged()
        initScroll()
        my_page=1
        if(Type.equals("all")) {
            getProducts(my_page)
        }else {
            getSearchProducts(my_page)
        }
    }

    override fun AddToFavourit(api:String,Productid: String) {
        if(DataSaver.getString("token",null)!=null) {
            var allproducts: getAllProducts_ViewModel =
                ViewModelProvider.NewInstanceFactory().create(
                    getAllProducts_ViewModel::class.java
                )
            root.SwipFavourits.isRefreshing = true
//            result_List.clear()
            requireContext()?.let {
                allproducts.getAddFavouritProducts(
                    api, Productid,
                    DataSaver.getString("token", null)!!
                ).observe(viewLifecycleOwner, Observer<AddFav_Response> {
                    my_page=1
                    result_List.clear()
                    listAdapter.notifyDataSetChanged()
                    initScroll()
                    EventBus.getDefault().postSticky(MessageEvent("cart"))
                    var mp  = MediaPlayer.create(context, R.raw.favourit);
                    mp.start()
                    if(Type.equals("all")) {
                        getProducts(my_page)
                    }else {
                        getSearchProducts(my_page)
                    }

                })
            }
        }
    }

    override fun AddToFavouritCart(api: String, Productid: String) {
        if(DataSaver.getString("token",null)!=null) {
            Loading.Show(requireContext())
            var addtocart: AddToCart_ViewModel =
                ViewModelProvider.NewInstanceFactory().create(
                    AddToCart_ViewModel::class.java
                )
            requireContext()?.let {
                addtocart.getData(
                    DataSaver.getString("token", null)!!,
                    Productid,
                    "1"
                    ,
                    it
                )
                    .observe(viewLifecycleOwner, Observer<AddToCart_Response> { loginmodel ->
                        Loading.Disable()
                        if (loginmodel != null) {
                            result_List.clear()
                            listAdapter.notifyDataSetChanged()
                            initScroll()
                            my_page=1
                            if(Type.equals("all")) {
                                getProducts(my_page)
                            }else {
                                getSearchProducts(my_page)
                            }
                            EventBus.getDefault().postSticky(MessageEvent("cart"))
                            var mp  = MediaPlayer.create(it, R.raw.alert);
                            mp.start()
                            SuccessAdCart(resources.getString(R.string.addtocartsuccess))
                        }else {
                            Toast.makeText(context, AddToCart_ViewModel.ErrorAddCart, Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }else {
            val intent = Intent(requireContext(), Login::class.java)
            startActivity(intent)

        }

    }

    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {/* Do something */
        Log.d("IGNORE", "Logging view to curb warnings: $event")

//        getFavouritProducts()

    };
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

    }


    fun SuccessAdCart(Message:String) {
        val alt_bld: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alt_bld.setMessage(Message).setCancelable(false)
            .setPositiveButton(resources.getString(R.string.ok), DialogInterface.OnClickListener { dialog, id ->

            })
        val alert: AlertDialog = alt_bld.create()
        // Title for AlertDialog
        alert.setTitle(resources.getString(R.string.cartt))
        alert.setCancelable(false)
        alert.show()
    }

}