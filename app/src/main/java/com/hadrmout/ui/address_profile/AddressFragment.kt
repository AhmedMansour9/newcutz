package com.hadrmout.ui.address_profile

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.hadrmout.R
import com.hadrmout.adapter.CartOrderAdapter
import com.hadrmout.base.BaseDialogFragment
import com.hadrmout.data.remote.model.*
import com.hadrmout.databinding.FragmentAddressProfileBinding
import com.hadrmout.ui.editprofile.EditProfileViewModel
import com.hadrmout.utils.SharedData
import com.hadrmout.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus


@AndroidEntryPoint
class AddressFragment : BaseDialogFragment<FragmentAddressProfileBinding>() {
    private var token: String? = String()
    private var data: SharedData? = null
    private var country_Id: String? = String()
    override var idLayoutRes: Int = R.layout.fragment_address_profile
    var listCities: MutableList<CountriesResponse.Data.City>? = arrayListOf()
    var listStates: MutableList<CountriesResponse.Data.City.State>? = arrayListOf()
    var newlistCities: MutableList<CountriesResponse.Data.City>? = arrayListOf()
    private lateinit var cartAdapter: CartOrderAdapter
    private lateinit var listCart: ListCartResponse.Data
    var TotalOrder: Double = 0.0
    var DeliveryPrice: Double = 0.0
    var Currency: String? = String()
    var city_Id: String? = String()
    var state_Id: String? = String()
    var oldcity_Id: String? = String()
    var code: String? = String()
    var city_Name: String? = String()
    var fullName: String? = String()
    var phone: String? = String()
    var Region: String? = String()
    var state_Name: String? = String()
    var F_Number: String? = String()
    var B_Number: String? = String()
    var Address: String? = String()
    var paymentMethod = "cach"
    var Country: String? = String()
    var ShippingPrice: String? = String()
    var Discount: Double = 0.0

    private val mViewModel2: EditProfileViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }

    val mViewModel: AddreeViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        getData()
        subscribeCounrties()
        subscribeAddress()
        onClickSave()
        setupObserverOrders()
        setupObserverProfile()
    }

    private fun setupObserverOrders() {
        mViewModel.createOrderResponse.observe(this, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    Toast.makeText(
                        requireContext(),
                        "" + resources.getString(R.string.updated),
                        Toast.LENGTH_SHORT
                    ).show()
                    EventBus.getDefault().postSticky(MessageEvent("address"))

                    dismiss()

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
        fullName = profile.data?.name.toString()
        phone = profile.data?.phone.toString()
    }


    private fun onClickSave() {
        mViewDataBinding.BtnSave.setOnClickListener() {
            if (checkOutValidation()) {
                val requestOrder = RequestCreateAddress(
                    fullName,
                    "a",
                    phone,
                    city_Id,
                    state_Id,
                    B_Number,
                    F_Number,
                    Address,
                    fullName
                )

                var hashMap=HashMap<String,String>()
                fullName?.let { it1 -> hashMap.put("frirstName", it1) }
                hashMap.put("lastName",".")
                phone?.let { it1 -> hashMap.put("phone", it1) }
                city_Id?.let { it1 -> hashMap.put("city_id", it1) }
                state_Id?.let { it1 -> hashMap.put("state_id", it1) }
                B_Number?.let { it1 -> hashMap.put("home_number", it1) }
                F_Number?.let { it1 -> hashMap.put("floor_number", it1) }
                Address?.let { it1 -> hashMap.put("street", it1) }
                fullName?.let { it1 -> hashMap.put("title", it1) }

                mViewModel.createOrder( token,hashMap)
            }
        }
    }


    private fun getData() {
        token?.let { mViewModel2.getProfile(it) }
        token?.let { mViewModel2.getAddress(it) }
    }


    fun checkOutValidation(): Boolean {
        if (validateCity() && validateBNumber() && validateFloorNumber() && validateAddress()) {
            return true
        }
        return false
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

    private fun subscribeAddress() {
        mViewModel2.addresssResponse.observe(this, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    bindingAddress(it.data?.data as AddressResponse.Data)
                }
                Status.LOADING -> {
//                    showLoading()

                }
                Status.ERROR -> {
                    dismissLoading()
                }
            }
        })
    }

    private fun bindingAddress(address: AddressResponse.Data) {
//        mViewDataBinding.ERegion.setText(address.customerRegion.toString())
        mViewDataBinding.EAddress.setText(address.street.toString())
        mViewDataBinding.EBNumber.setText(address.homeNumber.toString())
        mViewDataBinding.FNNumber.setText(address.floorNumber.toString())
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

    private fun bindingAdapter(List: List<CountriesResponse.Data>) {
        val adapter =
            ArrayAdapter(requireContext(), R.layout.custom_spinner, List)
        adapter.setDropDownViewResource(R.layout.custom_dropdown)
        mViewDataBinding.spinnerAdapter = adapter
        mViewModel.itemPositionCountry.observe(this, Observer { postion ->
            if (mViewModel.itemPositionCountry.value != null) {
                city_Id = List?.get(postion)?.id.toString()
                city_Name = List?.get(postion)?.title.toString()
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
            mViewDataBinding.SCity.adapter = adapter
            if (!oldcity_Id.isNullOrEmpty()) {
                mViewDataBinding.SCity.setSelection(
                    selectSpinnerValue(List, oldcity_Id.toString()),
                    true
                );
            }
            adapter?.notifyDataSetChanged()
            mViewModel.itemPositionCity.observe(this, Observer { postion ->
//                if (mViewModel.itemPositionCity.value != null && List!!.size > 0) {
                state_Id = List?.get(postion)?.id.toString()
                city_Name = List?.get(postion)?.title.toString()
//                    listStates?.clear()
//                    mViewDataBinding.spinnerAdapterStates?.notifyDataSetChanged()
//                    bindingAdapterStates(List?.get(postion)?.state)
//                    mViewModel.itemPositionCity.value = null
//                }
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
                        mViewModel.itemPositionStates.value = null
                    }
                }

            })
        }

    }


    fun validateRegion(): Boolean {
        Region = mViewDataBinding.ERegion.text.toString()
        if (Region.isNullOrEmpty()) {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.please_enterstate),
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        return true
    }


    fun validateBNumber(): Boolean {
        B_Number = mViewDataBinding.EBNumber.text.toString()
        if (B_Number.isNullOrEmpty()) {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.validateBNumber),
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        return true

    }

    fun validateFloorNumber(): Boolean {
        F_Number = mViewDataBinding.FNNumber.text.toString()
        if (F_Number.isNullOrEmpty()) {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.validateFNumber),
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

}