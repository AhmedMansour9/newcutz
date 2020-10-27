package com.cairocart.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.cairocart.Model.FiltertionModel
import com.cairocart.R
import kotlinx.android.synthetic.main.activity_filtertion.*
import org.greenrobot.eventbus.EventBus

class Filtertion : AppCompatActivity() {

    var MaxPrice:String=""
    var MinPrice:String=""
    var InCart:String=""
    var InFavourit:String=""
    var Limited:String=""
    var NewProduct:String=""
    var BestProduct:String=""
    var cat_id:String?= String()
    var sub_id:String?= String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_filtertion)
        GetMaxPrice()
        GetMinPrice()
        GetBest()
        GetInCart()
        Getlimited()
        GetInFavourit()
        GetlNew()
        FilterProducts()


    }

    private fun FilterProducts() {
        btnDone.setOnClickListener(){
            EventBus.getDefault().postSticky(FiltertionModel(MaxPrice,MinPrice,InCart,
            InFavourit,Limited,NewProduct,BestProduct))
            finish()

        }
    }

    private fun GetMaxPrice() {
        Check_MaxPrice.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                MaxPrice="true"
                Check_MinPrice.isEnabled=false
            } else {
                MaxPrice=""
                Check_MinPrice.isEnabled=true

            }
        }
    }
    private fun GetMinPrice() {
        Check_MinPrice.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                MinPrice="true"
                Check_MaxPrice.isEnabled=false

            } else {
                MinPrice=""
                Check_MaxPrice.isEnabled=true

            }
        }
    }
    private fun GetInCart() {
        Check_InCart.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                InCart="true"

            } else {
                InCart=""

            }
        }
    }

    private fun GetInFavourit() {
        Check_InFavourit.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                InFavourit="true"

            } else {
                InFavourit=""

            }
        }
    }

    private fun Getlimited() {
        Check_Limited.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Limited="true"

            } else {
                Limited=""

            }
        }
    }

    private fun GetlNew() {
        Check_New.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                NewProduct="true"

            } else {
                NewProduct=""

            }
        }
    }
    private fun GetBest() {
        Check_Best.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                BestProduct="true"

            } else {
                BestProduct=""

            }
        }
    }
}