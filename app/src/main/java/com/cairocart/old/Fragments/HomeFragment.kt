package com.cairocart.old.Fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.cairocart.R

import com.github.ag.floatingactionmenu.OptionsFabLayout
import java.util.*

class HomeFragment : Fragment() {


    //    lateinit var allproducts: Cart_ViewModel
    var toolbarhome: Toolbar? = null
    private lateinit var DataSaver: SharedPreferences
    var swipeTimer: Timer? = null
    var Phone:String?= String()
    var Whatapp:String?= String()
    var FaceBook:String?= String()
    private lateinit var fabWithOptions: OptionsFabLayout

    private var tokenfirebae:String?= String()
    companion object {
         var UserToken:String?= String()
    }
    lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_home, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(requireContext())
        UserToken=DataSaver.getString("token", null)


        return root
    }


}