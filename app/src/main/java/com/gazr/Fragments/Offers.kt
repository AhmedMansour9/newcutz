package com.gazr.Fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
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
import com.gazr.Activities.Login
import com.gazr.Adapter.Offers_Adapter
import com.gazr.ChangeLanguage
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
import kotlinx.android.synthetic.main.fragment_offers.view.*
import org.greenrobot.eventbus.EventBus


/**
 * A simple [Fragment] subclass.
 * Use the [Offers.newInstance] factory method to
 * create an instance of this fragment.
 */
class Offers : Fragment() , SwipeRefreshLayout.OnRefreshListener, DetailsProduct_id,
    ProductDetails_View {
   lateinit var root:View
    var UserToken:String?= String()
    private lateinit var DataSaver: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_offers, container, false)

       SwipRefresh()

        return root
    }

    override fun onRefresh() {
        DataSaver = PreferenceManager.getDefaultSharedPreferences(requireContext())

        UserToken =DataSaver.getString("token", null)

        getBestSalling()

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
                "1", it
                , "offers", UserToken
            ).observe(viewLifecycleOwner, Observer<AllProducts_Response> { loginmodel ->
                root.SwipOffers.isRefreshing = false
                if (loginmodel != null) {
                    root.T_TotalProdOffers.text = "( " + loginmodel.data?.meta?.total + " " + resources.getString(R.string.product) + " )"
                    var listAdapter =
                        Offers_Adapter(
                            requireContext(),
                            loginmodel.data?.data as List<AllProducts_Response.Data.Data>
                        )
                    listAdapter.onClick(this)
                    listAdapter.onClickFavourit(this)
                    root.recycler_Offers.setLayoutManager(
                        GridLayoutManager(
                            requireContext()
                            , 2
                        )
                    )
                    root.recycler_Offers.setAdapter(listAdapter)
                    root.recycler_Offers.overScrollMode = View.OVER_SCROLL_NEVER

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
}