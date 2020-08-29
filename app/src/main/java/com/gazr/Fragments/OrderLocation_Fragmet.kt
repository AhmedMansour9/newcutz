package com.gazr.Fragments


import android.content.Context
import android.content.SharedPreferences
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import com.chicchicken.ViewModel.Profile_ViewModel
import com.gazr.Adapter.Cities_Adapter
import com.gazr.Adapter.RecivePoints_Adapter
import com.gazr.Adapter.StatesAdapter
import com.gazr.Adapter.Types_Adapter
import com.gazr.ChangeLanguage
import com.gazr.Model.*
import com.gazr.R
import com.gazr.ViewModel.AddToCart_ViewModel
import com.gazr.ViewModel.Cities_ViewModel
import com.gazr.utils.Loading
import kotlinx.android.synthetic.main.fragment_order_location__fragmet.view.*

/**
 * A simple [Fragment] subclass.
 */
class OrderLocation_Fragmet : Fragment() {

    var TotalAfterDescount: Double = 0.0

    var Total: Double = 0.0
    var Currency: String? = null
    var Items: String? = null

    private lateinit var DataSaver: SharedPreferences
    var City_Id: String? = null
    var City: String? = null

    var State_Id: String? = null
    var State: String? = null

    var Delivery_type: String? = null
    var Delivery_Value: String? = null
    var receive_points_id:String?=null
    var CityProfile_Id: String? = null
    var StateProfile_Id: String? = null

