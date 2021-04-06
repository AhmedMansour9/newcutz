package com.cutz.ui.bottomnavigate

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navGraphViewModels
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.cutz.R
import com.cutz.adapter.CategoriesMenuAdapter
import com.cutz.adapter.CollectionsAapter
import com.cutz.base.BaseActivity
import com.cutz.data.remote.model.*
import com.cutz.databinding.BottomFragmentBinding
import com.cutz.ui.cart.CartViewModel
import com.cutz.ui.editprofile.EditProfileViewModel
import com.cutz.ui.home.HomeViewModel
import com.cutz.utils.SharedData
import com.cutz.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bottom_fragment.*
import kotlinx.android.synthetic.main.nav_header_navigation.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


@AndroidEntryPoint
class BottomNavigateActivity : BaseActivity<BottomFragmentBinding>() {

    private val mViewModelProfile: EditProfileViewModel by viewModels()
    override var idLayoutRes: Int = R.layout.bottom_fragment
    private lateinit var navController: NavController
    private var data: SharedData? = null
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfig: AppBarConfiguration
    private  var collectionsAdapter= CategoriesMenuAdapter(object : CategoriesMenuAdapter.CategoryItemListener{
        override fun itemClicked(productData: CategoriesResponse.Data) {
            drawerLayout.closeDrawer(GravityCompat.START)
            val bundle = Bundle()
            bundle.putParcelable("data", productData)
            Navigation.findNavController(this@BottomNavigateActivity, R.id.fragment)
                .navigate(R.id.subCategoriesMenuFragment,bundle);
        }
    })
    companion object {
        var token: String? = String()
        var Lang: String? = String()
    }

    val mViewModel: BottomViewModel by viewModels()
    val mViewModelCart: CartViewModel by viewModels()
    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = SharedData(this)
        token = data?.getValue(SharedData.ReturnValue.STRING, "token")
        getLang()
        initt()
        changeStatusBarColor()
        getData()
        initRecycle()
//        getCart()
        subscribeCart()
        categoryObserver()
        setupObserverProfile()

    }
    private fun initRecycle() {
        recycler_Categories.setHasFixedSize(true)
        recycler_Categories.layoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false )
        recycler_Categories.adapter = collectionsAdapter

    }

    fun setData(profile: ProfileResponse){
        FullName.setText(profile.data?.name.toString())
        Glide.with(this).load(profile.data?.image)
            .error(R.drawable.ic_editimage).into(Photo);

    }
    private fun setupObserverProfile() {
        mViewModelProfile.getProfileResponse.observe(this, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    it.data?.let { it1 -> setData(it1) }
                }
                Status.LOADING -> {
                }
                Status.ERROR -> {
                }
            }
        })
    }

    private fun categoryObserver() {
        mViewModel.categoryResponse.observe(this, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    addData(it.data?.data as MutableList<CategoriesResponse.Data>)
                }
                Status.LOADING -> {
                }
                Status.ERROR -> {
                }
            }
        })
    }


    private fun addData(data: MutableList<CategoriesResponse.Data>) {
        collectionsAdapter.setList(data)
    }
    private fun initt() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout = mViewDataBinding.drawerLayout
        navController = this.findNavController(R.id.fragment)
        toolbar?.setNavigationIcon(R.drawable.ic_menudrawer);
        supportActionBar?.setDisplayShowTitleEnabled(false);
        appBarConfig = AppBarConfiguration.Builder(
            R.id.homeFragment,
            R.id.offersFragment,
            R.id.recipesFragment,
            R.id.cartFragment,
            R.id.myAccountFragment,
        )
            .setOpenableLayout(drawerLayout)
            .build()
        setupActionBarWithNavController(navController, appBarConfig)
        mViewDataBinding.bottomNavigationView.setItemIconTintList(null);
        mViewDataBinding.bottomNavigationView.setupWithNavController(navController)
        mViewDataBinding.navView?.setupWithNavController(navController) //Setup Drawer navigation with navController
    }


    override fun onSupportNavigateUp(): Boolean { //Setup appBarConfiguration for back arrow
        return NavigationUI.navigateUp(navController, appBarConfig)
    }

    private fun getLang() {
        Lang = data?.getValue(SharedData.ReturnValue.STRING, "Lann")
        if (Lang.isNullOrEmpty()) {
            Lang = "en"
        }
        mViewModel.getCategory(Lang!!)
    }

    private fun getData() {
        var message = intent.getStringExtra("data")
        if (!message.isNullOrEmpty()) {
            Navigation.findNavController(this, R.id.fragment)
                .navigate(R.id.cartFragment);
        }
    }

    private fun setupNavigationView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupWithNavController(bottomNavigationView, navHostFragment.navController)

    }


    private fun changeStatusBarColor() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (navController.graph.startDestination == navController.currentDestination?.id) {
                Exit()
            } else {
                super.onBackPressed()
            }
        }

    }


    fun Exit() {
        var dialog = AlertDialog.Builder(this)
            .setMessage(resources.getString(R.string.exit_message))
            .setPositiveButton(
                resources.getString(R.string.yes),
                DialogInterface.OnClickListener { dialog, which ->
                    finish()
                })
            .setNegativeButton(
                resources.getString(R.string.no),
                DialogInterface.OnClickListener { dialog, which ->
                    return@OnClickListener
                })
            .show()
        val buttonPositive: Button = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
        buttonPositive.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        val buttonNegative: Button = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        buttonNegative.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

    }


    fun getCart() {
        token = data?.getValue(SharedData.ReturnValue.STRING, "token")
        mViewModelCart.getCart(token!!)

    }

    private fun isLogin(): Boolean {
        token = data?.getValue(SharedData.ReturnValue.STRING, "token")
        if (token.isNullOrEmpty()) {
            return false
        }
        return true
    }

    fun checkToken(): Boolean {
        if (token.isNullOrEmpty()) {
            return false
        }
        return true
    }


    private fun subscribeCart() {
        mViewModelCart.cartResponse.observe(this, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    it.data?.let { it1 -> setCounterCart(it1) }

                }
                Status.ERROR -> {
                    hideCounterart()
                }
            }
        })
    }

    private fun setCounterCart(list: ListCartResponse) {
        if (list.data != null) {
            if (list.data!!.cartItems!!.size > 0) {
                bottomNavigationView.getOrCreateBadge(R.id.cartFragment).setVisible(true)
                bottomNavigationView.getOrCreateBadge(R.id.cartFragment).number =
                    list.data!!.cartItems?.size!!
            } else {
                bottomNavigationView.getOrCreateBadge(R.id.cartFragment).setVisible(false)
            }
        } else {
            bottomNavigationView.getOrCreateBadge(R.id.cartFragment).setVisible(false)
        }
    }

    fun hideCounterart() {
        bottomNavigationView.getOrCreateBadge(R.id.cartFragment).setVisible(false)
    }

    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent(messsg: MessageEvent) {/* Do something */
        Log.d("IGNORE", "Logging view to curb warnings: $messsg")
        if (messsg.Message.equals("cart")) {
            getCart()
        }

    };

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onResume() {
        super.onResume()
    }


}