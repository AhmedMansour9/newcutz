package com.mgh.Fragments


import android.content.Context
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
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import com.mgh.Activities.Navigation
import com.mgh.Adapter.Favourit_Adapter
import com.mgh.ChangeLanguage
import com.mgh.Model.*
import com.mgh.R
import com.mgh.SharedPrefManager
import com.mgh.View.DetailsProduct_id
import com.mgh.View.ProductDetails_View
import com.mgh.ViewModel.getAllProducts_ViewModel
import kotlinx.android.synthetic.main.fragment_menu__meals.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * A simple [Fragment] subclass.
 */
class Menu_Meals : Fragment(), ProductDetails_View, DetailsProduct_id {
    override fun DetailsProducts(detailsProduct: AllProducts_Response.Data.Data) {
        var productsByid=Product_Details()
        val bundle = Bundle()
        bundle.putString("type",null)
        bundle.putParcelable("ProductItem", detailsProduct)
        productsByid.arguments=bundle
        activity!!.supportFragmentManager.beginTransaction().add(R.id.RelaMenu, productsByid)
            .addToBackStack(null).commit()

    }
    var toolbarhome: Toolbar?=null

    lateinit var root:View
    var bundle=Bundle()
    lateinit var categories: Categories_Response.Data.Data
    private lateinit var DataSaver: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_menu__meals, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
        init()

        getFilterProducts()


        return root
    }

    fun getFilterProducts(){
        bundle = this!!.arguments!!
        categories = bundle.getParcelable("ProductItem")!!
//        root.T_AllProduct.text=categories.title
        var allproducts: getAllProducts_ViewModel =  ViewModelProvider.NewInstanceFactory().create(
                getAllProducts_ViewModel::class.java)

        this.context!!.applicationContext?.let {
            allproducts.getProductsId(
                ChangeLanguage.getLanguage(it)+"/"+ SharedPrefManager.getInstance(context).currncy
                        +"/"+"category"+"/"+categories.id.toString(),DataSaver.getString("token",null)!!, it)?.observe(viewLifecycleOwner, Observer<AllProducts_Response> { loginmodel ->
                if(loginmodel!=null) {
                    val listAdapter =
                        Favourit_Adapter(loginmodel.data?.data
                                as List<AllProducts_Response.Data.Data>)
                    listAdapter.onClickFavourit(this)
                    listAdapter.onClick(this)
                    root.recycler_Meals.setAdapter(listAdapter)

                    root.recycler_Meals.setLayoutManager(
                        GridLayoutManager(
                            context!!.applicationContext
                            , 2
                        )
                    )
                    root.recycler_Meals.setAdapter(listAdapter)

                }

            })
        }
    }

    override fun Details(detailsProduct: AllProducts_Response.Data.Data) {
    }

    override fun AddToFavourit(api:String,Productid: String) {
        var allproducts: getAllProducts_ViewModel =  ViewModelProvider.NewInstanceFactory().create(
                getAllProducts_ViewModel::class.java)
        this.context!!.applicationContext?.let {
            allproducts.getAddFavouritProducts(api,Productid,
                    DataSaver.getString("token",null)!!, it).observe(viewLifecycleOwner, Observer<AddFav_Response> { loginmodel ->

            })
        }

    }

    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {/* Do something */
        getFilterProducts()

    };
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

    }

    fun init() {
        toolbarhome=root.findViewById(R.id.toolbarMenu)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)

        val toggle = ActionBarDrawerToggle(
            activity,
            Navigation.drawerLayout,
            root.toolbarMenu,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.apply {
            syncState()
            isDrawerIndicatorEnabled = false
            setHomeAsUpIndicator(R.drawable.ic_homemenu)
            setToolbarNavigationClickListener { Navigation.drawerLayout!!.openDrawer(GravityCompat.START) }
        }
        Navigation.drawerLayout?.addDrawerListener(toggle)

    }
}
