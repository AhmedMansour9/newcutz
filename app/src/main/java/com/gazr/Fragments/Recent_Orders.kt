package com.gazr.Fragments


import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gazr.Activities.NavigationDrawer
import com.gazr.Adapter.MyOrders_Adapter
import com.gazr.ChangeLanguage
import com.gazr.Model.MyOrders_Response
import com.gazr.R
import com.gazr.View.DetailsMyOrdersView
import com.gazr.ViewModel.MyOrders_ViewModel
import kotlinx.android.synthetic.main.fragment_my_orders.view.*

/**
 * A simple [Fragment] subclass.
 */
class Recent_Orders : Fragment() , DetailsMyOrdersView {

    var toolbarhome: Toolbar?=null

    lateinit var root:View
     var UserToken: String?= String()
    private lateinit var DataSaver: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_my_orders, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(requireContext())
        UserToken = DataSaver.getString("token", null)
//        init()/

        getAllOrders()


        return root
    }

    fun init() {
        toolbarhome=root.findViewById(R.id.toolbarorders)

        val toggle = ActionBarDrawerToggle(
            activity,
            NavigationDrawer.drawerLayout,
            toolbarhome,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.apply {
            syncState()
            isDrawerIndicatorEnabled = false
            setHomeAsUpIndicator(R.drawable.ic_homemenu)
            setToolbarNavigationClickListener { NavigationDrawer.drawerLayout!!.openDrawer(GravityCompat.START) }
        }
        NavigationDrawer.drawerLayout?.addDrawerListener(toggle)

    }

    fun getAllOrders(){
        var  allproducts = ViewModelProvider.NewInstanceFactory().create(
            MyOrders_ViewModel::class.java)
        requireContext()?.let {
            allproducts.getData(ChangeLanguage.getLanguage(it),"customer/orders",UserToken, it).observe(viewLifecycleOwner, Observer<MyOrders_Response> { loginmodel ->
                if(loginmodel!=null) {
//                    root.view3.visibility=View.VISIBLE
                    val listAdapter =
                        MyOrders_Adapter(loginmodel.data as List<MyOrders_Response.Data>)
                    listAdapter.OnClick(this)
                    root.Recycle_Order.layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    root.Recycle_Order.setAdapter(listAdapter)
                }else {
                    var status:Boolean=allproducts.getStatus()
                    if(status){
                        root.Recycle_Order.visibility=View.GONE
                        root.T_NoOrder.visibility=View.VISIBLE
                    }

                }

            })
        }
    }

    override fun showDetailsMyOrders(myOrdersData: MyOrders_Response.Data) {
        val detailsMyOrdersFragment = Details_OrdersFragment()
        val bundle = Bundle()
        bundle.putParcelable("MyOrdersItem", myOrdersData)
        detailsMyOrdersFragment.setArguments(bundle)
        Navigation.findNavController(root).navigate(R.id.action_recent_Orders_to_details_OrdersFragment,bundle);

    }


}
