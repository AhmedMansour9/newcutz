package com.gazr.Fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gazr.Activities.Congratulation
import com.gazr.Activities.TabsLayout
import com.gazr.Adapter.CartOrder
import com.gazr.ChangeLanguage
import com.gazr.Model.AllProducts_Response
import com.gazr.Model.Order_Response
import com.gazr.R
import com.gazr.ViewModel.Cart_ViewModel
import com.gazr.ViewModel.Order_ViewModel
import com.gazr.utils.Loading
import kotlinx.android.synthetic.main.fragment_confirm_order.view.*
import kotlinx.android.synthetic.main.fragment_confirm_order.view.Btn_order

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ConfirmOrder.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConfirmOrder : Fragment() {
   lateinit var root:View
    private lateinit var DataSaver: SharedPreferences
    var UserToken: String?= String()
    lateinit var allproducts: Cart_ViewModel
    var bundle=Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_confirm_order, container, false)
        allproducts = ViewModelProvider.NewInstanceFactory().create(
            Cart_ViewModel::class.java)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(requireContext())
        UserToken = DataSaver.getString("token", null)

        getData()
       getAllCart()
        Btn_Order()

        return root
    }
    fun getData(){
        bundle=this.requireArguments()
        root.T_FullName.text=bundle.getString("name")
        root.T_Phone.text=bundle.getString("phone")
        root.T_City.text=bundle.getString("city")

        root.DelivryPrice.text=bundle.getString("d_value")
        var total=bundle.getString("total")!!.toDouble()+bundle.getString("d_value")!!.toDouble()
        root.T_Total.text=total.toString()+" "+bundle.getString("cu")


    }


    fun getAllCart(){
        requireContext()?.let {
            allproducts.getData(
                ChangeLanguage.getLanguage(requireContext()),DataSaver.getString("token", null), it)
                .observe(viewLifecycleOwner, Observer<AllProducts_Response> { loginmodel ->
                    if(loginmodel!=null) {
                        if(loginmodel.data!!.data.size>0) {

                            val listAdapter =
                                CartOrder(requireContext(), loginmodel!!.data!!.data)
                            root.recycler_Cart.layoutManager = LinearLayoutManager(
                                requireContext(), LinearLayoutManager.VERTICAL
                                , false
                            )
                            root.recycler_Cart.setAdapter(listAdapter)
                        }else {

                        }
                    }

                })
        }
    }


    fun Btn_Order() {
        root.Btn_order.setOnClickListener() {

            var Name = bundle.getString("name")
            var Phone =bundle.getString("phone")
            var StreetName =bundle.getString("street")!!


                        Loading.Show(requireContext())
                        root.Btn_order.isEnabled = false
                        var order: Order_ViewModel =
                            ViewModelProviders.of(this)[Order_ViewModel::class.java]
                        order.getData(
                            UserToken!!,
                            Name!!,
                            StreetName,
                            Phone!!
                            , "cacheOnDelivery"
                            , "0"
                            , bundle.getString("city")!!
                            , ""
                            , StreetName
                            , "0"
                            , ""
                            , bundle.getString("b_number")!!
                            , bundle.getString("floor")!!
                            , ""
                            ,bundle.getString("promo")
                            ,bundle.getString("city_id")!!
                            ,bundle.getString("d_type")!!
                            , bundle.getString("state_id")
                            ,bundle.getString("receive_points_id")
                            ,bundle.getString("note")
                            , requireContext()
                        ).observe(viewLifecycleOwner,
                            Observer<Order_Response> { loginmodel ->
                                Loading.Disable()
                                if (loginmodel != null) {
                                    var inten=Intent(context, Congratulation::class.java)
                                    inten.putExtra("msg",requireContext().getString(R.string.ordersuccess))
                                    inten.putExtra("status","true")
                                    startActivity(inten)

                                } else {
                                    root.Btn_order.isEnabled = true
                                    Toast.makeText(
                                        requireContext(),
                                        requireContext().getString(R.string.failedmsg),
                                        Toast.LENGTH_LONG
                                    ).show()

                                }


                            })
        }
    }

}