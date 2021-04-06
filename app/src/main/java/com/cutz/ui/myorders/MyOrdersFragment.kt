package com.cutz.ui.myorders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cutz.R
import com.cutz.adapter.MyOrdersAdapter
import com.cutz.base.BaseFragment
import com.cutz.data.remote.model.MyOrdersResponse
import com.cutz.databinding.FragmentMyOrders2Binding
import com.cutz.ui.orderdetails.MyOrderDetails
import com.cutz.utils.SharedData
import com.cutz.utils.Status
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyOrdersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class MyOrdersFragment : BaseFragment<FragmentMyOrders2Binding>() {

    override var idLayoutRes: Int = R.layout.fragment_my_orders2
    var bundle = Bundle()
    private var data: SharedData? = null
    private var token: String? = String()
    private lateinit var myordersAdapter :MyOrdersAdapter

    val mViewModel: MyOrdersViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initAdapter()
        initRecycle()
        getOrders()
        subscribeMyOrder()

    }

    private fun initAdapter() {
        myordersAdapter=  MyOrdersAdapter(requireContext(),object : MyOrdersAdapter.CartItemListner {
            override fun onclick(list: MyOrdersResponse.Data) {
//                val newDialogFragment = MyOrderDetails()
//                var bundle=Bundle()
//                bundle.putParcelable("list",list)
//                newDialogFragment.arguments=bundle
//                val transaction: FragmentTransaction =
//                    requireActivity().supportFragmentManager.beginTransaction()
//                newDialogFragment.show(transaction, "New_Dialog_Fragment")
            }
        })

    }

    fun getOrders() {
        token = data?.getValue(SharedData.ReturnValue.STRING, "token")
        mViewModel.getOrders(token)


    }

    private fun init() {
        data = SharedData(requireContext())
    }

    private fun initRecycle() {
        mViewDataBinding.recyclerProductsGrid.setHasFixedSize(true)
        mViewDataBinding.recyclerProductsGrid.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.recyclerProductsGrid.adapter = myordersAdapter
    }

    private fun subscribeMyOrder() {
        mViewModel.ordersResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    it.data?.let { it1 -> setData(it1) }
                }
                Status.LOADING -> {
                    showLoading()
                }
                Status.ERROR -> {
                   dismissLoading()
//                    if(it.message.equals("404")){
//                        showEmptyCart()
//                    }
                }
            }
        })
    }

    private fun setData(it1: MyOrdersResponse) {
        if(it1.data.size>0){
            it1.data.reverse()
            myordersAdapter.setList(it1.data)
        }else {
            showEmptyPage()
        }

    }


    fun showEmptyPage(){
        mViewDataBinding.RelaEmpty.visibility= View.VISIBLE
        mViewDataBinding.recyclerProductsGrid.visibility=View.GONE

    }

}