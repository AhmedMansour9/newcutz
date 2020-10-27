package com.cairocart.Fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cairocart.Activities.Filtertion
import com.cairocart.Activities.Login
import com.cairocart.Adapter.Offers_Adapter
import com.cairocart.ChangeLanguage
import com.cairocart.EndlessScrollListener
import com.cairocart.Model.*
import com.cairocart.R
import com.cairocart.View.DetailsProduct_id
import com.cairocart.View.ProductDetails_View
import com.cairocart.ViewModel.AddToCart_ViewModel
import com.cairocart.ViewModel.getAllProducts_ViewModel
import com.cairocart.utils.Loading
import kotlinx.android.synthetic.main.fragment_offers.view.*
import kotlinx.android.synthetic.main.fragment_offers.view.Img_Filter
import kotlinx.android.synthetic.main.fragment_offers.view.T_Nofavourit
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * A simple [Fragment] subclass.
 * Use the [Offers.newInstance] factory method to
 * create an instance of this fragment.
 */
class Offers : Fragment() , SwipeRefreshLayout.OnRefreshListener, DetailsProduct_id,
    ProductDetails_View {
   lateinit var root:View
    var MaxPrice:String=""
    var MinPrice:String=""
    var InCart:String=""
    var InFavourit:String=""
    var Limited:String=""
    var NewProduct:String=""
    var BestProduct:String=""
    var Type:String?= String()

    lateinit var  listAdapter:Offers_Adapter

    var my_page = 1
    var hasMorePages:Boolean=true
    lateinit var result_List: MutableList<AllProducts_Response.Data.Data>
    lateinit var endlessScrollListener: EndlessScrollListener

    var UserToken:String?= String()
    private lateinit var DataSaver: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_offers, container, false)
        result_List=  mutableListOf<AllProducts_Response.Data.Data>()
        init()
        initScroll()
        openFilter()
       SwipRefresh()

        return root
    }
    fun init() {

        listAdapter =
            Offers_Adapter(requireContext(),result_List)
        listAdapter.onClick(this)
        listAdapter.onClickFavourit(this)
        root.recycler_Offers.setLayoutManager(
            GridLayoutManager(
                requireContext()
                , 2
            )
        )
        root.recycler_Offers.setAdapter(listAdapter)

//        toolbarhome=root.findViewById(R.id.toolbarFavourit)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(requireContext())

    }
    private fun openFilter() {
        root.Img_Filter.setOnClickListener(){
            var intent=Intent(context, Filtertion::class.java)
            startActivity(intent)

        }
    }
    override fun onRefresh() {
        DataSaver = PreferenceManager.getDefaultSharedPreferences(requireContext())
        UserToken =DataSaver.getString("token", null)
        if(Type.isNullOrEmpty()) {
            if (hasMorePages) {
                my_page++
                getBestSalling()
            }
        }else {
            if (hasMorePages) {
                my_page++
                getFilterProducts(
                    my_page,
                    ChangeLanguage.getLanguage(requireContext()),
                    DataSaver.getString("token", null)!!
                    ,
                    MaxPrice,
                    MinPrice,
                    InCart,
                    InFavourit,
                    Limited
                    ,
                    NewProduct,
                    BestProduct
                )
            }
        }
    }

    fun SwipRefresh() {
        root.SwipOffers.setOnRefreshListener(this)
        root.SwipOffers.isRefreshing = true
        root.SwipOffers.isEnabled = true
        root.SwipOffers.setColorSchemeResources(
            R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark
        )
        root.SwipOffers.post(Runnable {
            DataSaver = PreferenceManager.getDefaultSharedPreferences(requireContext())
            UserToken =DataSaver.getString("token", null)
            getBestSalling()
        })
    }

    fun getBestSalling() {
        var allproducts: getAllProducts_ViewModel = ViewModelProvider.NewInstanceFactory().create(
            getAllProducts_ViewModel::class.java)

        context?.let { ChangeLanguage.getLanguage(it) }?.let {
            allproducts.getLatest(
                my_page.toString(), it
                , "offers", UserToken
            ).observe(viewLifecycleOwner, Observer<AllProducts_Response> { loginmodel ->
                root.SwipOffers.isRefreshing = false
                if(loginmodel!=null) {
                    if(loginmodel.data!!.data.size>0){
                        root.T_TotalProdOffers.text = "( " + loginmodel.data?.meta?.total + " " + resources.getString(R.string.product) + " )"
                        hasMorePages= loginmodel.data?.meta?.hasMorePages!!
                        result_List.addAll(loginmodel.data!!.data)
                        listAdapter.notifyItemRangeInserted(
                            listAdapter.getItemCount(),
                            result_List.size
                        )
                        root.recycler_Offers.addOnScrollListener(endlessScrollListener)
                        root.T_Nofavourit.visibility=View.GONE
                        root.recycler_Offers.visibility=View.VISIBLE

                    }else {
                        root.T_Nofavourit.visibility=View.VISIBLE
                        root.recycler_Offers.visibility=View.GONE
                    }

                }
            })
        }



    }

    override fun AddToFavourit(api: String, Productid: String) {

        if(DataSaver.getString("token",null)!=null) {
            var allproducts: getAllProducts_ViewModel =
                ViewModelProvider.NewInstanceFactory().create(
                    getAllProducts_ViewModel::class.java
                )
//            result_List.clear()
            requireContext()?.let {
                allproducts.getAddFavouritProducts(
                    api, Productid,
                    DataSaver.getString("token", null)!!
                ).observe(viewLifecycleOwner, Observer<AddFav_Response> {

                    EventBus.getDefault().postSticky(MessageEvent("cart"))
                    var mp  = MediaPlayer.create(context, R.raw.favourit);
                    mp.start()


                })
            }
        }else {
            val intent = Intent(requireContext(), Login::class.java)
            startActivity(intent)
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
                            getBestSalling()
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

    override fun Details(detailsProduct: AllProducts_Response.Data.Data) {
        var productsByid = Product_Details()
        val bundle = Bundle()
        bundle.putString("type", null)
        bundle.putParcelable("ProductItem", detailsProduct)
        productsByid.arguments = bundle
        Navigation.findNavController(root).navigate(R.id.action_T_Offers_to_product_Details,bundle);

    }

    override fun DetailsProducts(detailsProduct: AllProducts_Response.Data.Data) {

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


    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: FiltertionModel) {/* Do something */
        Log.d("filter", "Logging view to curb warnings: $event")
        Type="filter"
        MaxPrice=event.MaxPrice
        MinPrice=event.MinPrice
        InCart=event.InCart
        MinPrice=event.MinPrice
        InCart=event.InCart
        InFavourit=event.InFavourit
        Limited=event.Limited
        result_List.clear()
        listAdapter.notifyDataSetChanged()
        initScroll()
        my_page=1
        root.SwipOffers.isRefreshing=true
        getFilterProducts(my_page,ChangeLanguage.getLanguage(requireContext()),DataSaver.getString("token", null)!!
            ,MaxPrice,MinPrice,InCart,InFavourit,Limited
            ,NewProduct,BestProduct)

    };
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

    }
    fun getFilterProducts( page:Int,lang:String,token:String, MaxPrice:String, MinPrice:String,
                           InCart:String,
                           InFavourit:String,
                           Limited:String,
                           NewProduct:String,
                           BestProduct:String){
        var allproducts: getAllProducts_ViewModel =  ViewModelProvider.NewInstanceFactory().create(
            getAllProducts_ViewModel::class.java)
        context?.let { ChangeLanguage.getLanguage(it) }?.let {
            allproducts.getFiltertionData(
                page.toString(),lang,token,MaxPrice,MinPrice,InCart,InFavourit,Limited,NewProduct,BestProduct
                ,null,null,"true")
                .observe(viewLifecycleOwner, Observer<AllProducts_Response> { loginmodel ->
                    root.SwipOffers.isRefreshing=false
                    if(loginmodel!=null) {
                        if(loginmodel.data!!.data.size>0){
                            root.T_TotalProdOffers.text = "( " + loginmodel.data?.meta?.total + " " + resources.getString(R.string.product) + " )"
                            hasMorePages= loginmodel.data?.meta?.hasMorePages!!
                            result_List.addAll(loginmodel.data!!.data)
                            listAdapter.notifyItemRangeInserted(
                                listAdapter.getItemCount(),
                                result_List.size
                            )
                            root.recycler_Offers.addOnScrollListener(endlessScrollListener)
                            root.T_Nofavourit.visibility=View.GONE
                            root.recycler_Offers.visibility=View.VISIBLE

                        }else {
                            root.T_Nofavourit.visibility=View.VISIBLE
                            root.recycler_Offers.visibility=View.GONE
                        }

                    }
                })
        }
    }



    fun initScroll(){

        endlessScrollListener = object : EndlessScrollListener(root.recycler_Offers.layoutManager!!) {
            override fun onLoadMore(currentPage: Int, totalItemCount: Int) {
                if(Type.isNullOrEmpty()) {
                    if (hasMorePages) {
                        my_page++
                        getBestSalling()
                    }
                }else {
                    if (hasMorePages) {
                        my_page++
                        getFilterProducts(
                            my_page,
                            ChangeLanguage.getLanguage(requireContext()),
                            DataSaver.getString("token", null)!!
                            ,
                            MaxPrice,
                            MinPrice,
                            InCart,
                            InFavourit,
                            Limited
                            ,
                            NewProduct,
                            BestProduct
                        )
                    }
                }

            }

            override fun onScroll(firstVisibleItem: Int, dy: Int, scrollPosition: Int) {}
        }
    }
}
