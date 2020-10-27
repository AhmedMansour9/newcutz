package com.cairocart.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.cairocart.R

class Addetional_Cart : AppCompatActivity() {
    lateinit var Product_Id:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_addetional__cart)

//        getAddetional()
    }


//    fun getAddetional(){
//        var Sizes: Nutrition_ViewModel =  ViewModelProvider.NewInstanceFactory().create(
//                Nutrition_ViewModel::class.java)
//        this.applicationContext?.let {
//            Sizes.getCartAddetional(intent.getStringExtra("id"), ChangeLanguage.getLanguage(applicationContext), it).observe(this, Observer<ExtraAdditonal_Response> { loginmodel ->
//                if(loginmodel!=null) {
//                    val listAdapter  = AddetionalCart_Adapter(applicationContext,loginmodel.data)
//                    recycler_Details.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL ,false)
//                    recycler_Details.setAdapter(listAdapter)
//                }else {
//                    T_NoProduct.visibility= View.VISIBLE
//                }
//
//            })
//        }
//
//    }

}
