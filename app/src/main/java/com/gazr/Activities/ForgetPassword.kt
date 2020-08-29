package com.gazr.Activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gazr.Model.Register_Model
import com.gazr.R
import com.gazr.ViewModel.Register_ViewModel


import com.gazr.utils.Loading
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_login.*

class ForgetPassword : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)



    }

//    fun ForgetPass(){
//        Btn_Forget.setOnClickListener() {
//
//            var RegisterViewModel = ViewModelProvider.NewInstanceFactory().create(
//                Register_ViewModel::class.java
//            )
//            Loading.Show(this)
//            Btn_login.isEnabled = false
//            Btn_login.hideKeyboard()
//            RegisterViewModel.getLogin(
//                E_EmailLogin.text.toString(),
//                E_PasswordLogin.text.toString(),
//                applicationContext
//            ).observe(this,
//                Observer<Register_Model> { loginmodel ->
//                    Btn_login.isEnabled = true
//                    Loading.Disable()
//                    if (loginmodel!=null) {
//
//
////                        val intent = Intent(this, Navigation::class.java)
////                        startActivity(intent)
//                        finish()
//                    } else {
//                        E_PasswordLogin.text=null
//                        Toast.makeText(
//                            applicationContext,
//                            applicationContext.getString(R.string.wrongemailorpass),
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//
//                }
//            )
//        }

//    }


    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}