package com.cutzegypt.ui.cart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cutzegypt.R
import com.cutzegypt.adapter.CartAdapter
import com.cutzegypt.base.BaseFragment
import com.cutzegypt.data.remote.model.ListCartResponse
import com.cutzegypt.data.remote.model.MessageEvent
import com.cutzegypt.databinding.FragmentCartBinding
import com.cutzegypt.ui.bottomnavigate.BottomNavigateActivity
import com.cutzegypt.ui.createorder.CreateOrderFragment
import com.cutzegypt.utils.SharedData
import com.cutzegypt.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    override var idLayoutRes: Int = R.layout.fragment_cart
    private var data: SharedData? = null
    private var token: String? = String()
    private lateinit var cartAdapter: CartAdapter
    private lateinit var id:String
    private var quta_id: String? = String()
    private lateinit var listCart:ListCartResponse.Data

    val mViewModel: CartViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initRecycle()
        getCart()
        openHome()
        subscribeCart()
        subscribeEditCart()
        subscribeDeleteCart()
        checkOut()

    }

    private fun checkOut() {
        mViewDataBinding.BtnOrder.setOnClickListener(){
            onCLickCheckOut()
        }


    }

    private fun initRecycle() {
        cartAdapter= CartAdapter(requireContext() ,object :CartAdapter.CartItemListner{
            override fun ediItem(item_id:String, qty: String) {
                 mViewModel.editCart(
                        token,item_id,qty)
            }

            override fun deleteItem(item:String) {
                    mViewModel.deleteCart(item,token)

            }

        })
        mViewDataBinding.recyclerProductCart.setHasFixedSize(true)
        mViewDataBinding.recyclerProductCart.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.recyclerProductCart.adapter = cartAdapter
    }

    fun getCart() {
        EventBus.getDefault().postSticky(MessageEvent("cart"))
        token = data?.getValue(SharedData.ReturnValue.STRING, "token")
        mViewModel.getCart(token!!)


    }
    private fun isLogin(): Boolean {
        token = data?.getValue(SharedData.ReturnValue.STRING, "token")
        if (token.isNullOrEmpty()) {
            return false
        }
        return true
    }


    private fun init() {
        mViewDataBinding.productsViewModel = mViewModel
        mViewDataBinding.shimmerLayout.startShimmerAnimation()
        mViewDataBinding.SwipCart.setOnRefreshListener(this)
        data = SharedData(requireContext())
        token = data?.getValue(SharedData.ReturnValue.STRING, "token")

    }




    private fun subscribeCart() {
        mViewModel.cartResponse.observe(viewLifecycleOwner, Observer {
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
                    showEmptyCart()
                }
            }
        })
    }

    override fun onRefresh() {
        getCart()
    }

    fun checkToken(): Boolean {
        quta_id=data?.getValue(SharedData.ReturnValue.STRING, "quta_id")
        token = data?.getValue(SharedData.ReturnValue.STRING, "token")

        if (token.isNullOrEmpty()) {
            return false
        }
        return true
    }


    fun setData(cartResponse: ListCartResponse) {
        if(cartResponse.data?.cartItems!!.size>0)
        {
            listCart= cartResponse.data!!

//            id=cartResponse.data!!..toString()
            mViewDataBinding.TotalProducts.text=cartResponse.data?.cartItems!!.size.toString() +" "+resources.getString(R.string.product)
            hideEmptyCart()
            mViewDataBinding.model=cartResponse.data
            cartAdapter.setDeveloperList(cartResponse.data?.cartItems as MutableList<ListCartResponse.Data.CartItem>)
        }else
        {
            showEmptyCart()
        }

    }
    private fun showEmptyCart(){
        mViewDataBinding.RelaCart.isVisible = false
        mViewDataBinding.RelaEmpty.isVisible = true
    }
    private fun hideEmptyCart(){
        mViewDataBinding.RelaCart.isVisible = true
        mViewDataBinding.RelaEmpty.isVisible = false

    }




    fun openHome(){
        mViewDataBinding.BtnGoHome.setOnClickListener(){
            val intent= Intent(requireContext(), BottomNavigateActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }


    private fun subscribeEditCart() {
        mViewModel.editCartResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    getCart()
                }
                Status.LOADING -> {
                    showLoading()

                }

                Status.ERROR -> {
                    dismissLoading()


                }
            }
        })
    }


    private fun subscribeDeleteCart() {
        mViewModel.deleteCartResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    EventBus.getDefault().postSticky(MessageEvent("cart"))
                    getCart()
                }
                Status.LOADING -> {
                    showLoading()

                }

                Status.ERROR -> {
                   dismissLoading()

                }
            }
        })
    }

    private fun onCLickCheckOut(){
        val newDialogFragment = CreateOrderFragment()
        val bundle = Bundle()
        bundle.putParcelable("data",listCart)
        newDialogFragment.setArguments(bundle)
        val transaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        newDialogFragment.show(transaction, "New_Dialog_Fragment")
    }



}