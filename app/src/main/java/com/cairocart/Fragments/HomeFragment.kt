package com.cairocart.Fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cairocart.Activities.Login
import com.cairocart.ChangeLanguage
import com.cairocart.Model.*
import com.cairocart.R

import com.github.ag.floatingactionmenu.OptionsFabLayout
import com.google.firebase.iid.FirebaseInstanceId
import com.smarteist.autoimageslider.SliderAnimations
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
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