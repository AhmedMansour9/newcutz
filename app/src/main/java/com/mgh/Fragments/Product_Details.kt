package com.mgh.Fragments


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.text.HtmlCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mgh.Activities.Login
import com.mgh.Activities.Navigation
import com.mgh.Adapter.Sizes_Adapter
import com.mgh.Adapter.SliderProduct_Adapter
import com.mgh.ChangeLanguage
import com.mgh.Model.*
import com.mgh.R
import com.mgh.SharedPrefManager
import com.mgh.ViewModel.AddToCart_ViewModel
import com.mgh.ViewModel.Cart_ViewModel
import com.mgh.ViewModel.Nutrition_ViewModel
import com.mgh.ViewModel.getAllProducts_ViewModel
import com.mgh.utils.Loading
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_offers__details.view.*
import kotlinx.android.synthetic.main.fragment_offers__details.view.viewPager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class Product_Details : Fragment() {

    companion object {
         var price:Double=0.0
         var Extra:Int=0
         var Total:Double=0.0
        var Currency:String?= String()
         var Counter:Int=1
     }
    lateinit var detailsFavourits: AllProducts_Response.Data.Data
    lateinit var ListSlider : List<AllProducts_Response.Data.Data.ProductImage>

    var bundle= Bundle()
    //    lateinit var allproducts: Cart_ViewModel
    var Product_Id:String?= String()
     var Wishlist: Boolean = false
    var Additonal:String?= String()
    lateinit var allproducts: Cart_ViewModel
    lateinit var details:ProductDeal_Response.Data

    private lateinit var DataSaver: SharedPreferences
    lateinit var root:View
    var swipeTimer: Timer?=null
    var Size:String?= String()
    private var UserToken:String?= String()

    val handler = Handler()
    val Update = Runnable {
        if (currentPage == NUM_PAGES) {
            currentPage = 0
        }
        root.viewPager!!.setCurrentItem(currentPage++, true)
    }
    private var currentPage = 0
    private var NUM_PAGES = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_offers__details, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
        UserToken=DataSaver.getString("token", null)
        init()
        getData()
        getSlider()
        getNutrition()
        Btn_Plus()
        Btn_Minus()
        AddToCart()
        onClickBenfits()
        onClickNuture()
        RemoveFromCart()
        getNumberOfCart()
        return root
    }

    fun init() {
        val toggle = ActionBarDrawerToggle(
            activity,
            Navigation.drawerLayout,
            root.toolbarDetails,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.apply {
            syncState()
            isDrawerIndicatorEnabled = false
            setHomeAsUpIndicator(R.drawable.ic_homemenu)
            setToolbarNavigationClickListener { Navigation.drawerLayout!!.openDrawer(GravityCompat.START) }
        }
        Navigation.drawerLayout?.addDrawerListener(toggle)

    }


    private fun onClickNuture() {
    root.Benfits.setOnClickListener(){
         root.Benfits.setTextColor(Color.parseColor("#ffffff"))
         root.Benfits.setBackgroundResource(R.drawable.bc_benfits)
         root.Nutr.setTextColor(Color.parseColor("#0b2031"))
         root.Nutr.setBackgroundColor(Color.TRANSPARENT)
         root.T_Benfits.visibility=View.VISIBLE
        root.recycler_Addetional.visibility=View.GONE

     }
    }



    private fun onClickBenfits() {
        root.Nutr.setOnClickListener(){
            root.Benfits.setTextColor(Color.parseColor("#0b2031"))
            root.Benfits.setBackgroundColor(Color.TRANSPARENT)
            root.Nutr.setTextColor(Color.parseColor("#ffffff"))
            root.Nutr.setBackgroundResource(R.drawable.bc_benfits)
            root.T_Benfits.visibility=View.GONE
            root.recycler_Addetional.visibility=View.VISIBLE

        }
    }


    fun AddToCart(){
        root.Btn_AddTocart.setOnClickListener(){
            UserToken=DataSaver.getString("token", null)
            if(UserToken!=null) {
                   Loading.Show(requireContext())
                   var addtocart: AddToCart_ViewModel =
                       ViewModelProvider.NewInstanceFactory().create(
                           AddToCart_ViewModel::class.java
                       )
                   this.context!!.applicationContext?.let {
                       addtocart.getData(
                           DataSaver.getString("token", null)!!,
                           Product_Id!!,
                           root.T_Quantity.text.toString()
                           ,
                           it
                       )
                           .observe(viewLifecycleOwner, Observer<AddToCart_Response> { loginmodel ->
                               Loading.Disable()
                               EventBus.getDefault().postSticky(MessageEvent("cart"))
                               if (loginmodel != null) {
                                   SuccessAdCart(resources.getString(R.string.addtocartsuccess))
                                   root.Btn_Removecart.visibility = View.VISIBLE
                                   root.Btn_AddTocart.visibility = View.INVISIBLE
                               }
                           })
                   }
               }else {
                   val intent = Intent(requireContext(), Login::class.java)
                   startActivity(intent)

               }

        }
    }
    fun RemoveFromCart(){
        root.Btn_Removecart.setOnClickListener(){

            Loading.Show(requireContext())
            var addtocart: Cart_ViewModel = ViewModelProvider.NewInstanceFactory().create(
                Cart_ViewModel::class.java)
            this.context!!.applicationContext?.let {
                addtocart.DeleteData(DataSaver.getString("token", null)!!, "en",Product_Id!!
                    , it)
                    .observe(viewLifecycleOwner, Observer<PlusCart_Response> { loginmodel ->
                        Loading.Disable()
                        EventBus.getDefault().postSticky(MessageEvent("cart"))
                        if (loginmodel != null) {
                            SuccessAdCart(resources.getString(R.string.remove_cart))
                            root.Btn_Removecart.visibility=View.INVISIBLE
                            root.Btn_AddTocart.visibility=View.VISIBLE
                        }
                    })
            }

        }
    }



    fun Btn_Plus(){
        root.Img_Plus.setOnClickListener(){
            Counter++
            root.T_Quantity.text=Counter.toString()
            Total=root.T_Quantity.text.toString().toDouble()*price
            root.T_TotalPrices.text = Total.toString()+" "+ detailsFavourits.currency
        }
    }

    fun Btn_Minus(){
        root.Img_Minus.setOnClickListener(){
            if(Counter>1)
                Counter--
            root.T_Quantity.text=Counter.toString()
            Total=root.T_Quantity.text.toString().toDouble()*price
            root.T_TotalPrices.text = Total.toString()+" "+detailsFavourits.currency

        }
    }


    fun getData(){
        bundle = this.arguments!!
        Counter=1
        detailsFavourits = bundle.getParcelable("ProductItem")!!
        price=detailsFavourits.total!!.toDouble()
        root.T_SalePrices.text=detailsFavourits.total.toString()+" "+detailsFavourits.currency
        root.T_TotalPrices.text=detailsFavourits.total.toString()+" "+detailsFavourits.currency
      if(detailsFavourits.productInCart==0){
          root.Btn_Removecart.visibility=View.INVISIBLE
          root.Btn_AddTocart.visibility=View.VISIBLE
      }else {
          root.Btn_Removecart.visibility=View.VISIBLE
          root.Btn_AddTocart.visibility=View.INVISIBLE
      }
        ListSlider= detailsFavourits.productImages as List<AllProducts_Response.Data.Data.ProductImage>
        root.T_SmallDescrptionn.text=detailsFavourits.shortDescription
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                root.T_Benfits.setText(
                    Html.fromHtml(
                        detailsFavourits.description,
                        Html.FROM_HTML_MODE_LEGACY)
                )

            } else {
                HtmlCompat.fromHtml(detailsFavourits.description!!, HtmlCompat.FROM_HTML_MODE_LEGACY)

            }
            detailsFavourits.isProductFavoirte==1
            Product_Id=detailsFavourits.id.toString()
           root.RatingBarDetails.rating=detailsFavourits.totalRate!!.toFloat()

            root.Title.text=detailsFavourits.name

    }

    fun getSlider(){
                    root.viewPager!!.adapter = this.context?.let { it1 ->
                        SliderProduct_Adapter(
                            it1,
                            ListSlider
                        )
                    }
        root.view_pager_circle_indicatoDetails.setViewPager(root.viewPager)

                    NUM_PAGES = ListSlider.size
                    swipeTimer = Timer()
                    swipeTimer!!.schedule(object : TimerTask() {
                        override fun run() {
                            handler.post(Update)
                        }
                    }, 3000, 3000)

    }


    fun AddToFavourit(api:String,Productid: String) {
        var allproducts: getAllProducts_ViewModel =  ViewModelProvider.NewInstanceFactory().create(
            getAllProducts_ViewModel::class.java)
        this.context!!.applicationContext?.let {
            allproducts.getAddFavouritProducts(api,Productid,
                DataSaver.getString("token",null)!!, it).observe(viewLifecycleOwner, Observer<AddFav_Response> {
                EventBus.getDefault().postSticky(MessageEvent("fav"))
            })
        }

    }

    fun getNutrition(){

        val listAdapter  = Sizes_Adapter(context!!.applicationContext,
            detailsFavourits.reviews as List<AllProducts_Response.Data.Data.Review>
        )
        root.recycler_Addetional.layoutManager = LinearLayoutManager(this.context!!.applicationContext, LinearLayoutManager.VERTICAL ,false)
        root.recycler_Addetional.setAdapter(listAdapter)


    }


    fun SuccessAdCart(Message:String) {
        val alt_bld: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alt_bld.setMessage(Message).setCancelable(false)
            .setPositiveButton("ok", DialogInterface.OnClickListener { dialog, id ->

            })
        val alert: AlertDialog = alt_bld.create()
        // Title for AlertDialog
        alert.setTitle(resources.getString(R.string.cartt))
        alert.setCancelable(false)
        alert.show()
    }



    fun getNumberOfCart() {
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
        var allproducts: Cart_ViewModel = ViewModelProvider.NewInstanceFactory().create(
            Cart_ViewModel::class.java
        )
        this.context!!.applicationContext?.let {
            allproducts.getData(ChangeLanguage.getLanguage(requireContext()),UserToken, it
            ).observe(viewLifecycleOwner, Observer<AllProducts_Response> { loginmodel ->

                if (loginmodel != null) {
                    if(loginmodel.data!!.data.size>0)
                        root.T_notification_numdetai.visibility = View.VISIBLE
                    root.T_notification_numdetai.text = loginmodel.data!!.data.size.toString()

                }

            })
        }

    }



    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent(messsg: MessageEvent) {/* Do something */
        Log.d("IGNORE", "Logging view to curb warnings: $messsg")
        UserToken=DataSaver.getString("token", null)
        getNumberOfCart()


    };
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

    }
}