    var Code: String? = null
    lateinit var UserToken: String
    lateinit var root: View
    var bundle: Bundle? = Bundle()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_order_location__fragmet, container, false)
        getTypes()
        getData()
        Get_Profle()
        Btn_Order()
        AddPromo()
        return root
    }
    fun Get_Profle() {
        var Prof_ViewModel: Profile_ViewModel =
            ViewModelProviders.of(this)[Profile_ViewModel::class.java]
        Loading.Show(requireContext())
        Prof_ViewModel.getData( DataSaver.getString("token",null),
            requireContext().applicationContext
        ).observe(viewLifecycleOwner,
            Observer<Profile_Response> { loginmodel ->
                Loading.Disable()
                if (loginmodel != null) {
                    CityProfile_Id=loginmodel.data?.city?.id.toString()
                    StateProfile_Id=loginmodel.data?.state?.id.toString()
                    root.E_Name.setText(loginmodel.data?.fullName)
                    root.E_phone.setText(loginmodel.data?.phone)
                    root.E_StrretName.setText(loginmodel.data?.customerStreet)
                    root.E_BuildingNumber.setText(loginmodel.data?.customerHomeNumber)
                    root.E_floornumber.setText(loginmodel.data?.customerStreet)
                } else {

                }
            }
        )
    }

    private fun getData() {
        DataSaver = PreferenceManager.getDefaultSharedPreferences(requireContext())
        UserToken = DataSaver.getString("token", null)!!
        bundle = requireArguments()
        Items = bundle?.getString("items")
        Currency = bundle?.getString("cu")
        root.T_TotalNumber.text=String.format( Items + " " +resources.getString(R.string.product))
        Total = bundle?.getString("totalprice")!!.toDouble()
        TotalAfterDescount = Total
        root.T_TotalCostOrder.text = String.format( bundle!!.getString("totalprice") + " " + Currency)
        root.T_TotalCost.text = TotalAfterDescount.toString()+" "+Currency

    }


    fun getAllCities() {
        val allCities = ViewModelProvider.NewInstanceFactory().create(Cities_ViewModel::class.java)
        requireContext()?.let {
            allCities.getDataCountries(ChangeLanguage.getLanguage(requireContext()), it)
                .observe(viewLifecycleOwner, Observer<Cities_Response> { loginmodel ->
                    if (loginmodel != null) {
                        root.T_Time.text=loginmodel.data.get(0).info_receive_point
                        val customAdapter = Cities_Adapter(
                            requireContext(),
                            loginmodel.data as List<Cities_Response.Data>
                        )
                        root.S_City.setPrompt(resources.getString(R.string.city));
                        root.S_City.setAdapter(customAdapter)
                        var s = 1
                        while (s < loginmodel.data!!.size) {
                            if (loginmodel.data!!.get(s)!!.id.toString().equals(CityProfile_Id)) {
                                root.S_City.setSelection(s)
                            }
                            s++
                        }
                        root.S_City.setOnItemSelectedListener(object :
                            AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                adapterView: AdapterView<*>,
                                view: View?,
                                i: Int,
                                l: Long
                            ) {
                                var Cityy = root.S_City.getSelectedItem().toString()
                                var s = 0
                                while (s < loginmodel.data!!.size) {
                                    if (loginmodel!!.data!!.get(s)?.city.equals(Cityy)) {
                                        City_Id = loginmodel!!.data!!.get(s)!!.id.toString()
                                        City = loginmodel!!.data!!.get(s)!!.city
                                        Delivery_Value=null
                                        getDeliveriesTime(City_Id!!)

                                        getAllStates(City_Id.toString())
                                    }
                                    s++
                                }

                            }
                            override fun onNothingSelected(adapterView: AdapterView<*>) {

                            }
                        })
                    }
                })
        }
    }


    fun getAllStates(id:String) {
        val allCities = ViewModelProvider.NewInstanceFactory().create(Cities_ViewModel::class.java)
        requireContext()?.let {
            allCities.getDataStates(ChangeLanguage.getLanguage(requireContext()),id, it)
                .observe(viewLifecycleOwner, Observer<Cities_Response> { loginmodel ->
                    if (loginmodel != null) {
                        val customAdapter = StatesAdapter(
                            requireContext(),
                            loginmodel.data as List<Cities_Response.Data>
                        )
                        root.S_State.setAdapter(customAdapter)
                        var s = 1
                        while (s < loginmodel.data!!.size) {
                            if (loginmodel.data!!.get(s)!!.id.toString().equals(StateProfile_Id)) {
                                root.S_State.setSelection(s)
                            }
                            s++
                        }
                        root.S_State.setOnItemSelectedListener(object :
                            AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                adapterView: AdapterView<*>,
                                view: View?,
                                i: Int,
                                l: Long
                            ) {
                                var Cityy = root.S_State.getSelectedItem().toString()
                                var s = 0
                                while (s < loginmodel.data!!.size) {
                                    if (loginmodel!!.data!!.get(s)?.city.equals(Cityy)) {
                                        State_Id = loginmodel.data.get(s).id.toString()
                                        State = loginmodel!!.data!!.get(s)!!.city
                                        if ( Delivery_type.equals("Immediately")|| Delivery_type.equals("توصيل سريع")) {

                                            Delivery_Value = loginmodel!!.data!!.get(s)!!.price
                                            root.T_Delivry.text = Delivery_Value + " " + Currency
                                            var total =
                                                Delivery_Value!!.toDouble() + TotalAfterDescount!!.toDouble()
                                            root.T_TotalCost.setText(total.toString() + " " + Currency)

                                        }
                                    }
                                    s++
                                }
                            }

                            override fun onNothingSelected(adapterView: AdapterView<*>) {
                            }
                        })
                    }
                })
        }
    }


    fun getDeliveriesTime(State_Id:String) {
        val allCities = ViewModelProvider.NewInstanceFactory().create(Cities_ViewModel::class.java)
        requireContext()?.let {
            allCities.getTypesTime(ChangeLanguage.getLanguage(requireContext()),State_Id, it)
                .observe(viewLifecycleOwner, Observer<RecivesPoints_Response> { loginmodel ->
                    if (loginmodel != null) {
                        val customAdapt = RecivePoints_Adapter(requireContext(),
                            loginmodel.data as List<RecivesPoints_Response.Data>
                        )
                        root.S_DelivryTime.setAdapter(customAdapt)
                        root.S_DelivryTime.setOnItemSelectedListener(object :
                            AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                adapterView: AdapterView<*>,
                                view: View?,
                                i: Int,
                                l: Long
                            ) {
                                var Cityy = root.S_DelivryTime.getSelectedItem().toString()
                                var s = 0
                                while (s < loginmodel.data!!.size) {
                                    if (loginmodel.data!!.get(s)!!.title.toString().contains(Cityy)) {
                                        if ( Delivery_type.equals("Schedule")|| Delivery_type.equals("توصيل مجمع يوميا")) {
                                            root.T_Delivry.visibility = View.VISIBLE
                                            root.T_Delivry.text =
                                                loginmodel!!.data!!.get(s)!!.price.toString()+ " " + Currency
                                            Delivery_Value =
                                                loginmodel!!.data!!.get(s)!!.price.toString()
                                            var total =
                                                Delivery_Value!!.toDouble() + TotalAfterDescount!!.toDouble()
                                            root.T_TotalCost.setText(total.toString() + " " + Currency)

                                        }
                                        receive_points_id=loginmodel!!.data!!.get(s)!!.id.toString()
                                    }
                                    s++
                                }

                            }
                            override fun onNothingSelected(adapterView: AdapterView<*>) {

                            }
                        })
                    }
                })
        }
    }

    fun getTypes() {

        val customAdapter = Types_Adapter(
            requireContext(),resources.getStringArray(R.array.types)
        )

        root.S_Delivry.setAdapter(customAdapter)
        root.S_Delivry.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>,
                view: View,
                i: Int,
                l: Long
            ) {
                var Cityy = root.S_Delivry.getSelectedItem().toString()
                var s = 0
                   while (s < resources.getStringArray(R.array.types).size) {
                       if ( Cityy.equals("Immediately")|| Cityy.equals("توصيل سريع")) {
                           root.Delivry_time.visibility=View.VISIBLE
                           root.Card_DeliveryTime.visibility=View.VISIBLE
                           Delivery_type="Immediately"
                           root.Delivry_time.visibility=View.GONE
                           root.Card_DeliveryTime.visibility=View.GONE
                           receive_points_id=null
                           root.T_Time.visibility=View.VISIBLE
                           getAllCities()


                       }else {
                           root.Delivry_time.visibility=View.VISIBLE
                           root.Card_DeliveryTime.visibility=View.VISIBLE
                           Delivery_type="Schedule"
                           root.T_Time.visibility=View.GONE
                           getAllCities()


                       }
                       s++
                   }
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {
            }
        })

    }

    fun AddPromo() {
        root.Btn_EnterPromo.setOnClickListener() {

            if (root.E_Promo.text.toString().isNullOrEmpty()) {
                root.E_Promo.error = resources.getString(R.string.feildempty)
                return@setOnClickListener
            }
            Loading.Show(requireContext())
            var addtocart: AddToCart_ViewModel = ViewModelProvider.NewInstanceFactory().create(
                AddToCart_ViewModel::class.java
            )
            this.requireContext()?.let {
                addtocart.getPromoCodeData(DataSaver.getString("token", null)!!, root.E_Promo.text.toString()!!, it)
                    .observe(viewLifecycleOwner, Observer<PromoCode_Response> { loginmodel ->
                        Loading.Disable()
                        if (loginmodel != null) {
                            TotalAfterDescount = 0.0
                            Code=root.E_Promo.text.toString()
                            root.Btn_EnterPromo.isEnabled = false
                            root.E_Promo.text = null
                            TotalAfterDescount = Total - loginmodel.data!!.discount
                            root.T_Promoo.visibility=View.VISIBLE
                            root.Promoo.visibility=View.VISIBLE
                            root.T_Promoo.text=loginmodel.data!!.discount.toString()+ " " + Currency
                            root.T_TotalCostOrder.text =  TotalAfterDescount.toString() + " " + Currency
                            root.T_BeforeDiscount.setPaintFlags(root.T_BeforeDiscount.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
                            root.T_BeforeDiscount.text = bundle!!.getString("totalprice") + " " + Currency
                            var total =
                                Delivery_Value!!.toDouble() + TotalAfterDescount!!.toDouble()
                            root.T_TotalCost.setText(total.toString() + " " + Currency)

                        } else {
                            if(!AddToCart_ViewModel.ErrorPromo.isNullOrEmpty()) {
                                Toast.makeText(
                                    requireContext(),
                                    AddToCart_ViewModel.ErrorPromo,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    })
            }

        }
    }

    fun Btn_Order() {
        root.Btn_order.setOnClickListener() {

            var Name = root.E_Name.text.toString()
            var Phone = root.E_phone.text.toString()
            var StreetName = root.E_StrretName.text.toString()
            var B_number = root.E_BuildingNumber.text.toString()
            var F_number = root.E_floornumber.text.toString()

            if (Name.isEmpty()) {
                root.E_Name.error = "Name required"
                root.E_Name.requestFocus()
            }
            if (Phone.isEmpty()) {
                root.E_phone.error = "Phone required"
                root.E_phone.requestFocus()
            }


            if (StreetName.isEmpty()) {
                root.E_StrretName.error = "Street Name required"
                root.E_StrretName.requestFocus()
            }
            if (B_number.isEmpty()) {
                root.E_BuildingNumber.error = "Buid Number required"
                root.E_BuildingNumber.requestFocus()
            }
            if (State.isNullOrEmpty()) {
                Toast.makeText(context, resources.getString(R.string.please_enterstate), Toast.LENGTH_SHORT).show()
            }
            if (F_number.isEmpty()) {
                root.E_floornumber.error = "Floor Number required"
                root.E_floornumber.requestFocus()
            } else if (!Name.isEmpty() && !Phone.isEmpty() &&
                !StreetName.isEmpty() && !B_number.isEmpty() && !F_number.isEmpty() && !State.isNullOrEmpty()
            ) {
                     root.Btn_order.hideKeyboard()
                        var productsByid = ConfirmOrder()
                        val bundle = Bundle()
                        bundle.putString("name", root.E_Name.text.toString())
                        bundle.putString("street", StreetName)
                        bundle.putString("phone", Phone!!)
                        bundle.putString("p_method", "cacheOnDelivery")
                        bundle.putString("city", City)
                        bundle.putString("city_id", City_Id)
                        bundle.putString("b_number", B_number)
                        bundle.putString("floor", F_number)
                        bundle.putString("promo", Code)
                        bundle.putString("d_type", Delivery_type)
                        bundle.putString("d_value", Delivery_Value)
                        bundle.putString("total", TotalAfterDescount.toString())
                        bundle.putString("cu", Currency)
                        bundle.putString("state_id", State_Id)
                        bundle.putString("receive_points_id", receive_points_id)
                        productsByid.arguments = bundle
                        Navigation.findNavController(root).navigate(R.id.action_orderLocation_Fragmet_to_confirmOrder, bundle);


//                        Loading.Show(requireContext())
//                        root.Btn_order.isEnabled = false
//                        var order: Order_ViewModel =
//                            ViewModelProviders.of(this)[Order_ViewModel::class.java]
//                        order.getData(
//                            UserToken,
//                            root.E_Name.text.toString(),
//                            StreetName,
//                            Phone
//                            , "cacheOnDelivery"
//                            , "0"
//                            , ""
//                            , ""
//                            , StreetName
//                            , "0"
//                            , ""
//                            , B_number
//                            , F_number
//                            , ""
//                            ,Code
//                            , requireContext()
//                        ).observe(viewLifecycleOwner,
//                            Observer<Order_Response> { loginmodel ->
//                                Loading.Disable()
//                                if (loginmodel != null) {
//                                    Toast.makeText(
//                                        requireContext(),
//                                        requireContext().getString(R.string.ordersuccess),
//                                        Toast.LENGTH_LONG
//                                    ).show()
//                                    val intent = Intent(
//                                        requireContext(),
//                                        NavigationDrawer::class.java
//                                    )
//                                    startActivity(intent)
//                                    requireActivity().finish()
//
//                                } else {
//                                    root.Btn_order.isEnabled = true
//                                    Toast.makeText(
//                                        requireContext(),
//                                        requireContext().getString(R.string.failedmsg),
//                                        Toast.LENGTH_LONG
//                                    ).show()
//                                }
//                            }
//                        )
//
//
//
//
//                }
//
//
//
//            } else {
//                Toast.makeText(
//                    requireContext(),
//                    requireContext().getString(R.string.nointernet),
//                    Toast.LENGTH_LONG
//                ).show()
//            }
                    }



        }
    }


    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}




















