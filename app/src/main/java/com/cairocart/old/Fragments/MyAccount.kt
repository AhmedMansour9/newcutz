package com.cairocart.old.Fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import com.cairocart.R


/**
 * A simple [Fragment] subclass.
 * Use the [MyAccount.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyAccount : Fragment() {
    private lateinit var dataSaver: SharedPreferences
    var UserToken: String?= String()

    lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_my_account, container, false)
        dataSaver = PreferenceManager.getDefaultSharedPreferences(context);


        return root
    }


}