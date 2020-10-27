package com.cairocart.Fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cairocart.Adapter.AllNotification_Adpter
import com.cairocart.ChangeLanguage
import com.cairocart.Model.AllNotifications_Response
import com.cairocart.Model.MessageEvent
import com.cairocart.R
import com.cairocart.ViewModel.AllNotifications_ViewModel
import kotlinx.android.synthetic.main.fragment_my_orders.view.*
import org.greenrobot.eventbus.EventBus

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AllNotifications.newInstance] factory method to
 * create an instance of this fragment.
 */
class AllNotifications : Fragment() {
    var toolbarhome: Toolbar?=null
    var UserToken: String?= String()
    private lateinit var DataSaver: SharedPreferences

    lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_all_notifications, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(requireContext())
        UserToken = DataSaver.getString("token", null)

        getAllNotifications()

        return root
    }


    fun getAllNotifications() {
        var allproducts = ViewModelProvider.NewInstanceFactory().create(
            AllNotifications_ViewModel::class.java
        )
        requireContext()?.let {
            allproducts.getNotifications(ChangeLanguage.getLanguage(it),UserToken!!)
                .observe(viewLifecycleOwner, Observer<AllNotifications_Response> { loginmodel ->
                    if (loginmodel != null) {
                        EventBus.getDefault().postSticky(MessageEvent("cart"))

//                    root.view3.visibility=View.VISIBLE
                        val listAdapter =
                            AllNotification_Adpter(it,loginmodel.data as List<AllNotifications_Response.Data>)
                        root.Recycle_Order.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        root.Recycle_Order.setAdapter(listAdapter)
                    }

                })
        }
    }
}