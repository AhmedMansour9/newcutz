package com.cutzegypt.ui.subcategories

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.cutzegypt.R
import com.cutzegypt.adapter.SubCategoriesAdapter
import com.cutzegypt.base.BaseFragment
import com.cutzegypt.data.remote.model.CategoriesResponse
import com.cutzegypt.data.remote.model.SectonsResponse
import com.cutzegypt.databinding.FragmentSubCategoriesMenuBinding
import com.cutzegypt.ui.category.CategoriesViewModel
import com.cutzegypt.ui.nointernet.NoInternertActivity
import com.cutzegypt.utils.SharedData
import com.cutzegypt.utils.isConnected
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SubCategoriesMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SubCategoriesMenuFragment : BaseFragment<FragmentSubCategoriesMenuBinding>() {

    override var idLayoutRes: Int = R.layout.fragment_sub_categories_menu
    private var data: SharedData? = null
    lateinit var items: CategoriesResponse.Data
    lateinit var bundle: Bundle
    var type: String? = String()
    private val mViewModel: CategoriesViewModel by viewModels()
    var list: MutableList<SectonsResponse.Data.Category.SubCategory>? = mutableListOf()


    private var subCategoryAdapter =
        SubCategoriesAdapter(object : SubCategoriesAdapter.CategoryItemListener {
            override fun itemClicked(productData: SectonsResponse.Data.Category.SubCategory) {
                val bundle = Bundle()
                bundle.putString("type","sub")
                bundle.putParcelable("cat", productData)
                Navigation.findNavController(mViewDataBinding.root)
                    .navigate(R.id.action_subCategoriesMenuFragment_to_productsById, bundle);
            }
        })

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewDataBinding.registerViewModel = mViewModel
        init()
        initRecycle()
        getData()

    }



    private fun getData() {
        bundle = requireArguments()
        items = bundle.getParcelable("data")!!
        items.subCategories.let { subCategoryAdapter.setList(it) }
    }


    private fun initRecycle() {

        mViewDataBinding.recyclerSubCategroies.setHasFixedSize(true)
        mViewDataBinding.recyclerSubCategroies.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        mViewDataBinding.recyclerSubCategroies.adapter = subCategoryAdapter
    }

    private fun init() {
        data = SharedData(requireContext())

    }


    override fun onStart() {
        super.onStart()
        checkInternet()
    }

    fun checkInternet() {
        if (!requireContext().isConnected) {
            startActivity(Intent(requireContext(), NoInternertActivity::class.java))
        }
    }





    fun openProducts(productData: SectonsResponse.Data.Category.SubCategory) {
        val bundle = Bundle()
        bundle.putString("type","sub")
        bundle.putParcelable("cat", productData)
        Navigation.findNavController(mViewDataBinding.root)
            .navigate(R.id.action_T_Categories_to_productsById, bundle);

    }
}