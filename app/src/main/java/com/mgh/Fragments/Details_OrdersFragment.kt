package com.mgh.Fragments


import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mgh.Adapter.OrderDetails_Adapter
import com.mgh.ChangeLanguage
import com.mgh.Model.MyOrders_Response
import com.mgh.Model.OrderDetails_Response
import com.mgh.R
import com.mgh.ViewModel.MyOrders_ViewModel
import kotlinx.android.synthetic.main.fragment_details__orders.view.*

/**
 * A simple [Fragment] subclass.
 */
class Details_OrdersFragment : Fragment() {
    lateinit var root:View
    lateinit var bundle:Bundle
    lateinit var MyOrderId:String
    lateinit var UserToken: String
    lateinit var myOrdersData: MyOrders_Response.Data
    private lateinit var DataSaver: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_details__orders, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
        UserToken = DataSaver.getString("token", null)!!
        getData()
       getAllProducts()

        return root
    }

    fun getData() {

        bundle = this.arguments!!
        if (bundle != null) {
            myOrdersData = bundle.getParcelable("MyOrdersItem")!!
            MyOrderId = myOrdersData.id.toString()
            root.T_order_id.text= myOrdersData.id.toString()
            val orderStatusValue = myOrdersData.paymentStatus
            if(orderStatusValue.toString().equals("0")){
                root.T_order_status.text=resources.getString(R.string.InProgress)
            }


            val pi = myOrdersData.total!!.toDouble()
            val s = "%.2f".format(pi)

//            root.T_date.text=myOrdersData.code
            root.T_price.text=s + myOrdersData.orderDetails!!.get(0)!!.currency

        }

    }
    fun getAllProducts(){

                    val listAdapter =
                        OrderDetails_Adapter(myOrdersData.orderDetails)
                    root.recycler_details_list_my_orders.layoutManager = LinearLayoutManager(
                        this.context!!.applicationContext,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    root.recycler_details_list_my_orders.setAdapter(listAdapter)

    }


}
