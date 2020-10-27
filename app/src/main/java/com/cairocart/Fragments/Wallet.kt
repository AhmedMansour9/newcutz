package com.cairocart.Fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.chicchicken.ViewModel.Profile_ViewModel
import com.cairocart.Model.Profile_Response
import com.cairocart.R
import com.cairocart.utils.Loading
import kotlinx.android.synthetic.main.fragment_wallet.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Wallet.newInstance] factory method to
 * create an instance of this fragment.
 */
class Wallet : Fragment() {
    private lateinit var DataSaver: SharedPreferences
    var UserToken: String?= String()

     lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_wallet, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(requireContext())
        Get_Profle()


        return root
    }

    fun Get_Profle() {
        var Prof_ViewModel: Profile_ViewModel =
            ViewModelProviders.of(this)[Profile_ViewModel::class.java]
        Loading.Show(requireContext())
        Prof_ViewModel.getData(
            DataSaver.getString("token",null),
            requireContext().applicationContext
        ).observe(viewLifecycleOwner,
            Observer<Profile_Response> { loginmodel ->
                Loading.Disable()
                if (loginmodel != null) {
                root.Points.text=resources.getString(R.string.no_points)+" "+loginmodel.data?.totalWallet.toString()+" "+resources.getString(R.string.points)

                } else {

                }
            }
        )
    }

}