package com.cairocart.Fragments


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cairocart.R
import kotlinx.android.synthetic.main.fragment_my_profile.view.*

/**
 * A simple [Fragment] subclass.
 */
class MyProfile : Fragment() {
   lateinit var root:View


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_my_profile, container, false)


        return root
    }



    fun Rela_ChangeData() {
        root.Constrain_ChangeData.setOnClickListener() {
            root.Constrain_ChangeData.setBackgroundResource(R.drawable.bc_selected);
            root.Constrain_ChangeData.setTextColor(
                Color.BLACK)
            root.Constrain_ChangePass.setBackgroundColor(
                Color.TRANSPARENT
            )
            root.Constrain_ChangePass.setTextColor(Color.WHITE)
            root.constrain_Password.visibility = View.GONE
            root.constrain_Login.visibility = View.VISIBLE
        }


    }


    fun Rela_Password() {
        root.Constrain_ChangePass.setOnClickListener() {
            root.Constrain_ChangeData.setBackgroundColor(Color.TRANSPARENT);
            root.Constrain_ChangeData.setTextColor(
                Color.WHITE)
            root.Constrain_ChangePass.setBackgroundResource(
                R.drawable.bc_selected
            )
            root.Constrain_ChangePass.setTextColor(Color.BLACK)

            root.constrain_Password.visibility = View.VISIBLE
            root.constrain_Login.visibility = View.GONE

        }


    }


}
