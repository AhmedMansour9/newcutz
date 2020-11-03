package com.cairocart.ui.bottomnavigate

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.cairocart.R
import com.cairocart.base.BaseActivity
import com.cairocart.databinding.BottomFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_tabs_layout.*
import kotlinx.android.synthetic.main.bottom_fragment.bottomNavigationView


@AndroidEntryPoint
class BottomNavigateFragment : BaseActivity<BottomFragmentBinding>() {

    override var idLayoutRes: Int = R.layout.bottom_fragment
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        changeStatusBarColor()
        setupNavigationView()
        preventRecreate()

    }

    private fun setupNavigationView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupWithNavController(bottomNavigationView, navHostFragment.navController)
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.nav_home, R.id.nav_home, R.id.T_Offers,
//                R.id.T_Cart, R.id.T_Setting
//            )
//        )
//        setupActionBarWithNavController(navHostFragment.navController, appBarConfiguration)

    }

    private fun preventRecreate() = bottomNavigationView.setOnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.T_Categories -> Navigation.findNavController(this, R.id.fragment)
                .navigate(R.id.T_Categories)

            else -> Navigation.findNavController(this, R.id.fragment).navigate(R.id.homeFragment)
        }
        return@setOnNavigationItemSelectedListener true
    }


    private fun changeStatusBarColor() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        }
    }


}