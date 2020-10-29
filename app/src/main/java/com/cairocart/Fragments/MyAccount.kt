package com.cairocart.Fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import com.cairocart.Activities.Language
import com.cairocart.Activities.Login
import com.cairocart.R
import com.cairocart.SharedPrefManager
import kotlinx.android.synthetic.main.fragment_my_account.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


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