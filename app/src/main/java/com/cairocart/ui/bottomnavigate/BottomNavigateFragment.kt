package com.cairocart.ui.bottomnavigate

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.cairocart.R
import com.cairocart.base.BaseActivity
import com.cairocart.databinding.BottomFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bottom_fragment.*


@AndroidEntryPoint
class BottomNavigateFragment : BaseActivity<BottomFragmentBinding>() {

    override var idLayoutRes: Int = R.layout.bottom_fragment
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        changeStatusBarColor()
        setupNavigationView()
//        preventRecreate()

    }

    private fun setupNavigationView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController
//        var appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment,R.id.T_Categories, R.id.productsById,
//            R.id.T_Cart, R.id.T_Setting))
//        setupActionBarWithNavController(navHostFragment.navController,appBarConfiguration)

        setupWithNavController(bottomNavigationView, navHostFragment.navController)
        bottomNavigationView.setOnNavigationItemReselectedListener(){

        }
    }

    private fun preventRecreate() = bottomNavigationView.setOnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.T_Categories -> Navigation.findNavController(this, R.id.fragment)
                .navigate(R.id.T_Categories)

            R.id.productsById -> Navigation.findNavController(this, R.id.fragment)
                .navigate(R.id.productsById)

            else -> Navigation.findNavController(this, R.id.fragment).navigate(R.id.homeFragment)
        }
        return@setOnNavigationItemSelectedListener true
    }


    private fun changeStatusBarColor() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        }
    }

    override fun onBackPressed() {
        if (navController.graph.startDestination == navController.currentDestination?.id)
        {
            Exit()
        }
        else {
            super.onBackPressed()
        }
    }



    fun Exit(){
        var dialog= AlertDialog.Builder(this)
            .setMessage(resources.getString(R.string.exit_message))
            .setPositiveButton(resources.getString(R.string.yes), DialogInterface.OnClickListener {
                    dialog, which ->  finish()
            })
            .setNegativeButton(resources.getString(R.string.no), DialogInterface.OnClickListener { dialog, which ->
                return@OnClickListener
            })
            .show()
        val buttonPositive: Button = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
        buttonPositive.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        val buttonNegative: Button = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        buttonNegative.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

    }

}