package com.cairocart.Fragments


import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.cairocart.Adapter.Cart_Adapter
import com.cairocart.ChangeLanguage
import com.cairocart.Model.*
import com.cairocart.R
import com.cairocart.ViewModel.Cart_ViewModel
import com.cairocart.View.PlusId_View
import com.cairocart.ViewModel.AddToCart_ViewModel
import kotlinx.android.synthetic.main.fragment_cart.view.*
import org.greenrobot.eventbus.EventBus
import kotlin.math.roundToInt

/**
 * A simple [Fragment] subclass.
 */
class Cart : Fragment() , PlusId_View, SwipeRefreshLayout.OnRefreshListener{
    lateinit var root:View
    private lateinit var DataSaver: SharedPreferences
     var UserToken: String?= String()
    var Total:Int=0
    var toolbarhome: Toolbar?=null
    var bundle=Bundle()
    var T_TotalNumber:String?=null
    var Items:String?=null
    var Currency:String?=null
    var Taxes:String?=null

    companion object {
        var T_TotalPrice:String?=null
    }
    lateinit var allproducts: Cart_ViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_cart, container, false)
        allproducts = ViewModelProvider.NewInstanceFactory().create(
                Cart_ViewModel::class.java)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(requireContext())
        UserToken = DataSaver.getString("token", null)
        SwipRefresh()
        getAllCart()
        checkOrder()

        return root
    }


    private fun checkOrder() {
        root.Btn_Checkout.setOnClickListener() {
                    var productsByid = OrderLocation_Fragmet()
                    val bundle = Bundle()
                    bundle.putString("totalprice", T_TotalPrice!!)
                    bundle.putString("number", T_TotalNumber!!)
                    bundle.putString("items", Items!!)
                    bundle.putString("cu", Currency!!)
                    bundle.putString("taxes",Taxes)

                    productsByid.arguments = bundle
                    Navigation.findNavController(root)
                        .navigate(R.id.action_T_Cart_to_orderLocation_Fragmet, bundle);
        }
    }

    fun getAllCart(){
        root.SwipCart.isRefreshing= true
        requireContext()?.let {
            allproducts.getData(
                ChangeLanguage.getLanguage(requireContext()),DataSaver.getString("token", null), it)
                .observe(viewLifecycleOwner, Observer<AllProducts_Response> { loginmodel ->
                root.SwipCart.isRefreshing=false
                if(loginmodel!=null) {
                    if(loginmodel.data!!.data.size>0) {
                        Total = 0
                        root.Relaa_Cart.visibility = View.VISIBLE
//                        root.Rela_total.visibility = View.VISIBLE
                        root.Btn_Checkout.visibility = View.VISIBLE
                        T_TotalNumber = loginmodel.data?.data?.size.toString()
                        loginmodel.data?.data?.forEachIndexed { index, _ ->
                            Total = (Total + loginmodel.data?.data?.get(index)?.productInCartTotal!!).roundToInt()
                        }
                        Items = loginmodel.data?.data?.size.toString()
//                    val pi = loginmodel.data.data
//                    val s = "%.2f".format(pi)
                        T_TotalPrice = Total.toString()
                        Currency = loginmodel.data!!.data.get(0).currency
                        root.Total_Products.text= String.format(loginmodel.data!!.data.size.toString()
                                +" "+resources.getString(R.string.product))
                        root.T_TotalCost.text = String.format( loginmodel.data?.data?.size.toString() + "  " + resources.getString(R.string.Items) + "/" + resources.getString(
                            R.string.total
                        ) + " " + T_TotalPrice + " " + loginmodel.data!!.data.get(0).currency)
                        root.Total_Price.text =
                            T_TotalPrice + " " + loginmodel.data!!.data.get(0).currency
                        val listAdapter =
                            Cart_Adapter(requireContext(),
                                loginmodel!!.data!!.data as MutableList<AllProducts_Response.Data.Data>
                            )
                        listAdapter.OnClickPlus(this)
                        root.recycler_Cart.layoutManager = LinearLayoutManager(
                            requireContext(), LinearLayoutManager.VERTICAL
                            , false
                        )
                        root.recycler_Cart.setAdapter(listAdapter)
                    }else {
                        root.Relaa_Cart.visibility=View.GONE
                        root.Rela_total.visibility=View.GONE
                        root.T_NoCart.visibility=View.VISIBLE
                        root.Relaa_Cart.visibility=View.GONE
                        root.recycler_Cart.visibility=View.GONE
                        root.Btn_Checkout.visibility=View.GONE

                    }
                }

            })
        }
    }


    override fun minus_Id(Id: String) {


    }

    override fun Plus_Id(Id: String,Quantity:String) {
        var addtocart: AddToCart_ViewModel = ViewModelProvider.NewInstanceFactory().create(
            AddToCart_ViewModel::class.java)
        requireContext()?.let {
            addtocart.getData(DataSaver.getString("token", null)!!, Id,Quantity
                , it)
                .observe(viewLifecycleOwner, Observer<AddToCart_Response> { loginmodel ->
                    EventBus.getDefault().postSticky(MessageEvent("cart"))
                    if (loginmodel != null) {
                        getAllCart()
                    }
                })
        }
    }

    fun SwipRefresh(){
        root.SwipCart.setOnRefreshListener(this)
        root.SwipCart.isRefreshing=true
        root.SwipCart.isEnabled = true
        root.SwipCart.setColorSchemeResources(
                R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark
        )
        root.SwipCart.post(Runnable {
            getAllCart()

        })
    }
    override fun onRefresh() {
        getAllCart()

    }
    override fun delete(Id: String) {
        root.SwipCart.isRefreshing= true

        requireContext()?.let {
            allproducts.DeleteData(UserToken!!,"en",Id, it).observe(this, Observer<PlusCart_Response> { loginmodel ->
                if(loginmodel!=null) {
                    EventBus.getDefault().postSticky(MessageEvent("cart"))
                    getAllCart()
                }

            })
        }
    }

}
