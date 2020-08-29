package com.gazr.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.navigation.NavigationView
import com.gazr.ChangeLanguage
import com.gazr.Model.MessageEvent
import com.gazr.R
import kotlinx.android.synthetic.main.nav_header_navigation.view.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class NavigationDrawer : AppCompatActivity() {
    private lateinit var DataSaver: SharedPreferences
    private lateinit var navView: NavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration
    companion object {
        var toolbar: Toolbar?=null
        var drawerLayout: DrawerLayout?=null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ChangeLanguage.changeLang(this)
        setContentView(R.layout.activity_navigation)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(this)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        toolbar!!.setNavigationIcon(R.drawable.ic_homemenu);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,R.id.T_Offers, R.id.T_Cart,R.id.T_Setting
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
         hideItem()
        openRegister()
        openLogin()

    }

   fun  openRegister(){
       var hView:View=navView.getHeaderView(0)

       hView.T_Register.setOnClickListener(){
           var intent= Intent(this, Register::class.java)
           startActivity(intent)
       }
    }
    fun  openLogin(){
        var hView:View=navView.getHeaderView(0)

        hView.T_login.setOnClickListener(){
            var intent= Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
    private fun hideItem() {
        if(DataSaver.getString("token", null).isNullOrEmpty()){
            navView = findViewById<View>(R.id.nav_view) as NavigationView
            val nav_Menu: Menu = navView!!.getMenu()
            nav_Menu.findItem(R.id.nav_myprofile).isVisible = false
            nav_Menu.findItem(R.id.nav_logout).isVisible = false
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigation, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent(messsg: MessageEvent) {/* Do something */
        Log.d("IGNORE", "Logging view to curb warnings: $messsg")
        if(messsg.Message.equals("login")){
                navView = findViewById<View>(R.id.nav_view) as NavigationView
                val nav_Menu: Menu = navView!!.getMenu()
                nav_Menu.findItem(R.id.nav_myprofile).isVisible = true
                nav_Menu.findItem(R.id.nav_logout).isVisible = true
                var hView:View=navView.getHeaderView(0)
                hView.visibility=View.GONE
        }


    };




}
