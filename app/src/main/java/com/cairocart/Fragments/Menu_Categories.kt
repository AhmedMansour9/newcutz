package com.cairocart.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cairocart.Adapter.*
import com.cairocart.ChangeLanguage
import com.cairocart.EndlessScrollListener
import com.cairocart.Model.Categories_Response
import com.cairocart.R
import com.cairocart.View.ProductBytUd_View
import com.cairocart.ViewModel.Categories_ViewModel
import com.cairocart.domain.Category
import com.cairocart.treeview.Node
import com.cairocart.treeview.TreeItemController
import com.cairocart.treeview.toTree
import kotlinx.android.synthetic.main.fragment_menu.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
@ExperimentalCoroutinesApi
class Menu_Categories : Fragment(), SwipeRefreshLayout.OnRefreshListener, ProductBytUd_View {

    var toolbarCat: Toolbar? = null
    var my_page = 1
    var hasMorePages: Boolean = true
    lateinit var result_List: MutableList<Categories_Response.Data.ChildrenData>
    lateinit var endlessScrollListener: EndlessScrollListener
    lateinit var listAdapter: Categories_Adapter
    lateinit var root: View


    private val controller = TreeItemController(::onCategoryClicked)

    private fun onCategoryClicked(node: Node<Category>) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_menu, container, false)
        result_List = mutableListOf<Categories_Response.Data.ChildrenData>()
        init()
        SwipRefresh()
//         initScroll()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       root.treeItemsRv.setController(controller)

    }
    private fun addData(data: Categories_Response.Data?) {
        val tree = data?.toTree()
        tree?.let {
            controller.setData(it)
        }
    }

    fun init() {
        toolbarCat = root.findViewById(R.id.toolbarCat)
        listAdapter =
            Categories_Adapter(
                requireContext(),
                result_List as List<Categories_Response.Data.ChildrenData>
            )
        listAdapter.onClick(this)
//        root.recycler_Categroies.setLayoutManager(
//            GridLayoutManager(
//                requireContext(), 3
//            )
//        )
//        root.recycler_Categroies.adapter=listAdapter

    }

    fun initScroll() {
        endlessScrollListener =
            object : EndlessScrollListener(root.recycler_Categroies.layoutManager!!) {
                override fun onLoadMore(currentPage: Int, totalItemCount: Int) {

                    if (hasMorePages) {
                        my_page++
                        getAllCategories()
                    }


                }

                override fun onScroll(firstVisibleItem: Int, dy: Int, scrollPosition: Int) {}
            }
    }

    fun SwipRefresh() {
        root.SwipCategories.setOnRefreshListener(this)
        root.SwipCategories.isRefreshing = true
        root.SwipCategories.isEnabled = true
        root.SwipCategories.setColorSchemeResources(
            R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark
        )
        root.SwipCategories.post(Runnable {
            getAllCategories()
        })
    }

    fun getAllCategories() {
        var categories: Categories_ViewModel = ViewModelProvider.NewInstanceFactory().create(
            Categories_ViewModel::class.java
        )
        requireContext()?.let {
            categories.getCategories(ChangeLanguage.getLanguage(it), it).observe(
                viewLifecycleOwner,
                Observer<Categories_Response> { loginmodel ->
                    root.SwipCategories.isRefreshing = false
                    if (loginmodel != null) {
                        if (loginmodel.status?.code == 200) {
                            addData(loginmodel.data)
//                        root.T_TotalCat.text =
//                            "( " + loginmodel.data!!.meta!!.total.toString() + " " + resources.getString(
//                                R.string.menu
//                            ) + " )"
//                        result_List.addAll(loginmodel.data!!.data)
//                        listAdapter.notifyItemRangeInserted(
//                            listAdapter.getItemCount(),
//                            result_List.size
//                        )
//                        root.recycler_Categroies.addOnScrollListener(endlessScrollListener)

                        }
                    }
                })
        }
    }

    override fun onRefresh() {
        getAllCategories()
    }

    override fun Id(categories: Categories_Response.Data.ChildrenData) {
//        if(categories.subcategories.size>0){
//            var productsByid = SubCategories()
//            val bundle = Bundle()
//            bundle.putParcelable("items", categories)
//            productsByid.arguments = bundle
//            Navigation.findNavController(root)
//                .navigate(R.id.action_T_Categories_to_subCategories, bundle);
//        }else {
//            var productsByid = AllFilterProducts()
//            val bundle = Bundle()
//            bundle.putString("type", "search")
//            bundle.putString("link", "searchProduct")
//            bundle.putString("name", null)
//            bundle.putString("id", categories.id.toString())
//            productsByid.arguments = bundle
//            Navigation.findNavController(root)
//                .navigate(R.id.action_T_Categories_to_subCategories, bundle);
//        }

    }

    override fun Sub_Id(categories: Categories_Response.Data.ChildrenData) {

    }


}
