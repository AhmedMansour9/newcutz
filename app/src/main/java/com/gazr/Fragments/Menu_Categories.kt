package com.gazr.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.gazr.Adapter.Categories_Adapter
import com.gazr.ChangeLanguage
import com.gazr.EndlessScrollListener
import com.gazr.Model.Categories_Response
import com.gazr.R
import com.gazr.View.ProductBytUd_View
import com.gazr.ViewModel.Categories_ViewModel
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
            Categories_Adapter(requireContext(),
                result_List as List<Categories_Response.Data.Data>
            )
        listAdapter.onClick(this)
        root.recycler_Categroies.setLayoutManager(
            GridLayoutManager(
                requireContext()
                , 3
            )
        )
        root.recycler_Categroies.adapter=listAdapter

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
        requireContext()?.let {
            categories.getCategories(page.toString(), ChangeLanguage.getLanguage(it),it).observe(viewLifecycleOwner, Observer<Categories_Response> { loginmodel ->
                root.SwipCategories.isRefreshing=false
                if(loginmodel!=null) {
                    hasMorePages= loginmodel.data?.meta?.hasMorePages!!
                    root.T_TotalCat.text="( "+ loginmodel.data!!.meta!!.total.toString()+" "+resources.getString(R.string.menu)+" )"
                    result_List.addAll(loginmodel.data!!.data)
                    listAdapter.notifyItemRangeInserted(
                        listAdapter.getItemCount(),
                        result_List.size
                    )
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
        Navigation.findNavController(root).navigate(R.id.action_T_Categories_to_allFilterProducts,bundle);
    }

    override fun Sub_Id(categories: Categories_Response.Data.Data.Subcategory) {

    }


}
