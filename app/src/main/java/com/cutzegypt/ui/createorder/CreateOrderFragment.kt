package com.cutzegypt.ui.createorder

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cutzegypt.R
import com.cutzegypt.adapter.CartOrderAdapter
import com.cutzegypt.adapter.ShippingMethodAdapter
import com.cutzegypt.base.BaseDialogFragment
import com.cutzegypt.data.remote.model.*
import com.cutzegypt.databinding.FragmentCreateOrderBinding
import com.cutzegypt.ui.coupon.AddCouponActivity
import com.cutzegypt.ui.editprofile.EditProfileViewModel
import com.cutzegypt.ui.payonline.PayonlineActivity
import com.cutzegypt.ui.successorder.SuccessOrder
import com.cutzegypt.utils.SharedData
import com.cutzegypt.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateOrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class CreateOrderFragment : BaseDialogFragment<FragmentCreateOrderBinding>() {
    private var token: String? = String()
    private var data: SharedData? = null
    private var country_Id: String? = String()
    override var idLayoutRes: Int = R.layout.fragment_create_order
    var listCities: MutableList<CountriesResponse.Data.City>? = arrayListOf()
    var listStates: MutableList<CountriesResponse.Data.City.State>? = arrayListOf()
    private lateinit var cartAdapter: CartOrderAdapter
    private lateinit var listCart: ListCartResponse.Data
    var TotalOrder: Double = 0.0
    var DeliveryPrice: Double = 0.0
    var Currency: String? = String()
    var city_Id: String? = String()
    var state_Id: String? = String()
    var code: String? = String()
    var city_Name: String? = String()
    var f_Name: String? = String()
    var state_Name: String? = String()
    var phone: String? = String()
    var Email: String? = String()
    var Address: String? = String()
    var paymentMethod= "cach"
    var Country: String? = String()
    var ShippingPrice: String? = String()
    var Discount: Double=0.0

    val mViewModel: CreateOrderViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }
    private val mViewModel2: EditProfileViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }
    private var shipAdapter =
        ShippingMethodAdapter(object : ShippingMethodAdapter.ShippingMethodItemListener {
            override fun itemClicked(productData: ListShippingMethod.Data) {
//                cat_Id=productData.id.toString()
//                mViewDataBinding.TCategory.text=productData.name
            }

        })


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        initRecycle()
        getData()
        subscribeCounrties()
        setupObserverProfile()
        setupObserverOrders()
        onClickCheckOut()
        onClickAddCoupon()
        onClickRadioButton()


    }

    private fun onClickAddCoupon() {
        mViewDataBinding.AddCoupon.setOnClickListener(){
            startActivity(Intent(requireContext(),AddCouponActivity::class.java))
        }
    }

    private fun setupObserverOrders() {
        mViewModel.createOrderResponse.observe(this, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    var intent=Intent(requireContext(),SuccessOrder::class.java)
                    intent.putExtra("id",it.data?.data?.id.toString())
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    activity?.finish()
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

    private fun setupObserverProfile() {
        mViewModel2.getProfileResponse.observe(this, Observer {
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
                }
            }
        })
    }

    fun setData(profile: ProfileResponse) {
        mViewDataBinding.EFullName.editText?.setText(profile.data?.name.toString())
        mViewDataBinding.EPhone?.setText(profile.data?.phone.toString())
        mViewDataBinding.EEmail?.setText(profile.data?.email.toString())
    }

    private fun getData() {
        var bundle: Bundle? = Bundle()
        bundle = this.arguments
        listCart = bundle?.getParcelable("data")!!
        cartAdapter.setDeveloperList(listCart?.cartItems as MutableList<ListCartResponse.Data.CartItem>)
        mViewDataBinding.TotalProducts.text =
            listCart.cartItems!!.size.toString() + " " + resources.getString(R.string.product)
        mViewDataBinding.TotalPrice.text =
            listCart.totalCart.toString() + " " + listCart.cartItems?.get(0)?.product?.currency
        mViewDataBinding.TotalOrder.text =
            listCart.totalCart.toString() + " " + listCart.cartItems?.get(0)?.product?.currency
        Currency = listCart.cartItems?.get(0)?.product?.currency
        TotalOrder = listCart.totalCart
        mViewDataBinding.model = listCart
    }

    private fun initRecycle() {
        cartAdapter = CartOrderAdapter(requireContext())
        mViewDataBinding.orderRecyclerView.setHasFixedSize(true)
        mViewDataBinding.orderRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.orderRecyclerView.adapter = cartAdapter
    }

    private fun init() {
        mViewDataBinding.viewmodel = mViewModel
        data = SharedData(requireContext())
        token = data?.getValue(SharedData.ReturnValue.STRING, "token")
    }


    private fun subscribeCounrties() {
        mViewModel.countriesResponse.observe(this, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    bindingAdapter(it.data?.data as List<CountriesResponse.Data>)
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

    private fun bindingAdapter(List: List<CountriesResponse.Data>) {
        val adapter =
            ArrayAdapter(requireContext(), R.layout.custom_spinner, List)
        adapter.setDropDownViewResource(R.layout.custom_dropdown)
        mViewDataBinding.spinnerAdapter = adapter

        mViewModel.itemPositionCountry.observe(this, Observer { postion ->
            if (mViewModel.itemPositionCountry.value != null) {
                listCities?.clear()
                mViewDataBinding.spinnerAdapterCities?.notifyDataSetChanged()
                adapter.getItem(postion)?.id
                country_Id = "EG"
                bindingAdapterCities(adapter.getItem(postion)?.city)
                mViewModel.itemPositionCountry.value = null
            }
        })
    }

    private fun bindingAdapterCities(List: MutableList<CountriesResponse.Data.City>?) {
        listCities?.clear()
        listCities = List
        if (List!!.size > 0) {
            var adapter = listCities?.let {
                ArrayAdapter(
                    requireContext(), R.layout.custom_spinner,
                    it
                )
            }
            adapter?.setDropDownViewResource(R.layout.custom_dropdown)
            mViewDataBinding.spinnerAdapterCities = adapter
            mViewModel.itemPositionCity.observe(this, Observer { postion ->
                if (mViewModel.itemPositionCity.value != null && List!!.size > 0) {
                    city_Id = List?.get(postion)?.id.toString()
                    city_Name = List?.get(postion)?.title.toString()
                    listStates?.clear()
                    mViewDataBinding.spinnerAdapterStates?.notifyDataSetChanged()
                    bindingAdapterStates(List?.get(postion)?.state)
                    mViewModel.itemPositionCity.value = null
                }
            })
        }

    }

    private fun bindingAdapterStates(List: MutableList<CountriesResponse.Data.City.State>?) {
        listStates = List

        if (List!!.size > 0) {
            val adapter = listStates?.let {
                ArrayAdapter(
                    requireContext(), R.layout.custom_spinner,
                    it
                )
            }
            adapter?.setDropDownViewResource(R.layout.custom_dropdown)
            mViewDataBinding.spinnerAdapterStates = adapter
            mViewModel.itemPositionStates.observe(this, Observer { postion ->
                if (mViewModel.itemPositionStates.value != null) {
                    state_Id = listStates?.get(postion)?.id.toString()
                    listStates?.get(postion)?.id
                    if (listStates?.get(postion)?.price != null) {
                        DeliveryPrice = listStates?.get(postion)!!.price
                        state_Name = listStates?.get(postion)?.title
                        var total = (DeliveryPrice + TotalOrder) - Discount
                        mViewDataBinding.TotalDelivery.text =
                            "$DeliveryPrice $Currency"
                        mViewDataBinding.TotalOrder.text = total.toString() + " " + Currency
                        mViewModel.itemPositionStates.value = null
                    }
                }

            })
        }

    }

    fun onClickCheckOut() {
        mViewDataBinding.BtnOrder.setOnClickListener(){
            if (checkOutValidation()) {
                if(paymentMethod.equals("cach")){
                    val requestOrder = RequestCreateOrder(
                        city_Id,
                        state_Id,
                        code,
                        f_Name,
                        phone,
                        city_Name,
                        state_Name,
                        Address,
                        null,
                        null,
                        Email,
                        Address,
                        paymentMethod,
                        "android"
                    )
                    mViewModel.createOrder(requestOrder, token)
                }else {
                    var intent=Intent(requireContext(),PayonlineActivity::class.java)
                    intent.putExtra("price",TotalOrder)
                    startActivity(intent)
                }

            }
        }

    }

    fun onClickRadioButton(){

        mViewDataBinding.radios.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = mViewDataBinding.radios.findViewById(checkedId)
                if (radio.text .equals("Cash On Delivery") || radio.text .equals("نقدي")) {
                    paymentMethod = "cach"
                } else {
                    paymentMethod = "visa"
                }
            })
    }


    fun validateFName(): Boolean {
        f_Name = mViewDataBinding.EFName.text.toString()
        if (f_Name.isNullOrEmpty()) {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.validatefname),
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        return true
    }


    fun validateEmail(): Boolean {
        Email = mViewDataBinding.EEmail.text.toString()
        if (Email.isNullOrEmpty()) {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.validateEmail),
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        return true

    }

    fun validatePhone(): Boolean {
        phone = mViewDataBinding.EPhone.text.toString()
        if (phone.isNullOrEmpty()) {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.validatePhone),
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true

    }

    fun validateAddress(): Boolean {
        Address = mViewDataBinding.EAddress.text.toString()
        if (Address.isNullOrEmpty()) {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.validateAddress),
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        return true

    }

    fun validateCity(): Boolean {
        if (city_Id.isNullOrEmpty()) {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.please_enterstate),
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        return true
    }

    fun checkOutValidation(): Boolean {
        if (validateCity() && validateFName() && validateEmail() && validatePhone() && validateAddress()) {
            return true
        }
        return false
    }


    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent(messsg: MessageEvent) {/* Do something */
        if(messsg.Message.equals("coupon") )
        {
            code=messsg.addcoupon.code.toString()
            Discount=messsg.addcoupon.discount!!.toDouble()
            mViewDataBinding.TDiscount.visibility= View.VISIBLE
            mViewDataBinding.DiscountProducts.visibility= View.VISIBLE
            mViewDataBinding.DiscountProducts.text=messsg.addcoupon.discount.toString()

            var total = (DeliveryPrice + TotalOrder)-messsg.addcoupon.discount!!

            mViewDataBinding.TotalOrder.text = total.toString() + " " + Currency

        }

    };

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

}