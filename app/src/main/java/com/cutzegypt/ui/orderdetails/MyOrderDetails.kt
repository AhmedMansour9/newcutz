package com.cutzegypt.ui.orderdetails

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.cutzegypt.R
import com.cutzegypt.adapter.CartOrderDetailsAdapter
import com.cutzegypt.base.BaseDialogFragment
import com.cutzegypt.data.remote.model.MyOrdersResponse
import com.cutzegypt.databinding.FragmentMyOrderDetailsBinding
import com.cutzegypt.ui.createorder.CreateOrderViewModel
import com.cutzegypt.utils.SharedData
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyOrderDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MyOrderDetails : BaseDialogFragment<FragmentMyOrderDetailsBinding>() {
    private var token: String? = String()
    override var idLayoutRes: Int=R.layout.fragment_my_order_details
    private lateinit var listCart: MyOrdersResponse.Data
    private lateinit var Email:String

    private var data: SharedData? = null
    private var quta_id: String? = String()
    private lateinit var cartAdapter: CartOrderDetailsAdapter

    val mViewModel: CreateOrderViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        initRecycle()
        getData()
        finish()

    }

    private fun finish() {
        mViewDataBinding.ImgClose.setOnClickListener(){
            dismiss()
        }
    }


    private fun init() {
        data = SharedData(requireContext())
        token = data?.getValue(SharedData.ReturnValue.STRING, "token")
        quta_id=data?.getValue(SharedData.ReturnValue.STRING, "quta_id")
    }

    @SuppressLint("SetTextI18n")
    private fun getData() {
        var bundle: Bundle? = Bundle()
        bundle = this.arguments
        listCart = bundle?.getParcelable("list")!!
        Email= listCart.customerEmail.toString()
        mViewDataBinding.model=listCart
        mViewDataBinding.TEmail.text=listCart.customerEmail
        mViewDataBinding.TPhone.text=listCart.customerPhone
        cartAdapter.setDeveloperList(listCart.orderDetials)
        setImageStatus(listCart.status.toString())
    }

    private fun initRecycle() {
//        cartAdapter = CartOrderDetailsAdapter(requireContext())
//        mViewDataBinding.orderRecyclerView.setHasFixedSize(true)
//        mViewDataBinding.orderRecyclerView.layoutManager =
//            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
//        mViewDataBinding.orderRecyclerView.adapter = cartAdapter
    }
    private fun setImageStatus(status:String){

        if(status.equals("pending")){
//            root.T_order_status.text=context?.resources?.getString(R.string.pending)
            mViewDataBinding.ImageStatus.setImageResource(R.drawable.ic_pendingstatus)
        }
        else if(status.equals("inShipment")){
            mViewDataBinding.ImageStatus.setImageResource(R.drawable.ic_shipping)
//            root.T_order_status.text=context?.resources?.getString(R.string.shipping)
        }
        else if(status.equals("On arrival")){
            mViewDataBinding.ImageStatus.setImageResource(R.drawable.ic_arrival)
//            root.T_order_status.text=context?.resources?.getString(R.string.onarrival)
        }
        else if(status.equals("onDelivery")){
            mViewDataBinding.ImageStatus.setImageResource(R.drawable.ic_deliverystatus)
//            root.T_order_status.text=context?.resources?.getString(R.string.ondelivry)

        }
        else if(status.equals("completed")){
            mViewDataBinding.ImageStatus.setImageResource(R.drawable.ic_completedstatus)
//            root.T_order_status.text=context?.resources?.getString(R.string.completed)

        }
        else if(status.equals("canceled")){
            mViewDataBinding.ImageStatus.setImageResource(R.drawable.ic_canceled)
//            root.T_order_status.text=context?.resources?.getString(R.string.canceled)

        }

    }
}