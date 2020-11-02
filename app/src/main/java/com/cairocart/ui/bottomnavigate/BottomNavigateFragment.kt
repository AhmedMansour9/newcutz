package com.cairocart.ui.bottomnavigate

import android.os.Bundle
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.cairocart.R
import com.cairocart.base.BaseFragment
import com.cairocart.databinding.BottomFragmentBinding
import kotlinx.android.synthetic.main.activity_tabs_layout.*
import kotlinx.android.synthetic.main.bottom_fragment.bottomNavigationView
import androidx.navigation.ui.setupActionBarWithNavController
import com.cairocart.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BottomNavigateFragment : BaseActivity<BottomFragmentBinding>() {

    override var idLayoutRes: Int = R.layout.bottom_fragment
    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        changeStatusbarColor()
        setUpView()
        PreventRecreate()

    }

    fun setUpView(){
        var navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupWithNavController(bottomNavigationView, navHostFragment.navController)
        var appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home,R.id.nav_home, R.id.T_Offers,
            R.id.T_Cart, R.id.T_Setting))
        setupActionBarWithNavController(navHostFragment.navController,appBarConfiguration)

    }

    fun PreventRecreate(){
        bottomNavigationView.setOnNavigationItemReselectedListener {  }
    }

    fun changeStatusbarColor(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        }
    }


}