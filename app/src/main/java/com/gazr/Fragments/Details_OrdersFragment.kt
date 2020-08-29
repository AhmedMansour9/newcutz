package com.gazr.Fragments


import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gazr.Adapter.OrderDetails_Adapter
import com.gazr.Model.MyOrders_Response
import com.gazr.R
import kotlinx.android.synthetic.main.fragment_details__orders.view.*
import kotlinx.android.synthetic.main.fragment_details__orders.view.T_date
import kotlinx.android.synthetic.main.fragment_details__orders.view.T_order_id
import kotlinx.android.synthetic.main.fragment_details__orders.view.T_order_status
import kotlinx.android.synthetic.main.item_myorder.view.*

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
        DataSaver = PreferenceManager.getDefaultSharedPreferences(requireContext())
        UserToken = DataSaver.getString("token", null)!!
        getData()
       getAllProducts()

        return root
    }

    fun getData() {

        bundle = requireArguments()
        if (bundle != null) {
            myOrdersData = bundle.getParcelable("MyOrdersItem")!!
            MyOrderId = myOrdersData.id.toString()
            root.T_order_id.text= myOrdersData.id.toString()
            val orderStatusValue = myOrdersData.paymentStatus

            root.T_date.text=myOrdersData.createdAt
            root.T_FullName.text=myOrdersData.customerName
            root.T_Phone.text=myOrdersData.customerPhone
            root.T_City.text=myOrdersData.customerCity
            root.DelivryPrice.text=myOrdersData.delivery_fees

            val pi = myOrdersData.total!!.toDouble()
            val s = "%.2f".format(pi)
            root.T_Total.text=s + myOrdersData.orderDetails!!.get(0)!!.currency

            if(myOrdersData.status.equals("pendding")){
                root.T_order_status.text=context?.resources?.getString(R.string.pending)
                root.Image_Status.setImageResource(R.drawable.ic_pendingstatus)
            }
            else if(myOrdersData.status.equals("inShipment")){
                root.Image_Status.setImageResource(R.drawable.ic_shipping)
                root.T_order_status.text=context?.resources?.getString(R.string.shipping)


            }

            else if(myOrdersData.status.equals("On arrival")){
                root.Image_Status.setImageResource(R.drawable.ic_shipping)
                root.T_order_status.text=context?.resources?.getString(R.string.onarrival)

            }
            else if(myOrdersData.status.equals("onDelivery")){
                root.Image_Status.setImageResource(R.drawable.ic_deliverystatus)
                root.T_order_status.text=context?.resources?.getString(R.string.ondelivry)

            }
            else if(myOrdersData.status.equals("completed")){
                root.Image_Status.setImageResource(R.drawable.ic_completedstatus)
                root.T_order_status.text=context?.resources?.getString(R.string.completed)

            }
            else if(myOrdersData.status.equals("canceled")){
                root.Image_Status.setImageResource(R.drawable.ic_canceled)
                root.T_order_status.text=context?.resources?.getString(R.string.canceled)

            }

        }

    }
    fun getAllProducts(){

                    val listAdapter =
                        context?.let { OrderDetails_Adapter(it,myOrdersData.orderDetails) }
                    root.recycler_details_list_my_orders.layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    root.recycler_details_list_my_orders.setAdapter(listAdapter)

    }


}
