package com.cutz.ui.subcategories

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.cutz.R
import com.cutz.adapter.CategoryAdapter
import com.cutz.adapter.SubCategoriesAdapter
import com.cutz.base.BaseFragment
import com.cutz.data.remote.model.CategoriesResponse
import com.cutz.data.remote.model.SectonsResponse
import com.cutz.databinding.CategoryFragmentBinding
import com.cutz.databinding.FragmentSubCategoriesMenuBinding
import com.cutz.ui.bottomnavigate.BottomNavigateActivity
import com.cutz.ui.category.CategoriesNavigator
import com.cutz.ui.category.CategoriesViewModel
import com.cutz.ui.nointernet.NoInternertActivity
import com.cutz.utils.SharedData
import com.cutz.utils.isConnected
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