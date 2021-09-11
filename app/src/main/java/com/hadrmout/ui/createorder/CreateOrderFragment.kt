package com.hadrmout.ui.createorder

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hadrmout.R
import com.hadrmout.adapter.CartOrderAdapter
import com.hadrmout.adapter.ShippingMethodAdapter
import com.hadrmout.base.BaseDialogFragment
import com.hadrmout.data.remote.model.*
import com.hadrmout.databinding.FragmentCreateOrderBinding
import com.hadrmout.ui.address_profile.AddressFragment
import com.hadrmout.ui.coupon.AddCouponActivity
import com.hadrmout.ui.editprofile.EditProfileViewModel
import com.hadrmout.ui.payonline.PayonlineActivity
import com.hadrmout.ui.successorder.SuccessOrder
import com.hadrmout.utils.SharedData
import com.hadrmout.utils.Status
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
    var code: String? = null
    var city_Name: String? = String()
    var f_Name: String? = String()
    var customer_home_number: String? = String()
    var customer_floor_number: String? = String()
    var state_Name: String? = String()
    var address_id: String? = String()
    var phone: String? = String()
    var Email: String? = String()
    var Address: String? = String()
    var paymentMethod = "cach"
    var Country: String? = String()
    var ShippingPrice: String? = String()
    var Discount: Double = 0.0
    var oldcity_Id: String? = String()
    var newlistCities: MutableList<CountriesResponse.Data.City>? = arrayListOf()


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
//        subscribeCounrties()
        setupObserverProfile()
        setupObserverOrders()
        onClickCheckOut()
        onClickAddCoupon()
        onClickRadioButton()
        subscribeAddress()
        changeAddress()

    }

    private fun onClickAddCoupon() {
        mViewDataBinding.relaCoupon.setOnClickListener(){
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

    @SuppressLint("SetTextI18n")
    private fun getData() {
        var bundle: Bundle? = Bundle()
        bundle = this.arguments
        listCart = bundle?.getParcelable("data")!!
        cartAdapter.setDeveloperList(listCart?.cartItems as MutableList<ListCartResponse.Data.CartItem>)
        mViewDataBinding.TotalProducts.text =
            listCart.cartItems!!.size.toString() + " " + resources.getString(R.string.product)
        mViewDataBinding.TotalPrice.text =
            listCart.totalCart + " " + context?.resources?.getString(R.string.currency)
        mViewDataBinding.TotalOrder.text =
            listCart.totalCart + " " + context?.resources?.getString(R.string.currency)
        Currency = context?.resources?.getString(R.string.currency)
        TotalOrder = listCart.totalCart.toDouble()
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
        newlistCities = List;
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
//            mViewDataBinding.spinnerAdapterCities = adapter
            mViewDataBinding.SCity.adapter = adapter
            if (!oldcity_Id.isNullOrEmpty()) {
                mViewDataBinding.SCity.setSelection(
                    selectSpinnerValue(List, oldcity_Id.toString()),
                    true
                );
            }
            adapter?.notifyDataSetChanged()


            mViewModel.itemPositionCity.observe(this, Observer { postion ->
                if (mViewModel.itemPositionCity.value != null && List!!.size > 0) {
                    city_Id = List?.get(postion)?.id.toString()
                    city_Name = List?.get(postion)?.title.toString()
//                    listStates?.clear()
//                    mViewDataBinding.spinnerAdapterStates?.notifyDataSetChanged()
//                    bindingAdapterStates(List?.get(postion)?.state)
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
                if (mViewModel.itemPositionStates.value != null && postion != -1) {
                    state_Id = listStates?.get(postion)?.id.toString()
                    listStates?.get(postion)?.id
//                    if (listStates?.get(postion)?.price != null) {
//                        DeliveryPrice = listStates?.get(postion)!!.price
//                        state_Name = listStates?.get(postion)?.title
//                        var total = (DeliveryPrice + TotalOrder) - Discount
//                        mViewDataBinding.TotalDelivery.text =
//                            "$DeliveryPrice $Currency"
//                        mViewDataBinding.TotalOrder.text = total.toString() + " " + Currency
//                        mViewModel.itemPositionStates.value = null
//                    }
                }

            })
        }

    }

    fun onClickCheckOut() {
        mViewDataBinding.BtnOrder.setOnClickListener(){
            if (checkOutValidation()) {
                if(paymentMethod.equals("cach")){
                    val requestOrder = RequestCreateOrder(
                        code,
                        Discount.toString(),
                        address_id
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
        if (validateFName() && validateEmail() && validatePhone() && validateAddress()) {
            return true
        }
        return false
    }


    @SuppressLint("SetTextI18n")
    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent(messsg: MessageEvent) {/* Do something */
        if(messsg.Message.equals("coupon") )
        {
            mViewDataBinding.relaCoupon.visibility=View.INVISIBLE
            mViewDataBinding.relaDiscount.visibility=View.VISIBLE
            code = messsg.addcoupon.code.toString()
            Discount = messsg.addcoupon.discount!!.toDouble()
            mViewDataBinding.TDiscountt.text = messsg.addcoupon.discount.toString()+" $Currency"

            var total = (DeliveryPrice + TotalOrder) - messsg.addcoupon.discount!!

            mViewDataBinding.TotalOrder.text = "$total  $Currency"

        } else if (messsg.Message.equals("address")) {
            token?.let { mViewModel2.getAddress(it) }
        }

    };
    private fun subscribeAddress() {
        mViewModel2.addresssResponse.observe(this, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    bindingAddress(it.data?.data as AddressResponse.Data)
                }
                Status.LOADING -> {
                }
                Status.ERROR -> {
                }
            }
        })
    }

    private fun bindingAddress(address: AddressResponse.Data) {
        address_id = address.id.toString()
        city_Id = address.city?.id.toString()
        Address = address.street.toString()
        city_Name = address.city?.title.toString()
//        state_Name=address.customerRegion.toString()
//        customer_floor_number=address.floorNumber.toString()
//        customer_home_number=address.homeNumber.toString()
        if (address.street.isNullOrEmpty()) {
            mViewDataBinding.street.setText(resources.getString(R.string.noaddress))
            mViewDataBinding.addAddress.setText(resources.getString(R.string.add_address))
        } else {
            mViewDataBinding.addAddress.setText(resources.getString(R.string.edit_address))
            mViewDataBinding.street.setText(address.street.toString())
        }
        oldcity_Id = address.city?.id.toString()
        if (!oldcity_Id.isNullOrEmpty()) {
            newlistCities?.let { selectSpinnerValue(it, oldcity_Id.toString()) }?.let {
                mViewDataBinding.SCity.setSelection(
                    it,
                    true
                )
            };
        }
    }

    private fun selectSpinnerValue(
        ListSpinner: MutableList<CountriesResponse.Data.City>,
        myString: String
    ): Int {
        var index = 0
        for (i in ListSpinner.indices) {
            if (ListSpinner[i].id.toString().equals(myString)) {
                index = i
                break
            }
        }
        return index
    }

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    private fun changeAddress() {
        mViewDataBinding.addAddress.setOnClickListener() {
            val newDialogFragment = AddressFragment()
            val bundle = Bundle()
            newDialogFragment.setArguments(bundle)
            val transaction: FragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()
            newDialogFragment.show(transaction, "New_Dialog_Fragment")
        }
    }


}