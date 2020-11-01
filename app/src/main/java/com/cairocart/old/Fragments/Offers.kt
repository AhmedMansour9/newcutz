package com.cairocart.old.Fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cairocart.R
import com.cairocart.old.Model.DataX


/**
 * A simple [Fragment] subclass.
 * Use the [Offers.newInstance] factory method to
 * create an instance of this fragment.
 */
class Offers : Fragment()
     {
   lateinit var root:View



    var my_page = 1
    var hasMorePages:Boolean=true
    lateinit var result_List: MutableList<DataX>

    var UserToken:String?= String()
    private lateinit var DataSaver: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_offers, container, false)
        result_List=  mutableListOf<DataX>()

        return root
    }

}
