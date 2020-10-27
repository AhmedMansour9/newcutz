package com.cairocart.Activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import com.cairocart.Adapter.Gifts_Adapter
import com.cairocart.BaseActivity
import com.cairocart.Model.AddToCart_Response
import com.cairocart.Model.Gifts_Response
import com.cairocart.R
import com.cairocart.SharedPrefManager
import com.cairocart.View.DetailsGifts_View
import com.cairocart.ViewModel.MyOrders_ViewModel
import kotlinx.android.synthetic.main.activity_gifts.*

class Gifts : BaseActivity() , DetailsGifts_View {

    lateinit var root: View
    var UserToken: String?= String()
    private lateinit var DataSaver: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_gifts)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(this)
        UserToken = DataSaver.getString("token", null)

        getAllGifts()
    }

    fun getAllGifts(){
        var  allproducts = ViewModelProvider.NewInstanceFactory().create(
            MyOrders_ViewModel::class.java)
        this?.let {
            allproducts.getGifts("customer/gifts",UserToken, it).observe(this, Observer<Gifts_Response> { loginmodel ->
                if(loginmodel!=null) {
//                    root.view3.visibility=View.VISIBLE
                    val listAdapter =
                        Gifts_Adapter(loginmodel.data as List<Gifts_Response.Data>)
                    listAdapter.OnClick(this)
                    Recycle_Gifts.setLayoutManager(
                        GridLayoutManager(
                            this
                            , 3
                        )
                    )
                    Recycle_Gifts.setAdapter(listAdapter)
                }

            })
        }
    }

    override fun details(details: Gifts_Response.Data) {
        Progress.visibility=View.VISIBLE
        var  allproducts = ViewModelProvider.NewInstanceFactory().create(
            MyOrders_ViewModel::class.java)
        this?.let {
            allproducts.addGift("customer/getGift",UserToken,details.id.toString(), it).observe(this, Observer<AddToCart_Response> { loginmodel ->
                Progress.visibility=View.GONE
                var intent= Intent(this,Congratulation::class.java)
                if(loginmodel!=null) {
                    SharedPrefManager.getInstance(this).saveGift("used")
                    Recycle_Gifts.visibility=View.INVISIBLE
                    if(details.type.equals("promocode")){
                        intent.putExtra("msg",resources.getString(R.string.gift_promo)+""+details.giftValue+"%")
                        startActivity(intent)
                        finish()
                    }else if(details.type.equals("gift")){
                        if(details.giftValue.equals("emptygift")){
                            T_Result.text=resources.getString(R.string.empty_gift)+" "+details.giftValue
                        }  else {
                            intent.putExtra("msg",resources.getString(R.string.gift_promo)+" "+details.giftValue)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            })
        }
    }
}