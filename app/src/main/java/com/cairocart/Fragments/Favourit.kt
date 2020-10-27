package com.cairocart.Fragments


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cairocart.Adapter.Favourit_Adapter
import com.cairocart.ChangeLanguage
import com.cairocart.EndlessScrollListener
import com.cairocart.Model.AddFav_Response
import com.cairocart.Model.AllProducts_Response
import com.cairocart.Model.MessageEvent
import com.cairocart.R
import com.cairocart.View.DetailsProduct_id
import com.cairocart.View.ProductDetails_View
import com.cairocart.ViewModel.getAllProducts_ViewModel
import kotlinx.android.synthetic.main.fragment_favourit.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * A simple [Fragment] subclass.
 */
class Favourit : Fragment() , SwipeRefreshLayout.OnRefreshListener, ProductDetails_View,
    DetailsProduct_id {


    var toolbarhome: Toolbar?=null
    var my_page = 1
    var hasMorePages:Boolean=true
    lateinit var result_List: MutableList<AllProducts_Response.Data.Data>
    lateinit var endlessScrollListener: EndlessScrollListener
    lateinit var  listAdapter:Favourit_Adapter

    lateinit var root:View
    private lateinit var DataSaver: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        root= inflater.inflate(R.layout.fragment_favourit, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(requireContext())
        result_List=  mutableListOf<AllProducts_Response.Data.Data>()

        init()
        SwipRefresh()
          initScroll()

        return root
    }

    fun initScroll(){

        endlessScrollListener = object : EndlessScrollListener(root.recycler_Favourits.layoutManager!!) {
            override fun onLoadMore(currentPage: Int, totalItemCount: Int) {

                if (hasMorePages) {
                    my_page++
                    getFavouritProducts(my_page)
                }


            }

            override fun onScroll(firstVisibleItem: Int, dy: Int, scrollPosition: Int) {}
        }
    }
    fun init() {
        toolbarhome=root.findViewById(R.id.toolbarFavourit)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(requireContext())
         listAdapter =
            Favourit_Adapter(result_List)
        listAdapter.onClick(this)
        listAdapter.onClickFavourit(this)
        root.recycler_Favourits.setLayoutManager(
            GridLayoutManager(
                requireContext()
                , 2
            )
        )
        root.recycler_Favourits.setAdapter(listAdapter)

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
            getFavouritProducts(my_page)
        })
    }

    fun getFavouritProducts( page:Int){
        var allproducts: getAllProducts_ViewModel =  ViewModelProvider.NewInstanceFactory().create(
                getAllProducts_ViewModel::class.java)
        context?.let { ChangeLanguage.getLanguage(it) }?.let {
            allproducts.getLatest(
                page.toString(), it
                ,"customer/favoirtes",DataSaver.getString("token",null))
                .observe(viewLifecycleOwner, Observer<AllProducts_Response> { loginmodel ->
                    root.SwipFavourits.isRefreshing=false

                    if(loginmodel!=null) {
                        root.T_TotalFav.text="( "+loginmodel.data!!.data!!.size.toString()+" "+resources.getString(R.string.product)+" )"
                        hasMorePages= loginmodel.data?.meta?.hasMorePages!!
                        result_List.addAll(loginmodel.data!!.data)
                        listAdapter.notifyItemRangeInserted(
                            listAdapter.getItemCount(),
                            result_List.size
                        )
                        //  Toast.makeText(this,"pop"+result_List.size,Toast.LENGTH_LONG).show()
                        root.recycler_Favourits.addOnScrollListener(endlessScrollListener)


                    }else {
                        root.T_Nofavourit.visibility=View.VISIBLE
                        root.recycler_Favourits.visibility=View.GONE
                        root.T_TotalFav.visibility=View.GONE
                    }
                })
        }

    }


    override fun Details(detailsProduct: AllProducts_Response.Data.Data) {
    }
    override fun DetailsProducts(detailsProduct: AllProducts_Response.Data.Data) {
//        var productsByid=Product_Details()
        val bundle = Bundle()
        bundle.putString("type","true")
        bundle.putParcelable("ProductItem", detailsProduct)
        Navigation.findNavController(root).navigate(R.id.action_favourit2_to_product_Details,bundle);

    }

    override fun onRefresh() {
        result_List.clear()
        my_page=1
        listAdapter.notifyDataSetChanged()
        initScroll()
        getFavouritProducts(my_page)
    }

    override fun AddToFavourit(api:String,Productid: String) {
        var allproducts: getAllProducts_ViewModel =  ViewModelProvider.NewInstanceFactory().create(
                getAllProducts_ViewModel::class.java)
        root.SwipFavourits.isRefreshing=true
        requireContext()?.let {
            allproducts.getAddFavouritProducts(api,Productid,
                    DataSaver.getString("token",null)!!).observe(viewLifecycleOwner, Observer<AddFav_Response> {
                my_page=1
                result_List.clear()
                listAdapter.notifyDataSetChanged()
                initScroll()
                getFavouritProducts(my_page)

            })
        }

    }

    override fun AddToFavouritCart(api: String, Productid: String) {

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
}
