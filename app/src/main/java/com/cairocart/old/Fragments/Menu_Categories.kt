package com.cairocart.old.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cairocart.ChangeLanguage
import com.cairocart.old.Model.Categories_Response
import com.cairocart.R
import com.cairocart.old.Model.CatModel
import com.cairocart.old.ViewModel.Categories_ViewModel
import com.cairocart.old.treeview.Node
import com.cairocart.old.treeview.TreeItemController
import com.cairocart.old.treeview.toTree
import kotlinx.android.synthetic.main.fragment_menu.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi


/**
 * A simple [Fragment] subclass.
 */
@ExperimentalCoroutinesApi
class Menu_Categories : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    var toolbarCat: Toolbar? = null
    var my_page = 1
    var hasMorePages: Boolean = true
    lateinit var result_List: MutableList<Categories_Response.DataCategory.ChildrenDataa>
    lateinit var root: View



    private val controller = TreeItemController(::onCatModelClicked)

    private fun onCatModelClicked(node: Node<CatModel>) {
        node.value = node.value.copy(isExpanded = !node.value.isExpanded)
        tree?.let {
            controller.setData(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
// Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_menu, container, false)
        result_List = mutableListOf<Categories_Response.DataCategory.ChildrenDataa>()
        SwipRefresh()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        root.treeItemsRv.setController(controller)

    }

    var tree: Node<CatModel>? = null
    private fun addData(data: Categories_Response.DataCategory?) {
        tree = data?.toTree()
        tree?.let {
            controller.setData(it)
        }
    }


//    fun initScroll() {
//        endlessScrollListener =
//            object : EndlessScrollListener(root.treeItemsRv.layoutManager!!) {
//                override fun onLoadMore(currentPage: Int, totalItemCount: Int) {
//
//                    if (hasMorePages) {
//                        my_page++
//                        getAllCategories()
//                    }
//
//
//                }
//
//                override fun onScroll(firstVisibleItem: Int, dy: Int, scrollPosition: Int) {}
//            }
//    }

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
        val categories: Categories_ViewModel by viewModels()

        requireContext()?.let {
            categories.getCategories(ChangeLanguage.getLanguage(it), it).observe(
                viewLifecycleOwner,
                { loginmodel ->
                    root.SwipCategories.isRefreshing = false
                    if (loginmodel != null) {
                        if (loginmodel.status?.code == 200) {
                            addData(loginmodel.data)

                        }
                    }
                })
        }
    }

    override fun onRefresh() {
        getAllCategories()
    }




}
