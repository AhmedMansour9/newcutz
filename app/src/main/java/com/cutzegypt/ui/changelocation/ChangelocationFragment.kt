package com.cutzegypt.ui.changelocation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.cutzegypt.R
import com.cutzegypt.base.BaseFragment
import com.cutzegypt.data.remote.model.*
import com.cutzegypt.databinding.FragmentChangelocationBinding
import com.cutzegypt.ui.bottomnavigate.BottomNavigateActivity
import com.cutzegypt.utils.SharedData
import com.cutzegypt.utils.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChangelocationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class ChangelocationFragment : BaseFragment<FragmentChangelocationBinding>() {
    private var token: String? = String()
    private var data: SharedData? = null
    private var country_Id: String? = String()
    override var idLayoutRes: Int = R.layout.fragment_changelocation

    var code: String? = String()
    var phone: String? = String()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val REQUEST_LOCATION_PERMISSION = 1

    // TODO: Step 1.1, Review variables (no changes).
// FusedLocationProviderClient - Main class for receiving location updates.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    val mViewModel: ChangeLocationViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        init()
        subscribeCounrties()
        onClickMyLocation()
        requiestLocationPermisson()
        subscribeNearestLocation()


    }

    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
    private fun onClickMyLocation() {
       mViewDataBinding.TSelectLocation.setOnClickListener(){
           if (isPermissionGranted()) {
               if (ActivityCompat.checkSelfPermission(
                       requireContext(),
                       Manifest.permission.ACCESS_FINE_LOCATION
                   ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                       requireContext(),
                       Manifest.permission.ACCESS_COARSE_LOCATION
                   ) != PackageManager.PERMISSION_GRANTED
               ) {
                   return@setOnClickListener
               }

               fusedLocationProviderClient.lastLocation
                   .addOnSuccessListener { location: Location? ->
                       mViewModel.getNearestBranches(
                           location?.latitude.toString(),
                           location?.longitude.toString()
                       )

                   }
           }
           else {
               requiestLocationPermisson()
           }
       }


    }

    private fun requiestLocationPermisson(){
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION_PERMISSION
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {

            }
        }
    }

    private fun showAlert(addess: String, lat: String, lng: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(resources.getString(R.string.confirm_location))
        builder.setMessage(addess)
        builder.setPositiveButton(android.R.string.ok) { dialog, which ->
            var locationModel = LocationModel(lat, lng, addess)
            val gson = Gson()
            val datajson: String = gson.toJson(locationModel)
            data?.putValue("json", datajson)
            val intent = Intent(requireContext(), BottomNavigateActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

        }

        builder.setNegativeButton(android.R.string.cancel) { dialog, which ->
            dialog.dismiss()
        }


        builder.show()
    }


    private fun init() {
        mViewDataBinding.viewmodel = mViewModel
        data = SharedData(requireContext())
        token = data?.getValue(SharedData.ReturnValue.STRING, "token")
    }


    private fun subscribeCounrties() {
        mViewModel.countriesResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    bindingAdapter(it.data?.data as MutableList<BranchesResponse.Data>)
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

    private fun subscribeNearestLocation() {
        mViewModel.nearestResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    bindingData(it.data?.data as NearestBranchResponse.Data)
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

    private fun bindingData(mutableList: NearestBranchResponse.Data) {
        showAlert(
            mutableList.title.toString(),
            mutableList.lat.toString(),
            mutableList.lng.toString()
        )
    }

    private fun bindingAdapter(List: MutableList<BranchesResponse.Data>) {
        if(List.size>1){
            mViewDataBinding.TOr.visibility=View.VISIBLE
            mViewDataBinding.or.visibility=View.VISIBLE
            mViewDataBinding.TSelectBranch.visibility=View.VISIBLE
            mViewDataBinding.SCountry.visibility=View.VISIBLE
        }

        var branchesResponse =
            BranchesResponse.Data(resources.getString(R.string.branch), "", 0, 0.0, 0.0)
        List.add(0, branchesResponse)


    val adapter: ArrayAdapter<BranchesResponse.Data> = object : ArrayAdapter<BranchesResponse.Data>(
        requireContext(),
        R.layout.custom_spinner,
        List
    ) {
        override fun getDropDownView(
            position: Int,
            convertView: View?,
            parent: ViewGroup
        ): View {
            val view: TextView = super.getDropDownView(
                position,
                convertView,
                parent
            ) as TextView
            // set item text bold
            view.setTypeface(view.typeface, Typeface.BOLD)

            // set selected item style
            if (position == mViewDataBinding.SCountry.selectedItemPosition && position != 0) {
                view.background = ColorDrawable(Color.parseColor("#F7E7CE"))
                view.setTextColor(Color.parseColor("#333399"))
            }

            // make hint item color gray
            if (position == 0) {
                view.setTextColor(Color.LTGRAY)
            }

            return view
        }

        override fun isEnabled(position: Int): Boolean {
            // disable first item
            // first item is display as hint
            return position != 0
        }
    }

        adapter.setDropDownViewResource(R.layout.custom_dropdown)
        mViewDataBinding.spinnerAdapter = adapter
//        mViewDataBinding.SCountry.setSelection(List.size -1);
        mViewModel.itemPositionBranches.observe(viewLifecycleOwner, Observer { postion ->
            if (!List[postion].title.equals(resources.getString(R.string.branch)))
                showAlert(
                    List[postion].title,
                    List[postion].lat.toString(),
                    List[postion].lng.toString()
                )

        })

    }




}