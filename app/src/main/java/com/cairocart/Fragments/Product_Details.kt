package com.cairocart.Fragments


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Paint
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cairocart.Activities.Login
import com.cairocart.Adapter.Offers_Adapter
import com.cairocart.Adapter.Sizes_Adapter
import com.cairocart.Adapter.SliderProduct_Adapter
import com.cairocart.ChangeLanguage
import com.cairocart.Model.*
import com.cairocart.R
import com.cairocart.View.DetailsProduct_id
import com.cairocart.View.ProductDetails_View
import com.cairocart.ViewModel.AddToCart_ViewModel
import com.cairocart.ViewModel.Cart_ViewModel
import com.cairocart.ViewModel.getAllProducts_ViewModel
import com.cairocart.utils.Loading
import kotlinx.android.synthetic.main.fragment_offers__details.view.*
import kotlinx.android.synthetic.main.fragment_offers__details.view.Img_Cartt
import kotlinx.android.synthetic.main.fragment_offers__details.view.Img_Favourit
import kotlinx.android.synthetic.main.fragment_offers__details.view.RatingBar
import kotlinx.android.synthetic.main.fragment_offers__details.view.T_Reviees
import kotlinx.android.synthetic.main.fragment_offers__details.view.Title
import kotlinx.android.synthetic.main.fragment_offers__details.view.recycler_Addetional
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class Product_Details : Fragment(), DetailsProduct_id , ProductDetails_View {

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
        DataSaver = PreferenceManager.getDefaultSharedPreferences(requireContext())
        UserToken=DataSaver.getString("token", null)
        bundle = requireArguments()
        detailsFavourits = bundle.getParcelable("ProductItem")!!
        getData()
        getSlider()
        OnClickBtn_Descrption()
        OnClickBtn_Reviwes()
        getReviwes()
        Btn_Plus()
        Btn_Minus()
        AddToCart()
        RemoveFromCart()
        OnClickFavourit()
        ShareLink()
        getSimiliar()
        BuyCart()
        return root
    }

    private fun ShareLink() {
        root.Img_Share.setOnClickListener(){
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                detailsFavourits.product_link
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }

    }

    fun getSimiliar() {
        var allproducts: getAllProducts_ViewModel = ViewModelProvider.NewInstanceFactory().create(
            getAllProducts_ViewModel::class.java
        )
        context?.let { ChangeLanguage.getLanguage(it) }?.let {
            allproducts.getSimilar(
                "1", it
                , "similarProducts", UserToken,detailsFavourits.id.toString()
            ).observe(viewLifecycleOwner, Observer<AllProducts_Response> { loginmodel ->
                if (loginmodel != null) {
                    var listAdapter =
                        Offers_Adapter(
                            requireContext(),
                            loginmodel.data?.data as List<AllProducts_Response.Data.Data>
                        )
                    listAdapter.onClick(this)
                    listAdapter.onClickFavourit(this)
                    root.recycler_Similar.layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    root.recycler_Similar.setAdapter(listAdapter)

                }
            })
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
                   requireContext()?.let {
                       addtocart.getData(
                           DataSaver.getString("token", null)!!,
                           Product_Id!!,
                           root.T_Quantity.text.toString()
                           ,
                           it
                       )
                           .observe(viewLifecycleOwner, Observer<AddToCart_Response> { loginmodel ->
                               Loading.Disable()
                               if (loginmodel != null) {
                                   EventBus.getDefault().postSticky(MessageEvent("cart"))
                                   var mp  = MediaPlayer.create(it, R.raw.alert);
                                   mp.start()
                                   SuccessAdCart(resources.getString(R.string.addtocartsuccess))
                                   root.Btn_Removecart.visibility = View.VISIBLE
                                   root.Btn_AddTocart.visibility = View.INVISIBLE
                                   root.Img_Cartt.setImageResource(R.drawable.ic_cart)
                                   root.Btn_BuyNow.visibility=View.INVISIBLE

                               }else {
                                   Toast.makeText(context, AddToCart_ViewModel.ErrorAddCart, Toast.LENGTH_SHORT).show()
                               }
                           })
                   }
               }else {
                   val intent = Intent(requireContext(), Login::class.java)
                   startActivity(intent)

               }

        }
    }

    fun BuyCart(){
        root.Btn_BuyNow.setOnClickListener(){
            UserToken=DataSaver.getString("token", null)
            if(UserToken!=null) {
                Loading.Show(requireContext())
                var addtocart: AddToCart_ViewModel =
                    ViewModelProvider.NewInstanceFactory().create(
                        AddToCart_ViewModel::class.java
                    )
                requireContext()?.let {
                    addtocart.getData(
                        DataSaver.getString("token", null)!!,
                        Product_Id!!,
                        root.T_Quantity.text.toString()
                        ,
                        it
                    )
                        .observe(viewLifecycleOwner, Observer<AddToCart_Response> { loginmodel ->
                            Loading.Disable()
                            if (loginmodel != null) {
                                EventBus.getDefault().postSticky(MessageEvent("cart"))
                                var mp  = MediaPlayer.create(it, R.raw.alert);
                                mp.start()
                                root.Btn_Removecart.visibility = View.VISIBLE
                                root.Btn_AddTocart.visibility = View.INVISIBLE
                                root.Img_Cartt.setImageResource(R.drawable.ic_cart)
                                this!!.activity?.let { Navigation.findNavController(it, R.id.fragment).navigate(R.id.T_Cart) };

                            }else {
                                Toast.makeText(context, AddToCart_ViewModel.ErrorAddCart, Toast.LENGTH_SHORT).show()
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
            requireContext()?.let {
                addtocart.DeleteData(DataSaver.getString("token", null)!!, "en",Product_Id!!
                    , it)
                    .observe(viewLifecycleOwner, Observer<PlusCart_Response> { loginmodel ->
                        Loading.Disable()
                        EventBus.getDefault().postSticky(MessageEvent("cart"))
                        if (loginmodel != null) {
                            SuccessAdCart(resources.getString(R.string.remove_cart))
                            root.Btn_Removecart.visibility=View.INVISIBLE
                            root.Btn_AddTocart.visibility=View.VISIBLE
                            root.Btn_BuyNow.visibility=View.VISIBLE

                            root.Img_Cartt.setImageResource(R.drawable.ic_emptycart)
                            var mp  = MediaPlayer.create(it, R.raw.favourit);
                            mp.start()

                        }
                    })
            }

        }
    }



    fun Btn_Plus(){
        root.Img_Plus.setOnClickListener(){
            if(detailsFavourits.stock!! >Counter){
            Counter++
            root.T_Quantity.text=Counter.toString()
            Total=root.T_Quantity.text.toString().toDouble()*price
            root.T_TotalPrices.text = Total.toString()+" "+ detailsFavourits.currency
            }else {
                Toast.makeText(context, resources.getString(R.string.out_stock), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun Btn_Minus(){
        root.Img_Minus.setOnClickListener(){
            if(Counter>1) {
                Counter--
                root.T_Quantity.text = Counter.toString()
                Total = root.T_Quantity.text.toString().toDouble() * price
                root.T_TotalPrices.text = Total.toString()+" "+ detailsFavourits.currency
            }
        }
    }


    fun getData(){
        Counter=1
        price=detailsFavourits.total!!.toDouble()
        root.Title2.text=detailsFavourits.name
        root.T_price.text=detailsFavourits.total.toString()+" "+detailsFavourits.currency
        root.T_price2.text=detailsFavourits.total.toString()+" "+detailsFavourits.currency
        root.T_SmallDescripion.text=detailsFavourits.shortDescription
        root.T_Stock.text=String.format(resources.getString(R.string.in_stock)+" "+detailsFavourits.stock)
        root.T_Wight.text=String.format(resources.getString(R.string.wight)+" "+detailsFavourits.unit_value+" "+detailsFavourits.unit)
        root.T_TotalPrices.text=detailsFavourits.total.toString()+" "+detailsFavourits.currency
        if(detailsFavourits.discount!!.toDouble()>0){
          root.T_Orignalprice2.text=detailsFavourits.salePrice.toString()+" "+detailsFavourits.currency
            root.T_Orignalprice2.visibility=View.VISIBLE
            root.T_Orignalprice2.setPaintFlags(root.T_Orignalprice2.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
            root.T_Discount.visibility=View.VISIBLE
            root.T_Discount.text=resources.getString(R.string.discountt)+" "+detailsFavourits.discount+" "+detailsFavourits.currency

        }
        if(detailsFavourits.stock==0){
         root.Rela_Carttt.visibility=View.GONE
            root.T_Stock.text=resources.getString(R.string.out_stock)}
        checkvisablity()
        ListSlider= detailsFavourits.productImages as List<AllProducts_Response.Data.Data.ProductImage>
        root.T_SmallDescripion.text=detailsFavourits.shortDescription
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                root.T_Benfits.setText(
                    Html.fromHtml(
                        detailsFavourits.description,
                        Html.FROM_HTML_MODE_LEGACY)
                )

            } else {
                root.T_Benfits.setText(HtmlCompat.fromHtml(detailsFavourits.description!!, 0));

            }
            detailsFavourits.isProductFavoirte==1
            Product_Id=detailsFavourits.id.toString()
            root.RatingBar.rating=detailsFavourits.totalRate!!.toFloat()
            root.Title.text=detailsFavourits.name
            root.T_Reviees.text= String.format("(${detailsFavourits.totalNumberReview.toString()+resources.getString(R.string.reviews)})")
    }
    fun checkvisablity(){
        if(detailsFavourits.productInCart==0){
            root.Btn_Removecart.visibility=View.INVISIBLE
            root.Btn_AddTocart.visibility=View.VISIBLE
        }else {
            root.Btn_Removecart.visibility=View.VISIBLE
            root.Btn_AddTocart.visibility=View.INVISIBLE
            root.Btn_BuyNow.visibility=View.GONE

        }
        if (detailsFavourits.isProductFavoirte==0){
            root.Img_Favourit.setImageResource(R.drawable.ic_emptyfavourit)
        }else {
            root.Img_Favourit.setImageResource(R.drawable.ic_productfavourit)
        }
        if (detailsFavourits.productInCart==0){
            root.Img_Cartt.setImageResource(R.drawable.ic_emptycart)
        }else {
            root.Img_Cartt.setImageResource(R.drawable.ic_cart)
        }
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



//    fun  openReviwes(){
//
//        root.Nutr.setOnClickListener(){
//            var intent= Intent(requireContext(), Riviews::class.java)
//            intent.putExtra("list",detailsFavourits)
//            intent.putExtra("id",detailsFavourits.id.toString())
//            startActivity(intent)
//        }
//    }



    fun SuccessAdCart(Message:String) {
        val alt_bld: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alt_bld.setMessage(Message).setCancelable(false)
            .setPositiveButton(resources.getString(R.string.ok), DialogInterface.OnClickListener { dialog, id ->

            })
        val alert: AlertDialog = alt_bld.create()
        // Title for AlertDialog
        alert.setTitle(resources.getString(R.string.cartt))
        alert.setCancelable(false)
        alert.show()
    }




    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent(messsg: MessageEvent) {/* Do something */
        Log.d("IGNORE", "Logging view to curb warnings: $messsg")
        UserToken=DataSaver.getString("token", null)


    };
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

    }
    fun OnClickBtn_Descrption(){
        root.bt_toggle_description.setOnClickListener(){
          var checked=  toggleArrow(root.bt_toggle_description)
            if(checked){
                root.T_Benfits.visibility=View.VISIBLE
            }else {
                root.T_Benfits.visibility=View.GONE
            }
        }
    }

    fun toggleArrow(view: View): Boolean {
        return if (view.rotation == 0f) {
            view.animate().setDuration(200).rotation(180f)
            true
        } else {
            view.animate().setDuration(200).rotation(0f)
            false
        }
    }

    fun OnClickBtn_Reviwes(){
        root.bt_toggle_reviwes.setOnClickListener(){
            var checked=  toggleArrow(root.bt_toggle_reviwes)
            if(checked){
                if(detailsFavourits.reviews.size==0){
                    root.T_NoReview.visibility=View.VISIBLE
                }
                root.recycler_Addetional.visibility=View.VISIBLE
            }else {
                root.T_NoReview.visibility=View.GONE
                root.recycler_Addetional.visibility=View.GONE
            }
        }
    }


    fun getReviwes(){

        val listAdapter  = context?.let {
            Sizes_Adapter(
                it,
                detailsFavourits.reviews
            )
        }
        root.recycler_Addetional.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
        root.recycler_Addetional.setAdapter(listAdapter)


    }
   fun OnClickFavourit(){
       root.Img_Favourit.setOnClickListener(){
           if(UserToken!=null) {
               if (detailsFavourits.isProductFavoirte == 0) {
                   detailsFavourits.isProductFavoirte == 1
                   root.Img_Favourit.setImageResource(R.drawable.ic_productfavourit)
                   AddFavourit("customer/addFavorite", detailsFavourits.id.toString())
               } else {
                   detailsFavourits.isProductFavoirte == 0
                   root.Img_Favourit.setImageResource(R.drawable.ic_emptyfavourit)
                   AddFavourit("customer/removeFavorite", detailsFavourits.id.toString())

               }
           }else {
               val intent = Intent(requireContext(), Login::class.java)
               startActivity(intent)
           }
       }
   }

    fun AddFavourit(api:String,id: String) {
        var allproducts: getAllProducts_ViewModel = ViewModelProvider.NewInstanceFactory().create(
            getAllProducts_ViewModel::class.java
        )
        allproducts.getAddFavouritProducts(
            api, id,
            DataSaver.getString("token", null)!!).observe(viewLifecycleOwner, Observer<AddFav_Response> {
            EventBus.getDefault().postSticky(MessageEvent("fav"))
            var mp  = MediaPlayer.create(context, R.raw.alert);
            mp.start()

        })

    }


    override fun AddToFavourit(api: String, Productid: String) {
        var allproducts: getAllProducts_ViewModel = ViewModelProvider.NewInstanceFactory().create(
            getAllProducts_ViewModel::class.java
        )

        allproducts.getAddFavouritProducts(
            api, Productid,
            DataSaver.getString("token", null)!!).observe(viewLifecycleOwner, Observer<AddFav_Response> {

        })

    }

    override fun AddToFavouritCart(api: String, Productid: String) {
    }


    override fun DetailsProducts(detailsProduct: AllProducts_Response.Data.Data) {

    }

    override fun Details(detailsProduct: AllProducts_Response.Data.Data) {
        getSimiliar()

        detailsFavourits=detailsProduct
        getData()
        getSlider()

    }


}
