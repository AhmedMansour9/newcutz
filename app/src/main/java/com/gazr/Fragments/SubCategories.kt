package com.gazr.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.gazr.Adapter.Categories_Adapter
import com.gazr.Adapter.SubCategories_Adapter
import com.gazr.EndlessScrollListener
import com.gazr.Model.Blogs_Response
import com.gazr.Model.Categories_Response
import com.gazr.R
import com.gazr.View.ProductBytUd_View
import com.gazr.ViewModel.Categories_ViewModel
import kotlinx.android.synthetic.main.fragment_menu.view.*
import kotlinx.android.synthetic.main.fragment_sub_categories.view.*
import kotlinx.android.synthetic.main.fragment_sub_categories.view.T_TotalCat
import kotlinx.android.synthetic.main.fragment_sub_categories.view.recycler_Categroies

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SubCategories.newInstance] factory method to
 * create an instance of this fragment.
 */
class SubCategories : Fragment() , ProductBytUd_View {
    var toolbarCat: Toolbar?=null
    var my_page = 1
    var hasMorePages:Boolean=true
    lateinit var result_List: MutableList<Categories_Response.Data.Data>
    lateinit var endlessScrollListener: EndlessScrollListener
    lateinit var root:View
    var bundle= Bundle()
    lateinit var details: Categories_Response.Data.Data

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_sub_categories, container, false)

        init()



        return root
    }

    fun init() {
        bundle = requireArguments()
        details = bundle.getParcelable("items")!!
        root.T_SubCategory.text=details.name
        root.T_TotalCat.text="( "+ details.subcategories.size.toString()+" "+resources.getString(R.string.sub_cat)+" )"
        toolbarCat=root.findViewById(R.id.toolbarCat)
       var listAdapter = SubCategories_Adapter(requireContext(), details.subcategories)
        listAdapter.onClick(this)
        root.recycler_Categroies.setLayoutManager(
            GridLayoutManager(
                requireContext()
                , 2
            )
        )
        root.recycler_Categroies.adapter=listAdapter

    }

    override fun Id(categories: Categories_Response.Data.Data) {

    }

    override fun Sub_Id(subcategories: Categories_Response.Data.Data.Subcategory) {
        var productsByid = AllFilterProducts()
        val bundle = Bundle()
        bundle.putString("type", "search")
        bundle.putString("link", "searchProduct")
        bundle.putString("name", null)
        bundle.putString("sub_id", subcategories.id.toString())
        productsByid.arguments = bundle
        Navigation.findNavController(root)
            .navigate(R.id.action_subCategories_to_allFilterProducts, bundle);

    }


}