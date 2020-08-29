package com.gazr.Activities

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.PreferenceManager
import com.chicchicken.ViewModel.Profile_ViewModel
import com.gazr.BaseActivity
import com.gazr.ChangeLanguage
import com.gazr.Model.AllProducts_Response
import com.gazr.Model.MessageEvent
import com.gazr.Model.Profile_Response
import com.gazr.R
import com.gazr.SharedPrefManager
import com.gazr.ViewModel.Cart_ViewModel
import com.gazr.utils.Loading
import kotlinx.android.synthetic.main.activity_tabs_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class TabsLayout : BaseActivity() {
    lateinit var navController: NavController
    var toolbar: Toolbar?=null
    private lateinit var DataSaver: SharedPreferences
    var UserToken:String?= String()
    var status:String?= String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabs_layout)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        setupViews()
        getNumberOfCart()
        Get_Gifts()
    }

    fun setupViews()
    {
        var navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.navController)
        var appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home,R.id.T_Categories,R.id.nav_home, R.id.T_Offers,
             R.id.T_Cart, R.id.T_Setting))
        setupActionBarWithNavController(navHostFragment.navController,appBarConfiguration)
        status=intent.getStringExtra("status")
        if(status!=null) {
            this!!.let { Navigation.findNavController(it, R.id.fragment).navigate(R.id.T_Setting) };
        }

        }
    fun Get_Gifts() {
        var Prof_ViewModel: Profile_ViewModel =
            ViewModelProviders.of(this)[Profile_ViewModel::class.java]
        UserToken =DataSaver.getString("token", null)
        Loading.Show(this)
        Prof_ViewModel.getData(
            UserToken,
            this
        ).observe(this,
            Observer<Profile_Response> { loginmodel ->
                Loading.Disable()
                if (loginmodel != null) {

                    if(loginmodel.data?.isGiftUsed.equals("0")){
                        startActivity(Intent(this,Gifts::class.java))
                    }
                } else {
                }
            }
        )

    }

    override fun onBackPressed() {
        var id=R.id.login
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

    fun getNumberOfCart() {
        DataSaver = PreferenceManager.getDefaultSharedPreferences(this)
        var allproducts: Cart_ViewModel = ViewModelProvider.NewInstanceFactory().create(
            Cart_ViewModel::class.java
        )
        this.let {
            allproducts.getData(
                ChangeLanguage.getLanguage(this),DataSaver.getString("token", null), it
            ).observe(this, Observer<AllProducts_Response> { loginmodel ->

                if (loginmodel != null) {
                    if (loginmodel.data!!.data.size > 0){
                        bottomNavigationView.getOrCreateBadge(R.id.T_Cart).setVisible(true)
                        bottomNavigationView.getOrCreateBadge(R.id.T_Cart).number = loginmodel.data!!.data.size
                }else {
                        bottomNavigationView.getOrCreateBadge(R.id.T_Cart).setVisible(false)
//                        bottomNavigation.removeBadge(menuItemId)
                    }
                }

            })
        }

    }

    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent(messsg: MessageEvent) {/* Do something */
        Log.d("IGNORE", "Logging view to curb warnings: $messsg")
       UserToken =DataSaver.getString("token", null)

        getNumberOfCart()


    };

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }
}