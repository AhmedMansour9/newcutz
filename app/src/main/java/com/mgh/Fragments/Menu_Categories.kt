package com.mgh.Fragments


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
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mgh.Activities.Navigation
import com.mgh.Adapter.Categories_Adapter
import com.mgh.ChangeLanguage
import com.mgh.EndlessScrollListener
import com.mgh.Model.Categories_Response
import com.mgh.R
import com.mgh.SharedPrefManager
import com.mgh.View.ProductBytUd_View
import com.mgh.ViewModel.Categories_ViewModel
import kotlinx.android.synthetic.main.fragment_menu.view.*

/**
 * A simple [Fragment] subclass.
 */
class Menu_Categories : Fragment(), SwipeRefreshLayout.OnRefreshListener, ProductBytUd_View {

    var toolbarCat: Toolbar?=null
    var my_page = 1
    var hasMorePages:Boolean=true
    lateinit var result_List: MutableList<Categories_Response.Data.Data>
    lateinit var endlessScrollListener: EndlessScrollListener
     lateinit var  listAdapter:Categories_Adapter
    lateinit var root:View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_menu, container, false)
        result_List=  mutableListOf<Categories_Response.Data.Data>()

        init()
        SwipRefresh()
         initScroll()
        return root
    }

    fun init() {
        toolbarCat=root.findViewById(R.id.toolbarCat)
         listAdapter =
            Categories_Adapter(context!!.applicationContext,
                result_List as List<Categories_Response.Data.Data>
            )
        listAdapter.onClick(this)
        root.recycler_Categroies.setLayoutManager(
            GridLayoutManager(
                context!!.applicationContext
                , 2
            )
        )
        root.recycler_Categroies.adapter=listAdapter
        val toggle = ActionBarDrawerToggle(
            activity,
            Navigation.drawerLayout,
            toolbarCat,
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

    fun initScroll(){
        endlessScrollListener = object : EndlessScrollListener(root.recycler_Categroies.layoutManager!!) {
            override fun onLoadMore(currentPage: Int, totalItemCount: Int) {

                    if (hasMorePages) {
                        my_page++
                        getAllCategories(my_page)
                    }


        }

        override fun onScroll(firstVisibleItem: Int, dy: Int, scrollPosition: Int) {}
    }
}

    fun SwipRefresh(){
        root.SwipCategories.setOnRefreshListener(this)
        root.SwipCategories.isRefreshing=true
        root.SwipCategories.isEnabled = true
        root.SwipCategories.setColorSchemeResources(
                R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark
        )
        root.SwipCategories.post(Runnable {
           getAllCategories(my_page)
        })
    }

    fun getAllCategories( page:Int){
        var categories: Categories_ViewModel =  ViewModelProvider.NewInstanceFactory().create(Categories_ViewModel::class.java)
        this.context!!.applicationContext?.let {
            categories.getCategories(page.toString(), it).observe(viewLifecycleOwner, Observer<Categories_Response> { loginmodel ->
                root.SwipCategories.isRefreshing=false
                if(loginmodel!=null) {
                    hasMorePages= loginmodel.data?.meta?.hasMorePages!!
                    root.T_TotalCat.text="( "+ loginmodel.data!!.meta!!.total.toString()+" "+resources.getString(R.string.menu)+" )"
                    result_List.addAll(loginmodel.data!!.data)
                    listAdapter.notifyItemRangeInserted(
                        listAdapter.getItemCount(),
                        result_List.size
                    )
                    //  Toast.makeText(this,"pop"+result_List.size,Toast.LENGTH_LONG).show()
                    root.recycler_Categroies.addOnScrollListener(endlessScrollListener)

                }
            })
        }
    }

    override fun onRefresh() {
        result_List.clear()
        my_page=1
        listAdapter.notifyDataSetChanged()
        initScroll()
        getAllCategories(my_page)
    }

    override fun Id(categories: Categories_Response.Data.Data) {
        var productsByid = AllFilterProducts()
        val bundle = Bundle()
        bundle.putString("type", "search")
        bundle.putString("link", "searchProduct")
        bundle.putString("name", null)
        bundle.putString("id", categories.id.toString())

        productsByid.arguments = bundle
        activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_Home, productsByid)
            .addToBackStack(null).commit()
    }


}
