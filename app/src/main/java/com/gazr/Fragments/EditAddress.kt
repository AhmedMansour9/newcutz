package com.gazr.Fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.chicchicken.ViewModel.Profile_ViewModel
import com.gazr.Adapter.Cities_Adapter
import com.gazr.ChangeLanguage
import com.gazr.Model.Cities_Response
import com.gazr.Model.Edit_ProfileResponse
import com.gazr.Model.Profile_Response
import com.gazr.R
import com.gazr.ViewModel.Cities_ViewModel
import com.gazr.ViewModel.EditProfile_ViiewModel
import com.gazr.utils.Loading
import kotlinx.android.synthetic.main.fragment_edit_address.view.*
import kotlinx.android.synthetic.main.fragment_my_profile.view.*
import kotlinx.android.synthetic.main.fragment_order_location__fragmet.view.*
import kotlinx.android.synthetic.main.fragment_order_location__fragmet.view.E_BuildingNumber
import kotlinx.android.synthetic.main.fragment_order_location__fragmet.view.E_Name
import kotlinx.android.synthetic.main.fragment_order_location__fragmet.view.E_StrretName
import kotlinx.android.synthetic.main.fragment_order_location__fragmet.view.E_floornumber
import kotlinx.android.synthetic.main.fragment_order_location__fragmet.view.E_phone
import kotlinx.android.synthetic.main.fragment_order_location__fragmet.view.S_City
import kotlinx.android.synthetic.main.fragment_order_location__fragmet.view.S_State

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditAddress.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditAddress : Fragment() {
    private lateinit var DataSaver: SharedPreferences
    var City_Id: String? = null
    var City: String? = null
    var CityProfile_Id: String? = null
    var StateProfile_Id: String? = null

    var State_Id: String? = null
    var State: String? = null
    var Email:String?= String()
    var Phone:String?= String()

    lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_edit_address, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(requireContext())
        Get_Profle()
        Btn_Confirm()


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
                    getAllCities()
                    Email=loginmodel.data?.email
                    Phone=loginmodel.data?.phone
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

    fun getAllCities() {
        val allCities = ViewModelProvider.NewInstanceFactory().create(Cities_ViewModel::class.java)
        requireContext()?.let {
            allCities.getDataCountries(ChangeLanguage.getLanguage(requireContext()), it)
                .observe(viewLifecycleOwner, Observer<Cities_Response> { loginmodel ->
                    if (loginmodel != null) {
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
                                view: View,
                                i: Int,
                                l: Long
                            ) {
                                var Cityy = root.S_City.getSelectedItem().toString()
                                var s = 0
                                while (s < loginmodel.data!!.size) {
                                    if (loginmodel!!.data!!.get(s)?.city.equals(Cityy)) {
                                        City_Id = loginmodel!!.data!!.get(s)!!.id.toString()
                                        City = loginmodel!!.data!!.get(s)!!.city
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
                        val customAdapter = Cities_Adapter(
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
                                view: View,
                                i: Int,
                                l: Long
                            ) {
                                var Cityy = root.S_State.getSelectedItem().toString()
                                var s = 0
                                while (s < loginmodel.data!!.size) {
                                    if (loginmodel!!.data!!.get(s)?.city.equals(Cityy)) {
                                        State_Id = loginmodel.data.get(s).id.toString()
                                        State = loginmodel!!.data!!.get(s)!!.city
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


    fun Btn_Confirm() {
        root.Btn_Save.setOnClickListener() {

            var Name = root.E_Name.text.toString()
            var Phone = root.E_phone.text.toString()
            var StreetName = root.E_StrretName.text.toString()
            var B_Number = root.E_BuildingNumber.text.toString()
            var FloorNumber = root.E_floornumber.text.toString()

            if (Name.isEmpty()) {
                root.E_NameProfile.error = "Name required"
                root.E_NameProfile.requestFocus()
            }
            if (Phone.isEmpty()) {
                root.E_Phone.error = "Phone required"
                root.E_Phone.requestFocus()
            }
            if (StreetName.isEmpty()) {
                root.E_StrretName.error = "StreetName required"
                root.E_StrretName.requestFocus()
            }
            if (B_Number.isEmpty()) {
                root.E_BuildingNumber.error = "Building Number required"
                root.E_BuildingNumber.requestFocus()
            }
            if (FloorNumber.isEmpty()) {
                root.E_floornumber.error = "Floor Number required"
                root.E_floornumber.requestFocus()
            }

          else if (!Name.isEmpty() && !Phone.isEmpty() && !StreetName.isEmpty()
                && !B_Number.isEmpty() && !FloorNumber.isEmpty()) {

                var editProf_ViewModel: EditProfile_ViiewModel =
                    ViewModelProviders.of(this)[EditProfile_ViiewModel::class.java]
                Loading.Show(requireContext())

                editProf_ViewModel.getEditData(
                    DataSaver.getString("token",null)!!,
                    Email!!,Phone!!,
                    Name,
                    City_Id!!,
                    State_Id!!,
                    StreetName,B_Number,FloorNumber,
                    requireContext().applicationContext
                ).observe(viewLifecycleOwner,
                    Observer<Edit_ProfileResponse> { loginmodel ->
                        Loading.Disable()
                        if (loginmodel != null) {

                            Toast.makeText(
                                requireContext().applicationContext,
                                requireContext().applicationContext.getString(R.string.updated),
                                Toast.LENGTH_LONG
                            ).show()

                        } else {
                            Toast.makeText(
                                requireContext().applicationContext,
                                requireContext().applicationContext.getString(R.string.failedmsg),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                )

            }
        }

    }

}